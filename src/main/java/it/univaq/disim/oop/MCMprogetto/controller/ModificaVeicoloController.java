package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Alimentazione;
import it.univaq.disim.oop.MCMprogetto.domain.Stato;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModificaVeicoloController implements Initializable, DataInitializable<Utente, Object> {
	private ViewDispatcher dispatcher;
	private VeicoloService veicoloservice;
	private TipologiaService tipologiaservice;
	private Utente utente;

	@FXML
	private Label labelAggiungiVeicolo;
	@FXML
	private ChoiceBox<String> choiceboxTipologia;
	@FXML
	private ChoiceBox<Alimentazione> choiceboxAlimentazione;
	@FXML
	private TextField textfieldModello;
	@FXML
	private TextField textfieldTarga;
	@FXML
	private Label labelErrore;
	@FXML
	private TextField textfieldKm;
	@FXML
	private TextField textfieldGiorno;
	@FXML
	private Button bottoneAggiungi;
	@FXML
	private Button bottoneAnnulla;
	@FXML
	private TextField textfieldKmTot;
	@FXML
	private ChoiceBox<Stato> choiceboxStato;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelErrore.setText("");
		bottoneAggiungi.disableProperty()
				.bind(textfieldTarga.textProperty().isEmpty().or(textfieldModello.textProperty().isEmpty())
						.or(textfieldKmTot.textProperty().isEmpty()).or(textfieldKm.textProperty().isEmpty())
						.or(textfieldGiorno.textProperty().isEmpty()));

		try {
			choiceboxTipologia.getItems().addAll(tipologiaservice.findAllTipologia());
			choiceboxTipologia.setValue("berlina");
			choiceboxAlimentazione.getItems().addAll(veicoloservice.findListAlimentazioneVeicolo());
			choiceboxAlimentazione.setValue(Alimentazione.BENZINA);
			choiceboxStato.getItems().addAll(veicoloservice.findListStatoVeicolo());
			choiceboxStato.setValue(Stato.DISPONIBILE);
		} catch (BusinessException e) {
			e.printStackTrace();
		  }

	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = utente;
	}

	public ModificaVeicoloController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		veicoloservice = factory.getVeicoloService();
		tipologiaservice = factory.getTipologiaService();
	}

	@FXML // CREA VEICOLO
	void CreaVeicolo(ActionEvent event) throws BusinessException {
		try {
			Veicolo veicolo = new Veicolo();

			veicolo.setTarga(textfieldTarga.getText());
			veicolo.getTipologia().setNome(choiceboxTipologia.getValue());
			veicolo.setModello(textfieldModello.getText());
			veicolo.setAlimentazione(choiceboxAlimentazione.getValue());
			veicolo.setStato(choiceboxStato.getValue());
			switch (veicolo.getStato()) {
			case "disponibile":
				veicolo.setManutenzione(false);
				veicolo.setDisponibilita(true);
			case "non disponibile":
				veicolo.setManutenzione(true);
				veicolo.setDisponibilita(false);
			case "in noleggio":
				veicolo.setManutenzione(false);
				veicolo.setDisponibilita(false);
			}
			veicolo.setChilometri(Integer.parseInt(textfieldKmTot.getText()));
			veicolo.setTariffa_chilometraggio(Double.parseDouble(textfieldKm.getText()));
			veicolo.setTariffa_periodo(Double.parseDouble(textfieldGiorno.getText()));

			veicoloservice.createVeicolo(veicolo);
			dispatcher.renderView("vistaveicoli", utente, null);
		} catch (NumberFormatException e) // praticamente se metto delle lettere dove ci vogliono solo dei numeri fa
											// questo
		{
			labelErrore.setText("ERRORE: Compila correttamente i campi !");
		} // anzichè lanciare 56416541 eccezioni
		catch (BusinessException e) {
			labelErrore.setText("ERRORE: Veicolo già esistente");
		}
	}

	@FXML // TORNA INDIETRO
	void TornaIndietro(ActionEvent event) {
		dispatcher.renderView("vistaveicoli", utente, null);
	}

}
