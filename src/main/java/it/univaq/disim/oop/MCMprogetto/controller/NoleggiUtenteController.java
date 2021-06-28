package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class NoleggiUtenteController implements Initializable, DataInitializable<Utente> {
	private Noleggio noleggio;
	private AssistenzaService assistenzaservice;
	private NoleggioService noleggioservice;

	@FXML
	private TableView<Noleggio> tabellaNoleggi;
	@FXML
	private TableColumn<Noleggio, Integer> idNoleggio;
	@FXML
	private TableColumn<Noleggio, String> veicoloNoleggio;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> dataInizioNoleggio;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> dataFineNoleggio;
	@FXML
	private TableColumn<Noleggio, String> clienteNoleggio;
	@FXML
	private TableColumn<Noleggio, Button> azioniNoleggio;
	@FXML
	private TextField textDettagliRichiesta;
	@FXML
	private Label labelAvviso;
	@FXML
	private Button inviaButton;
	@FXML
	private Label labelErrore;

	public NoleggiUtenteController() {
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		assistenzaservice = factory.getAssistenzaService();
		noleggioservice = factory.getNoleggioService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelAvviso.setVisible(false);
		labelErrore.setVisible(false);
		textDettagliRichiesta.setVisible(false);
		inviaButton.setVisible(false);

		inviaButton.disableProperty().bind(textDettagliRichiesta.textProperty().isEmpty());

		idNoleggio.setStyle("-fx-alignment: CENTER;");
		dataInizioNoleggio.setStyle("-fx-alignment: CENTER;");
		dataFineNoleggio.setStyle("-fx-alignment: CENTER;");
		veicoloNoleggio.setStyle("-fx-alignment: CENTER;");
		clienteNoleggio.setStyle("-fx-alignment: CENTER;");
		azioniNoleggio.setStyle("-fx-alignment: CENTER;");

		idNoleggio.setCellValueFactory(new PropertyValueFactory<Noleggio, Integer>("ID"));
		dataInizioNoleggio.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("dataInizioNoleggio"));
		dataFineNoleggio.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("dataFineNoleggio"));
		clienteNoleggio.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Noleggio, String> tmp) {
						return new SimpleStringProperty(tmp.getValue().getCliente().getUsername());
					}
				});
		veicoloNoleggio.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Noleggio, String> tmp) {
						return new SimpleStringProperty(tmp.getValue().getVeicolo().getModello());
					}
				});
		azioniNoleggio.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Noleggio, Button> param) {
						final Button dettagliButton = new Button("Richiedi");
						dettagliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								try {
									RichiediAssistenza(param.getValue());
								} catch (BusinessException e) {
									e.printStackTrace();
								}
							}
						});
						return new SimpleObjectProperty<Button>(dettagliButton);
					}
				});
	}

	@Override
	public void initializeData(Utente utente) {
		List<Noleggio> listaNol = new ArrayList<Noleggio>();
		try {
			listaNol = noleggioservice.findNoleggiByUtente(utente);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		ObservableList<Noleggio> listaob = FXCollections.observableList(listaNol);
		tabellaNoleggi.setItems(listaob);
	}

	@FXML // INVIA RICHIESTA
	void InviaRichiesta(ActionEvent event) {
		try {
			Assistenza assistenza = new Assistenza();

			assistenza.setRichiesta(textDettagliRichiesta.getText());
			 assistenzaservice.createAssistenza(assistenza);

			this.noleggio.getListaAssistenza().add(assistenza);
			noleggioservice.updateNoleggio(noleggio);
			
			labelAvviso.setVisible(true);
			labelErrore.setVisible(false);
		} catch (BusinessException e) {
			labelErrore.setText(e.getMessage());
		}
	}

	// RICHIEDI
	private void RichiediAssistenza(Noleggio nol) throws BusinessException {
		this.noleggio=nol;
		textDettagliRichiesta.setVisible(true);
		inviaButton.setVisible(true);
		labelErrore.setVisible(false);
	}
}
