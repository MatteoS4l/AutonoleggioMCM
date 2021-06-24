package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Operatore;
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
import javafx.util.Callback;

public class PrenotazioniOperatoreController implements Initializable, DataInitializable<Noleggio, Operatore> {
	private NoleggioService noleggioservice;
	private Noleggio noleggio;

	@FXML
	private TableView<Noleggio> tabella_prenotazioni;
	@FXML
	private TableColumn<Noleggio, String> cliente_pren;
	@FXML
	private TableColumn<Noleggio, String> veicolo_pren;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> data_inizio_pren;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> data_fine_pren;
	@FXML
	private TableColumn<Noleggio, Button> azioni_pren;
	@FXML
	private TextField text_riposta;
	@FXML
	private Button bottone_risposta;
	@FXML
	private Label label_risposta;

	public PrenotazioniOperatoreController() {
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
	}

	@FXML
	void InviaRisposta(ActionEvent event) throws BusinessException {

		noleggio.setDettagli(text_riposta.getText());
		noleggioservice.updateNoleggio(noleggio);
		label_risposta.setVisible(true);
		initialize(null, null);

	}

	void mostraCose(Noleggio noleggio) {
		this.noleggio = noleggio;
		text_riposta.setVisible(true);
		bottone_risposta.setVisible(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		label_risposta.setVisible(false);
		text_riposta.setVisible(false);
		bottone_risposta.setVisible(false);

		cliente_pren.setStyle("-fx-alignment: CENTER;");
		veicolo_pren.setStyle("-fx-alignment: CENTER;");
		data_inizio_pren.setStyle("-fx-alignment: CENTER;");
		data_fine_pren.setStyle("-fx-alignment: CENTER;");
		azioni_pren.setStyle("-fx-alignment: CENTER;");

		cliente_pren.setCellValueFactory(
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
		veicolo_pren.setCellValueFactory(
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
		data_inizio_pren.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("data_inizio_noleggio"));
		data_fine_pren.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("data_fine_noleggio"));
		azioni_pren.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Noleggio, Button> param) {
						final Button dettagliButton = new Button("Invia");
						dettagliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								mostraCose(param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(dettagliButton);
					}
				});

		List<Noleggio> listacompleta1 = null;
		try {
			listacompleta1 = noleggioservice.FindNoleggiByDettagli();
			for (Noleggio tmp : listacompleta1) {
				if (tmp.getData_inizio_noleggio().isBefore(LocalDateTime.now())) {
					listacompleta1.remove(tmp);
				}
			}
		} catch (BusinessException e) {
			/* lista vuota */ } catch (ConcurrentModificationException e) {
			System.out.println("errore");
		}

		ObservableList<Noleggio> listaNol1 = FXCollections.observableList(listacompleta1);
		tabella_prenotazioni.setItems(listaNol1);

	}

}
