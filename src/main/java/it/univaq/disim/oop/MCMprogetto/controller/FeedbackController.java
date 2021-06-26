package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackService;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.domain.Amministratore;
import it.univaq.disim.oop.MCMprogetto.domain.Cliente;
import it.univaq.disim.oop.MCMprogetto.domain.Feedback;
import it.univaq.disim.oop.MCMprogetto.domain.Operatore;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class FeedbackController implements Initializable, DataInitializable<Veicolo> {
	private ViewDispatcher dispatcher;
	private FeedbackService feedbackservice;
	private Veicolo veicolo;
	private Utente utente;

	@FXML
	private TableView<Feedback> tabellaFeedback;
	@FXML
	private TableColumn<Feedback, String> utenteTabella;
	@FXML
	private TableColumn<Feedback, String> feedbackTabella;
	@FXML
	private TableColumn<Feedback, Integer> ratingTabella;
	@FXML
	private Label label_veicolo;

	public FeedbackController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		feedbackservice = factory.getFeedbackService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		utenteTabella.setStyle("-fx-alignment: CENTER;");
		this.utente = LayoutController.utenteMemorizzato;

		feedbackTabella.setStyle("-fx-alignment: CENTER;");
		utenteTabella.setStyle("-fx-alignment: CENTER;");
		ratingTabella.setStyle("-fx-alignment: CENTER;");
		feedbackTabella.setCellValueFactory(new PropertyValueFactory<Feedback, String>("feedback"));
		utenteTabella.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Feedback, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Feedback, String> tmp) {
						return new SimpleStringProperty(tmp.getValue().getNoleggio().getCliente().getUsername());
					}
				});
		ratingTabella.setCellValueFactory(new PropertyValueFactory<Feedback, Integer>("stelle"));
	}

	@Override
	public void initializeData(Veicolo veicolo) {
		this.veicolo = veicolo;
		label_veicolo.setText("Feedback Di: " + veicolo.getModello());

		List<Feedback> listafeedback = null;
		try {
			listafeedback = feedbackservice.findAllFeedbackByVeicolo(veicolo);
		} catch (BusinessException e) {
			System.err.println(e.getMessage());
		}

		ObservableList<Feedback> listaFeed = FXCollections.observableList(listafeedback);
		tabellaFeedback.setItems(listaFeed);
	}

	@FXML // TORNA INDIETRO
	void TornaIndietro(MouseEvent event) {
		if (utente instanceof Amministratore) {
			dispatcher.renderView("scheda_veicolo", veicolo);
		}
		if (utente instanceof Operatore) {
			dispatcher.renderView("vistaveicoli", utente);
		}
		if (utente instanceof Cliente) {
			dispatcher.renderView("scheda_noleggio", veicolo);
		}
	}
}
