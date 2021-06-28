package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class NotificheController implements Initializable, DataInitializable<Utente> {
	private NoleggioService noleggioservice;

	@FXML
	private TableView<Noleggio> tabellaNotifichePrenotazioni;
	@FXML
	private TableColumn<Noleggio, String> veicoloPrenotazioni;
	@FXML
	private TableColumn<Noleggio, String> dettagliPrenotazioni;
	@FXML
	private TableView<Noleggio> tabellaNotificheAssistenza;
	@FXML
	private TableColumn<Noleggio, String> dettagliAssistenza;
	@FXML
	private TableColumn<Noleggio, String> veicoloAssistenza;

	public NotificheController() {
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dettagliPrenotazioni.setCellValueFactory(new PropertyValueFactory<Noleggio, String>("dettagli"));
		veicoloPrenotazioni.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Noleggio, String> tmp) {
						return new SimpleStringProperty(tmp.getValue().getVeicolo().getModello());
					}
				});
		dettagliAssistenza.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Noleggio, String> tmp) {
						for (Assistenza ass : tmp.getValue().getListaAssistenza()) {
							return new SimpleStringProperty(ass.getRisposta());
						}
						return null;
					}
				});
		veicoloAssistenza.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Noleggio, String> tmp) {
						return new SimpleStringProperty(tmp.getValue().getVeicolo().getModello());
					}
				});
	}

	@Override
	public void initializeData(Utente utente) {
		try {
			List<Noleggio> listacompleta = new ArrayList<Noleggio>();
			for (Noleggio tmp : noleggioservice.findNoleggiByUtente(utente)) {
				if (tmp.getDettagli() != null) {
					listacompleta.add(tmp);
				}
			}
			ObservableList<Noleggio> listaNol1 = FXCollections.observableList(listacompleta);
			tabellaNotifichePrenotazioni.setItems(listaNol1);
		} catch (BusinessException e) {
			System.err.println(e.getMessage());
		}

		try {
			List<Noleggio> listacompleta = new ArrayList<Noleggio>();
			for (Noleggio tmp : noleggioservice.findAllNoleggi()) {
				if (!tmp.getListaAssistenza().isEmpty()) {
					for (Assistenza ass : tmp.getListaAssistenza()) {
						if (ass.getRisposta() != null) {
							listacompleta.add(tmp);
						}
					}
				}
			}
			ObservableList<Noleggio> listaNol2 = FXCollections.observableList(listacompleta);
			tabellaNotificheAssistenza.setItems(listaNol2);
		} catch (BusinessException e) {
			System.err.println(e.getMessage());
		}
	}

}
