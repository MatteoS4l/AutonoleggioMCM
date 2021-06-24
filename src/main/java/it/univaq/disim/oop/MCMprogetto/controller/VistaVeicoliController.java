package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Amministratore;
import it.univaq.disim.oop.MCMprogetto.domain.Operatore;
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

public class VistaVeicoliController implements Initializable, DataInitializable<Utente, Object> {
	private ViewDispatcher dispatcher;
	private VeicoloService veicoloservice;
	private Utente utente;

	@FXML
	private TableView<Veicolo> tabella_veicoli;
	@FXML
	private TableColumn<Veicolo, String> modello_veicolo;
	@FXML
	private TableColumn<Veicolo, String> tipologia_veicolo;
	@FXML
	private TableColumn<Veicolo, Double> prezzo_km_veicolo;
	@FXML
	private TableColumn<Veicolo, Double> prezzo_giorno_veicolo;
	@FXML
	private TableColumn<Veicolo, String> stato_veicolo;
	@FXML
	private TableColumn<Veicolo, Button> azioni_veicolo;
	@FXML
	private ComboBox<String> filtro_stato_veicolo;
	@FXML
	private Button aggiungi_veicolo_button;

	public VistaVeicoliController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		veicoloservice = factory.getVeicoloService();
	}

	@FXML // AGGIUNGI
	void AggiungiVeicolo(ActionEvent event) {
		dispatcher.renderView("agg_mod_veicolo", utente, null);
	}

	@FXML // FILTRA VEICOLI
	void FiltraVeicoli(ActionEvent event) {
		try {
			List<Veicolo> listaveicoli = veicoloservice.FindListaVeicoliByStato(filtro_stato_veicolo.getValue());
			ObservableList<Veicolo> listaV = FXCollections.observableList(listaveicoli);
			tabella_veicoli.setItems(listaV);
		} catch (BusinessException e) {
			System.out.println("errore");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		modello_veicolo.setStyle("-fx-alignment: CENTER;");
		tipologia_veicolo.setStyle("-fx-alignment: CENTER;");
		prezzo_km_veicolo.setStyle("-fx-alignment: CENTER;");
		prezzo_giorno_veicolo.setStyle("-fx-alignment: CENTER;");
		stato_veicolo.setStyle("-fx-alignment: CENTER;");
		azioni_veicolo.setStyle("-fx-alignment: CENTER;");

		modello_veicolo.setCellValueFactory(new PropertyValueFactory<>("modello"));
		tipologia_veicolo.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
		prezzo_km_veicolo.setCellValueFactory(new PropertyValueFactory<>("tariffa_chilometraggio"));
		prezzo_giorno_veicolo.setCellValueFactory(new PropertyValueFactory<>("tariffa_periodo"));
		stato_veicolo.setCellValueFactory(new PropertyValueFactory<>("stato"));
		azioni_veicolo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Veicolo, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Veicolo, Button> param) {
						final Button veicoliButton = new Button("visualizza");
						veicoliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								if (utente instanceof Amministratore) {
									dispatcher.renderView("scheda_veicolo", param.getValue(), utente);
								}
								if (utente instanceof Operatore) {
									dispatcher.renderView("feedback", param.getValue(), utente);
								}
							}
						});
						return new SimpleObjectProperty<Button>(veicoliButton);
					}
				});
		try {
			filtro_stato_veicolo.getItems().addAll(veicoloservice.FindListStatoVeicolo());
			filtro_stato_veicolo.getItems().add("Tutte");
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		List<Veicolo> listaV = null;
		try {
			listaV = veicoloservice.FindListaVeicoliByStato("Tutte");
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		ObservableList<Veicolo> listaV1 = FXCollections.observableList(listaV);
		tabella_veicoli.setItems(listaV1);

	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = utente;
		if (utente instanceof Operatore) {
			aggiungi_veicolo_button.setVisible(false);
		}

	}

}
