package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Stato;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class SostituzioneController implements Initializable, DataInitializable<Assistenza> {
	private NoleggioService noleggioservice;
	private VeicoloService veicoloservice;
	private ViewDispatcher dispatcher;
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
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
		veicoloservice = factory.getVeicoloService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
		tariffaKmSos.setCellValueFactory(new PropertyValueFactory<>("tariffaChilometraggio"));
		tariffaPeriodoSos.setCellValueFactory(new PropertyValueFactory<>("tariffaPeriodo"));
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
	}

	@Override
	public void initializeData(Assistenza assistenza) {
		this.assistenza = assistenza;
		this.noleggio = assistenza.getNoleggio();
		try {
			List<Veicolo> listaVeicoli = veicoloservice.findListaVeicoliByStato(Stato.DISPONIBILE);
			ObservableList<Veicolo> listOb = FXCollections.observableList(listaVeicoli);
			tabellaSos.setItems(listOb);
		} catch (BusinessException e) {
			System.err.println(e.getMessage());
		}
	}

	@FXML // INVIA NOTIFICA
	void InviaNotificaSostituzione(ActionEvent event) {
		assistenza.setRisposta(invioRispostaSos.getText());
		Noleggio nuovoNoleggio = new Noleggio();
		nuovoNoleggio.setVeicolo(veicolo);
		nuovoNoleggio.setCliente(noleggio.getCliente());
		nuovoNoleggio.setDataInizioNoleggio(LocalDate.now());
		nuovoNoleggio.setDataFineNoleggio(noleggio.getDataFineNoleggio());
		nuovoNoleggio.setPrezzoNoleggio(noleggio.getPrezzoNoleggio());
		nuovoNoleggio.setDettagli("Il veicolo sarà pronto per il ritiro il: " + LocalDate.now().plusDays(1).toString());

		try {
			noleggioservice.rispostaAssistenzaByNoleggio(nuovoNoleggio, assistenza);
		} catch (BusinessException e) {
			e.printStackTrace();
			successoSos.setText(e.getMessage());
		} finally {
			successoSos.setText("Sostituzione andata a buon fine!");
		}
		dispatcher.renderView("assistenza_operatore", LayoutController.utenteMemorizzato);

	}

	// SCEGLI VEICOLO SOSTITUTIVO
	void creaNoleggioSostituzione(Veicolo veicolo) {
		this.veicolo = veicolo;
		labelInizio.setVisible(true);
		labelFine.setVisible(true);
		bottoneSos.setVisible(true);
		invioRispostaSos.setVisible(true);
		dataFineSos.setVisible(true);
		dataInizioSos.setVisible(true);
		dataInizioSos.setValue(LocalDate.now());
		dataFineSos.setValue(assistenza.getNoleggio().getDataFineNoleggio());
	}

}
