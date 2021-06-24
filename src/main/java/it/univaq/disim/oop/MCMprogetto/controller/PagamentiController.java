package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class PagamentiController implements Initializable, DataInitializable<Utente, Object> {
	private NoleggioService noleggioservice;
	private VeicoloService veicoloservice;
	private Noleggio noleggio;
	private Veicolo veicolo;

	@FXML
	private TableView<Noleggio> tabella_pagamento;
	@FXML
	private TableColumn<Noleggio, Integer> id_pagamento;
	@FXML
	private TableColumn<Noleggio, String> veicolo_pagamento;
	@FXML
	private TableColumn<Noleggio, String> cliente_pagamento;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> data_in_pagamento;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> data_fi_pagamento;
	@FXML
	private TableColumn<Noleggio, Button> azioni_pagamento;
	@FXML
	private TextField text_set_km;
	@FXML
	private Button bottone_costo;
	@FXML
	private Label label_tariffa_did;
	@FXML
	private Label label_tariffa_euro;
	@FXML
	private Label label_costo_tot;
	@FXML
	private Button bottone_pagamento;
	@FXML
	private Label label_successo;
	@FXML
	private Label label_errore;
	@FXML
	private Label label_km_cliente;

	public PagamentiController() {
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
		veicoloservice = factory.getVeicoloService();
	}

	@FXML // VEDI COSTO
	void vediCosto(ActionEvent event) {
		try {
			double i = noleggio.getVeicolo().getTariffa_chilometraggio() * Double.parseDouble(text_set_km.getText());
			i /= 1000;
			label_costo_tot.setText(Double.toString(i) + "€");
			label_costo_tot.setVisible(true);
			bottone_pagamento.setVisible(true);
		} catch (NumberFormatException e) {
			label_errore.setVisible(true);
		}
	}

	@FXML // REGISTRA PAGAMENTO
	void registraPagamento(MouseEvent event) throws BusinessException, InterruptedException {
		try {
			double i = noleggio.getVeicolo().getTariffa_chilometraggio() * Double.parseDouble(text_set_km.getText());
			noleggio.setPrezzo_noleggio(i / 1000);
			noleggioservice.updateNoleggio(noleggio);
			label_successo.setVisible(true);
			veicolo.setChilometri(veicolo.getChilometri() + Integer.parseInt(text_set_km.getText()));
			veicoloservice.updateVeicolo(veicolo);

			initialize(null, null);
		} catch (NumberFormatException e) {
			label_errore.setVisible(true);
		}
	}

	// MOSTRA COSE
	void mostraCose(Noleggio noleggio) {
		this.noleggio = noleggio;
		this.veicolo = noleggio.getVeicolo();
		label_tariffa_did.setVisible(true);
		label_tariffa_euro.setVisible(true);
		label_km_cliente.setVisible(true);
		label_tariffa_euro.setText(Double.toString(noleggio.getVeicolo().getTariffa_chilometraggio()) + "€");
		text_set_km.setVisible(true);
		bottone_costo.setVisible(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		text_set_km.setVisible(false);
		bottone_pagamento.setVisible(false);
		label_costo_tot.setVisible(false);
		label_successo.setVisible(false);
		label_tariffa_did.setVisible(false);
		label_tariffa_euro.setVisible(false);
		bottone_costo.setVisible(false);
		label_km_cliente.setVisible(false);
		label_errore.setVisible(false);

		id_pagamento.setStyle("-fx-alignment: CENTER;");
		cliente_pagamento.setStyle("-fx-alignment: CENTER;");
		veicolo_pagamento.setStyle("-fx-alignment: CENTER;");
		data_in_pagamento.setStyle("-fx-alignment: CENTER;");
		data_fi_pagamento.setStyle("-fx-alignment: CENTER;");
		azioni_pagamento.setStyle("-fx-alignment: CENTER;");

		id_pagamento.setCellValueFactory(new PropertyValueFactory<Noleggio, Integer>("ID"));
		data_in_pagamento
				.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("data_inizio_noleggio"));
		data_fi_pagamento.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("data_fine_noleggio"));
		cliente_pagamento.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Noleggio, String> tmp) {
						if (tmp.getValue().getCliente() == null) {
							return new SimpleStringProperty();
						} else {
							return new SimpleStringProperty(tmp.getValue().getCliente().getUsername());
						}
					}
				});
		veicolo_pagamento.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Noleggio, String> tmp) {
						if (tmp.getValue().getVeicolo() == null) {
							return new SimpleStringProperty();
						} else {
							return new SimpleStringProperty(tmp.getValue().getVeicolo().getModello());
						}
					}
				});
		azioni_pagamento.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Noleggio, Button> param) {
						final Button dettagliButton = new Button("Registra");
						dettagliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								mostraCose(param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(dettagliButton);
					}
				});
		List<Noleggio> lista = null;
		try {
			lista = noleggioservice.FindNoleggiByPagamento();
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		ObservableList<Noleggio> lista1 = FXCollections.observableList(lista);
		tabella_pagamento.setItems(lista1);
	}

}
