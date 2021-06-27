package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Alimentazione;
import it.univaq.disim.oop.MCMprogetto.domain.Stato;
import it.univaq.disim.oop.MCMprogetto.domain.Tipologia;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SchedaVeicoloController implements Initializable, DataInitializable<Veicolo> {
	private ViewDispatcher dispatcher;
	private VeicoloService veicoloservice;
	private TipologiaService tipologiaservice;
	private Veicolo veicolo;

	@FXML
	private TextField modello;
	@FXML
	private ChoiceBox<Alimentazione> alimentazione;
	@FXML
	private TextField targa;
	@FXML
	private TextField kilometraggio;
	@FXML
	private TextField tariffaKm;
	@FXML
	private TextField tariffaGiorno;
	@FXML
	private ChoiceBox<Stato> stato;
	@FXML
	private ChoiceBox<Tipologia> tipologia;
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
			stato.getItems().addAll(Stato.values());
			tipologia.getItems().addAll(tipologiaservice.findAllTipologia());//// forse  è sbagliato
			alimentazione.getItems().addAll(Alimentazione.values());

			modificaButton.disableProperty()
					.bind(modello.textProperty().isEmpty()
							.or(targa.textProperty().isEmpty()).or(kilometraggio.textProperty().isEmpty())
							.or(tariffaKm.textProperty().isEmpty()).or(tariffaGiorno.textProperty().isEmpty()));
		} catch (BusinessException e) {
			e.printStackTrace();
			labelErrore.setText(e.getMessage());
		}
	}

	@Override
	public void initializeData(Veicolo veicolo) {
		this.veicolo = veicolo;

		targa.setText(veicolo.getTarga());
		modello.setText(veicolo.getModello());
		kilometraggio.setText(String.valueOf(veicolo.getChilometri()));
		tipologia.setValue(veicolo.getTipologia());
		alimentazione.setValue(veicolo.getAlimentazione());
		stato.setValue(veicolo.getStato());
		tariffaKm.setText(String.valueOf(veicolo.getTariffaChilometraggio()));
		tariffaGiorno.setText(String.valueOf(veicolo.getTariffaPeriodo()));
	}

	@FXML // MODIFICA
	void ModificaVeicolo(ActionEvent event) throws BusinessException {
		Veicolo veicoloModificato = new Veicolo();

		veicoloModificato.setTarga(targa.getText());
		veicoloModificato.setModello(modello.getText());
		veicoloModificato.setChilometri(Integer.parseInt(kilometraggio.getText()));
		veicoloModificato.setTipologia(tipologia.getValue());
		veicoloModificato.setAlimentazione(alimentazione.getValue());
		veicoloModificato.setStato(stato.getValue());
		veicoloModificato.setTariffaChilometraggio(Double.parseDouble(tariffaKm.getText()));
		veicoloModificato.setTariffaPeriodo(Double.parseDouble(tariffaGiorno.getText()));

		veicoloservice.updateVeicolo(veicoloModificato);
		dispatcher.renderView("vistaveicoli", null);
	}

	@FXML // ELIMINA
	void EliminaVeicolo(ActionEvent event) throws BusinessException {
		try {
			veicoloservice.deleteVeicolo(veicolo);
			dispatcher.renderView("vistaveicoli", null);
		} catch (VeicoloNonTrovatoException e) {
			labelErrore.setVisible(true);
		}
	}

	@FXML // VEDI FEEDBACK
	void VisualizzaFeedback(ActionEvent event) {
		dispatcher.renderView("feedback", veicolo);
	}

}
