package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.domain.Amministratore;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class VistaUtentiController implements Initializable, DataInitializable<Utente, Object> {
	private ViewDispatcher dispatcher;
	private UtenteService utenteservice;
	private Utente utente;

	@FXML
	private TableView<Utente> tabellaUtenti;
	@FXML
	private TableColumn<Utente, Integer> idTabellaUtenti;
	@FXML
	private TableColumn<Utente, String> nomeTabellaUtenti;
	@FXML
	private TableColumn<Utente, String> cognomeTabellaUtenti;
	@FXML
	private TableColumn<Utente, Integer> etaTabellaUtenti;
	@FXML
	private TableColumn<Utente, Character> sessoTabellaUtenti;
	@FXML
	private TableColumn<Utente, Button> azioniTabellaUtenti;

	public VistaUtentiController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		utenteservice = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idTabellaUtenti.setStyle("-fx-alignment: CENTER;");
		nomeTabellaUtenti.setStyle("-fx-alignment: CENTER;");
		cognomeTabellaUtenti.setStyle("-fx-alignment: CENTER;");
		etaTabellaUtenti.setStyle("-fx-alignment: CENTER;");
		sessoTabellaUtenti.setStyle("-fx-alignment: CENTER;");
		azioniTabellaUtenti.setStyle("-fx-alignment: CENTER;");

		idTabellaUtenti.setCellValueFactory(new PropertyValueFactory<>("ID"));
		nomeTabellaUtenti.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cognomeTabellaUtenti.setCellValueFactory(new PropertyValueFactory<>("cognome"));
		etaTabellaUtenti.setCellValueFactory(new PropertyValueFactory<>("eta"));
		sessoTabellaUtenti.setCellValueFactory(new PropertyValueFactory<>("sesso"));
		azioniTabellaUtenti.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Utente, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Utente, Button> param) {
						final Button appelliButton = new Button("visualizza");
						appelliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("scheda_utente", param.getValue(), null);
							}
						});
						return new SimpleObjectProperty<Button>(appelliButton);
					}
				});
	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = utente;

		try {
			// utenteservice.primaAggiunta();
			List<Utente> listautenti = utenteservice.FindAllUtenti((Amministratore) utente);
			ObservableList<Utente> listaU = FXCollections.observableList(listautenti);
			tabellaUtenti.setItems(listaU);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void AggiungiUtente(ActionEvent event) {
		dispatcher.renderView("registrazione", utente, null);
	}

}
