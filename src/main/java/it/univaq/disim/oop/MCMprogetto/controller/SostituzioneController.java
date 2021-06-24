package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class SostituzioneController implements Initializable, DataInitializable<Utente, Assistenza> {
	private VeicoloService veicoloservice;
	private NoleggioService noleggioservice;
	private AssistenzaService assistenzaservice;
	private Noleggio noleggio;
	private Assistenza assistenza;
	private Veicolo veicolo;

	@FXML
	private TableView<Veicolo> tabella_sos;
	@FXML
	private TableColumn<Veicolo, String> modello_veicolo_sos;
	@FXML
	private TableColumn<Veicolo, String> tipologia_veicolo_sos;
	@FXML
	private TableColumn<Veicolo, Double> tariffa_km__sos;
	@FXML
	private TableColumn<Veicolo, Double> tariffa_periodo_sos;
	@FXML
	private TableColumn<Veicolo, String> stato_veicolo_sos;
	@FXML
	private TableColumn<Veicolo, Button> azioni_sos;
	@FXML
	private DatePicker data_inizio_sos;
	@FXML
	private DatePicker data_fine_sos;
	@FXML
	private TextField invio_risposta_sos;
	@FXML
	private Button bottone_sos;
	@FXML
	private Label successo_sos;
	@FXML
	private Label label_inizio;
	@FXML
	private Label label_fine;

	public SostituzioneController() {
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		veicoloservice = factory.getVeicoloService();
		noleggioservice = factory.getNoleggioService();
		assistenzaservice = factory.getAssistenzaService();
	}

	@FXML // INVIA NOTIFICA
	void inviaNotificaSostituzione(ActionEvent event) {
		try {
			noleggio.setVeicolo(veicolo);
			noleggio.setServita(false);
			assistenza.setRisposta(invio_risposta_sos.getText());
			assistenza.setServita(true);
			noleggioservice.updateNoleggio(noleggio);
			assistenzaservice.updateAssistenza(assistenza);
			successo_sos.setVisible(true);

			data_inizio_sos.setValue(null);
			data_fine_sos.setValue(null);
			invio_risposta_sos.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// SCEGLI VEICOLO SOSTITUTIVO
	void creaNoleggioSostituzione(Veicolo veicolo) {
		this.veicolo = noleggio.getVeicolo();
		this.veicolo.setStato("non disponibile");
		try {
			veicoloservice.updateVeicolo(this.veicolo);
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		this.veicolo = veicolo;

		data_inizio_sos.setVisible(true);
		data_fine_sos.setVisible(true);
		invio_risposta_sos.setVisible(true);
		label_inizio.setVisible(true);
		label_fine.setVisible(true);
		bottone_sos.setVisible(true);

		data_inizio_sos.setValue(LocalDate.now());
		data_inizio_sos.setEditable(false);
		data_fine_sos.setValue(noleggio.getData_fine_noleggio().toLocalDate());
		data_fine_sos.setEditable(false);
		invio_risposta_sos.setText("Il tuo veicolo sostitutivo sarà disponibile dal "
				+ data_inizio_sos.getValue().toString() + " al " + data_fine_sos.getValue().toString());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Veicolo> listaveicoli = null;
		successo_sos.setVisible(false);
		data_inizio_sos.setVisible(false);
		data_fine_sos.setVisible(false);
		invio_risposta_sos.setVisible(false);
		label_inizio.setVisible(false);
		label_fine.setVisible(false);
		bottone_sos.setVisible(false);

		modello_veicolo_sos.setStyle("-fx-alignment: CENTER;");
		tipologia_veicolo_sos.setStyle("-fx-alignment: CENTER;");
		tariffa_km__sos.setStyle("-fx-alignment: CENTER;");
		tariffa_periodo_sos.setStyle("-fx-alignment: CENTER;");
		stato_veicolo_sos.setStyle("-fx-alignment: CENTER;");
		azioni_sos.setStyle("-fx-alignment: CENTER;");

		modello_veicolo_sos.setCellValueFactory(new PropertyValueFactory<>("modello"));
		tipologia_veicolo_sos.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
		tariffa_km__sos.setCellValueFactory(new PropertyValueFactory<>("tariffa_chilometraggio"));
		tariffa_periodo_sos.setCellValueFactory(new PropertyValueFactory<>("tariffa_periodo"));
		stato_veicolo_sos.setCellValueFactory(new PropertyValueFactory<>("stato"));
		azioni_sos.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Veicolo, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Veicolo, Button> param) {
						final Button dettagliButton = new Button("Scegli");
						dettagliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								creaNoleggioSostituzione(param.getValue());

							}
						});
						return new SimpleObjectProperty<Button>(dettagliButton);
					}
				});

		try {
			listaveicoli = veicoloservice.FindListaVeicoliByTipologia("sostitutivo");
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		ObservableList<Veicolo> listav = FXCollections.observableList(listaveicoli);
		tabella_sos.setItems(listav);
	}

	@Override
	public void initializeData(Utente utente, Assistenza assistenza) {
		this.assistenza = assistenza;

		try {
			this.noleggio = noleggioservice.FindNoleggioByIdAssistenza(assistenza.getID());
		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

}
