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
	private TableView<Veicolo> tabellaSos;
	@FXML
	private TableColumn<Veicolo, String> modelloVeicoloSos;
	@FXML
	private TableColumn<Veicolo, String> tipologiaVeicoloSos;
	@FXML
	private TableColumn<Veicolo, Double> tariffaKmSos;
	@FXML
	private TableColumn<Veicolo, Double> tariffaPeriodoSos;
	@FXML
	private TableColumn<Veicolo, String> statoVeicoloSos;
	@FXML
	private TableColumn<Veicolo, Button> azioniSos;
	@FXML
	private DatePicker dataInizioSos;
	@FXML
	private DatePicker dataFineSos;
	@FXML
	private TextField invioRispostaSos;
	@FXML
	private Button bottoneSos;
	@FXML
	private Label successoSos;
	@FXML
	private Label labelInizio;
	@FXML
	private Label labelFine;

	public SostituzioneController() {
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		veicoloservice = factory.getVeicoloService();
		noleggioservice = factory.getNoleggioService();
		assistenzaservice = factory.getAssistenzaService();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Veicolo> listaveicoli = null;
		successoSos.setVisible(false);
		dataInizioSos.setVisible(false);
		dataFineSos.setVisible(false);
		invioRispostaSos.setVisible(false);
		labelInizio.setVisible(false);
		labelFine.setVisible(false);
		bottoneSos.setVisible(false);

		modelloVeicoloSos.setStyle("-fx-alignment: CENTER;");
		tipologiaVeicoloSos.setStyle("-fx-alignment: CENTER;");
		tariffaKmSos.setStyle("-fx-alignment: CENTER;");
		tariffaPeriodoSos.setStyle("-fx-alignment: CENTER;");
		statoVeicoloSos.setStyle("-fx-alignment: CENTER;");
		azioniSos.setStyle("-fx-alignment: CENTER;");

		modelloVeicoloSos.setCellValueFactory(new PropertyValueFactory<>("modello"));
		tipologiaVeicoloSos.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
		tariffaKmSos.setCellValueFactory(new PropertyValueFactory<>("tariffa_chilometraggio"));
		tariffaPeriodoSos.setCellValueFactory(new PropertyValueFactory<>("tariffa_periodo"));
		statoVeicoloSos.setCellValueFactory(new PropertyValueFactory<>("stato"));
		azioniSos.setCellValueFactory(
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
		tabellaSos.setItems(listav);
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

	@FXML // INVIA NOTIFICA
	void inviaNotificaSostituzione(ActionEvent event) {
		try {
			noleggio.setVeicolo(veicolo);
			noleggio.setServita(false);
			assistenza.setRisposta(invioRispostaSos.getText());
			assistenza.setServita(true);
			noleggioservice.updateNoleggio(noleggio);
			assistenzaservice.updateAssistenza(assistenza);
			successoSos.setVisible(true);

			dataInizioSos.setValue(null);
			dataFineSos.setValue(null);
			invioRispostaSos.clear();
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

		dataInizioSos.setVisible(true);
		dataFineSos.setVisible(true);
		invioRispostaSos.setVisible(true);
		labelInizio.setVisible(true);
		labelFine.setVisible(true);
		bottoneSos.setVisible(true);

		dataInizioSos.setValue(LocalDate.now());
		dataInizioSos.setEditable(false);
		dataFineSos.setValue(noleggio.getData_fine_noleggio().toLocalDate());
		dataFineSos.setEditable(false);
		invioRispostaSos.setText("Il tuo veicolo sostitutivo sarà disponibile dal "
				+ dataInizioSos.getValue().toString() + " al " + dataFineSos.getValue().toString());
	}

	

}
