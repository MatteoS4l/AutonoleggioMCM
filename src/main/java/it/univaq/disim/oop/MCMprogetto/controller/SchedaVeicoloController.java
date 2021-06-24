package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.fxml.Initializable;

public class SchedaVeicoloController implements Initializable, DataInitializable<Veicolo, Utente> {
	private ViewDispatcher dispatcher;
	private VeicoloService veicoloservice;
	private TipologiaService tipologiaservice;
	private Veicolo veicolo;
	private Utente utente;

	@FXML
	private TextField modello;
	@FXML
	private TextField alimentazione;
	@FXML
	private TextField targa;
	@FXML
	private TextField kilometraggio;
	@FXML
	private TextField tariffaKm;
	@FXML
	private TextField tariffaGiorno;
	@FXML
	private ChoiceBox<String> stato;
	@FXML
	private ChoiceBox<String> tipologia;
	@FXML
	private Button modificaButton;
	@FXML
	private Button eliminaButton;
	@FXML
	private Label labelErrore;

	public SchedaVeicoloController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		veicoloservice = factory.getVeicoloService();
		tipologiaservice = factory.getTipologiaService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelErrore.setVisible(false);
		try {
			stato.getItems().addAll(veicoloservice.FindListStatoVeicolo());
			tipologia.getItems().addAll(tipologiaservice.FindAllTipologia());

			modificaButton.disableProperty()
					.bind(modello.textProperty().isEmpty().or(alimentazione.textProperty().isEmpty())
							.or(targa.textProperty().isEmpty()).or(kilometraggio.textProperty().isEmpty())
							.or(tariffaKm.textProperty().isEmpty()).or(tariffaGiorno.textProperty().isEmpty()));
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initializeData(Veicolo veicolo, Utente utente) {
		this.veicolo = veicolo;
		this.utente = utente;

		modello.setText(veicolo.getModello());
		tipologia.setValue(veicolo.getTipologia());
		stato.setValue(veicolo.getStato());
		alimentazione.setText(veicolo.getAlimentazione());
		targa.setText(veicolo.getTarga());
		kilometraggio.setText(String.valueOf(veicolo.getChilometri()));
		tariffaKm.setText(String.valueOf(veicolo.getTariffa_chilometraggio()));
		tariffaGiorno.setText(String.valueOf(veicolo.getTariffa_periodo()));
	}

	@FXML // MODIFICA
	void ModificaVeicolo(ActionEvent event) throws BusinessException {
		Veicolo veicolo_modificato = new Veicolo();

		veicolo_modificato.setID(veicolo.getID());
		veicolo_modificato.setModello(modello.getText());
		veicolo_modificato.setTarga(targa.getText());
		veicolo_modificato.setTipologia(tipologia.getValue());
		veicolo_modificato.setAlimentazione(alimentazione.getText());
		veicolo_modificato.setChilometri(Integer.parseInt(kilometraggio.getText()));
		veicolo_modificato.setTariffa_chilometraggio(Double.parseDouble(tariffaKm.getText()));
		veicolo_modificato.setTariffa_periodo(Double.parseDouble(tariffaGiorno.getText()));
		veicolo_modificato.setStato(stato.getValue());
		switch (veicolo_modificato.getStato()) {
		case "disponibile":
			veicolo_modificato.setManutenzione(false);
			veicolo_modificato.setDisponibilita(true);
		case "non disponibile":
			veicolo_modificato.setManutenzione(true);
			veicolo_modificato.setDisponibilita(false);
		case "in noleggio":
			veicolo_modificato.setManutenzione(false);
			veicolo_modificato.setDisponibilita(false);
		}

		veicoloservice.updateVeicolo(veicolo_modificato);
		dispatcher.renderView("vistaveicoli", utente, null);
	}

	@FXML // ELIMINA
	void EliminaVeicolo(ActionEvent event) throws BusinessException {
		try {
			veicoloservice.deleteVeicolo(veicolo);
			dispatcher.renderView("vistaveicoli", utente, null);
		} catch (VeicoloNonTrovatoException e) {
			labelErrore.setVisible(true);
		}
	}

	@FXML // VEDI FEEDBACK
	void VisualizzaFeedback(ActionEvent event) {
		dispatcher.renderView("feedback", veicolo, utente);
	}

}
