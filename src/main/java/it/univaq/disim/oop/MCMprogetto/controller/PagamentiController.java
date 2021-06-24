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
	private TableView<Noleggio> tabellaPagamento;
	@FXML
	private TableColumn<Noleggio, Integer> idPagamento;
	@FXML
	private TableColumn<Noleggio, String> veicoloPagamento;
	@FXML
	private TableColumn<Noleggio, String> clientePagamento;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> dataInPagamento;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> dataFiPagamento;
	@FXML
	private TableColumn<Noleggio, Button> azioniPagamento;
	@FXML
	private TextField textSetKm;
	@FXML
	private Button bottoneCosto;
	@FXML
	private Label labelTariffaDid;
	@FXML
	private Label labelTariffaEuro;
	@FXML
	private Label labelCostoTot;
	@FXML
	private Button bottonePagamento;
	@FXML
	private Label labelSuccesso;
	@FXML
	private Label labelErrore;
	@FXML
	private Label labelKmCliente;

	public PagamentiController() {
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
		veicoloservice = factory.getVeicoloService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textSetKm.setVisible(false);
		bottonePagamento.setVisible(false);
		labelCostoTot.setVisible(false);
		labelSuccesso.setVisible(false);
		labelTariffaDid.setVisible(false);
		labelTariffaEuro.setVisible(false);
		bottoneCosto.setVisible(false);
		labelKmCliente.setVisible(false);
		labelErrore.setVisible(false);

		idPagamento.setStyle("-fx-alignment: CENTER;");
		clientePagamento.setStyle("-fx-alignment: CENTER;");
		veicoloPagamento.setStyle("-fx-alignment: CENTER;");
		dataInPagamento.setStyle("-fx-alignment: CENTER;");
		dataFiPagamento.setStyle("-fx-alignment: CENTER;");
		azioniPagamento.setStyle("-fx-alignment: CENTER;");

		idPagamento.setCellValueFactory(new PropertyValueFactory<Noleggio, Integer>("ID"));
		dataInPagamento.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("data_inizio_noleggio"));
		dataFiPagamento.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("data_fine_noleggio"));
		clientePagamento.setCellValueFactory(
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
		veicoloPagamento.setCellValueFactory(
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
		azioniPagamento.setCellValueFactory(
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
		tabellaPagamento.setItems(lista1);
	}

	@FXML // VEDI COSTO
	void vediCosto(ActionEvent event) {
		try {
			double i = noleggio.getVeicolo().getTariffa_chilometraggio() * Double.parseDouble(textSetKm.getText());
			i /= 1000;
			labelCostoTot.setText(Double.toString(i) + "€");
			labelCostoTot.setVisible(true);
			bottonePagamento.setVisible(true);
		} catch (NumberFormatException e) {
			labelErrore.setVisible(true);
		}
	}

	@FXML // REGISTRA PAGAMENTO
	void registraPagamento(MouseEvent event) throws BusinessException, InterruptedException {
		try {
			double i = noleggio.getVeicolo().getTariffa_chilometraggio() * Double.parseDouble(textSetKm.getText());
			noleggio.setPrezzo_noleggio(i / 1000);
			noleggioservice.updateNoleggio(noleggio);
			labelSuccesso.setVisible(true);
			veicolo.setChilometri(veicolo.getChilometri() + Integer.parseInt(textSetKm.getText()));
			veicoloservice.updateVeicolo(veicolo);

			initialize(null, null);
		} catch (NumberFormatException e) {
			labelErrore.setVisible(true);
		}
	}

	// MOSTRA COSE
	void mostraCose(Noleggio noleggio) {
		this.noleggio = noleggio;
		this.veicolo = noleggio.getVeicolo();
		labelTariffaDid.setVisible(true);
		labelTariffaEuro.setVisible(true);
		labelKmCliente.setVisible(true);
		labelTariffaEuro.setText(Double.toString(noleggio.getVeicolo().getTariffa_chilometraggio()) + "€");
		textSetKm.setVisible(true);
		bottoneCosto.setVisible(true);
	}

}
