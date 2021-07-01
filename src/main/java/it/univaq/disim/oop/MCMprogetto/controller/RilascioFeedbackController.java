package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class RilascioFeedbackController implements Initializable, DataInitializable<Utente> {
	private ViewDispatcher dispatcher;
	private NoleggioService noleggioservice;

	@FXML
	private TableView<Noleggio> tabellaFeedbackUtente;
	@FXML
	private TableColumn<Noleggio, Integer> idFeedbackUtente;
	@FXML
	private TableColumn<Noleggio, String> veicoloFeedbackUtente;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> dataInFeedbackUtente;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> dataFiFeedbackUtente;
	@FXML
	private TableColumn<Noleggio, Button> azioniFeedbackUtente;

	public RilascioFeedbackController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idFeedbackUtente.setStyle("-fx-alignment: CENTER;");
		veicoloFeedbackUtente.setStyle("-fx-alignment: CENTER;");
		dataInFeedbackUtente.setStyle("-fx-alignment: CENTER;");
		dataFiFeedbackUtente.setStyle("-fx-alignment: CENTER;");
		azioniFeedbackUtente.setStyle("-fx-alignment: CENTER;");

		idFeedbackUtente.setCellValueFactory(new PropertyValueFactory<Noleggio, Integer>("ID"));
		dataInFeedbackUtente
				.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("dataInizioNoleggio"));
		dataFiFeedbackUtente
				.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("dataFineNoleggio"));
		veicoloFeedbackUtente.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Noleggio, String> tmp) {
						return new SimpleStringProperty(tmp.getValue().getVeicolo().getModello());
					}
				});
		azioniFeedbackUtente.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Noleggio, Button> param) {
						final Button feedButton = new Button("Lascia feedback");
						feedButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("creazione_feedback", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(feedButton);
					}
				});
	}

	@Override
	public void initializeData(Utente utente) {
		try {
			List<Noleggio> listacompleta = new ArrayList<Noleggio>();
			for (Noleggio tmp : noleggioservice.findNoleggiByUtente(utente)) {
				if (tmp.getDataFineNoleggio().isBefore(LocalDate.now()) && !tmp.isFeedLasciato()) {
					listacompleta.add(tmp);
				}
			}
			ObservableList<Noleggio> listaNol3 = FXCollections.observableList(listacompleta);
			tabellaFeedbackUtente.setItems(listaNol3);
		} catch (BusinessException | NullPointerException a) {
			a.printStackTrace();
		}
	}
}
