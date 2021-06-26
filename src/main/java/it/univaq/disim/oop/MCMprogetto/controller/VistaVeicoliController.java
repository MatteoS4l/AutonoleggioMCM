package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Amministratore;
import it.univaq.disim.oop.MCMprogetto.domain.Operatore;
import it.univaq.disim.oop.MCMprogetto.domain.Stato;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class VistaVeicoliController implements Initializable, DataInitializable<Utente> {
	private ViewDispatcher dispatcher;
	private VeicoloService veicoloservice;
	private Utente utente;

	@FXML
	private TableView<Veicolo> tabellaVeicoli;
	@FXML
	private TableColumn<Veicolo, String> modelloVeicolo;
	@FXML
	private TableColumn<Veicolo, String> tipologiaVeicolo;
	@FXML
	private TableColumn<Veicolo, Double> prezzoKmVeicolo;
	@FXML
	private TableColumn<Veicolo, Double> prezzoGiornoVeicolo;
	@FXML
	private TableColumn<Veicolo, String> statoVeicolo;
	@FXML
	private TableColumn<Veicolo, Button> azioniVeicolo;
	@FXML
	private ComboBox<Stato> filtroStatoVeicolo;
	@FXML
	private Button aggiungiVeicoloButton;

	public VistaVeicoliController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		veicoloservice = factory.getVeicoloService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		modelloVeicolo.setStyle("-fx-alignment: CENTER;");
		tipologiaVeicolo.setStyle("-fx-alignment: CENTER;");
		prezzoKmVeicolo.setStyle("-fx-alignment: CENTER;");
		prezzoGiornoVeicolo.setStyle("-fx-alignment: CENTER;");
		statoVeicolo.setStyle("-fx-alignment: CENTER;");
		azioniVeicolo.setStyle("-fx-alignment: CENTER;");

		modelloVeicolo.setCellValueFactory(new PropertyValueFactory<>("modello"));
		tipologiaVeicolo.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
		prezzoKmVeicolo.setCellValueFactory(new PropertyValueFactory<>("tariffa_chilometraggio"));
		prezzoGiornoVeicolo.setCellValueFactory(new PropertyValueFactory<>("tariffa_periodo"));
		statoVeicolo.setCellValueFactory(new PropertyValueFactory<>("stato"));
		azioniVeicolo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Veicolo, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Veicolo, Button> param) {
						final Button veicoliButton = new Button("visualizza");
						veicoliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								if (utente instanceof Amministratore) {
									dispatcher.renderView("scheda_veicolo", param.getValue());
								}
								if (utente instanceof Operatore) {
									dispatcher.renderView("feedback", param.getValue());
								}
							}
						});
						return new SimpleObjectProperty<Button>(veicoliButton);
					}
				});
		filtroStatoVeicolo.getItems().addAll(Stato.values());
	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = LayoutController.utenteMemorizzato;
		if (utente instanceof Operatore) {
			aggiungiVeicoloButton.setVisible(false);
		}

		List<Veicolo> listaV = null;
		try {
			listaV = veicoloservice.findListaVeicoliByStato("Tutte");
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		ObservableList<Veicolo> listaV1 = FXCollections.observableList(listaV);
		tabellaVeicoli.setItems(listaV1);
	}

	@FXML // AGGIUNGI
	void AggiungiVeicolo(ActionEvent event) {
		dispatcher.renderView("agg_mod_veicolo", utente);
	}

	@FXML // FILTRA VEICOLI
	void FiltraVeicoli(ActionEvent event) {
		try {
			List<Veicolo> listaveicoli = veicoloservice.findListaVeicoliByStato(filtroStatoVeicolo.getValue().toString());
			ObservableList<Veicolo> listaV = FXCollections.observableList(listaveicoli);
			tabellaVeicoli.setItems(listaV);
		} catch (BusinessException e) {
			System.out.println("errore");
		}
	}

}
