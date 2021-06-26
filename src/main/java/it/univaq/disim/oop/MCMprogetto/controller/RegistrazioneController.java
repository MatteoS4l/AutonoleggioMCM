package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.domain.Amministratore;
import it.univaq.disim.oop.MCMprogetto.domain.Cliente;
import it.univaq.disim.oop.MCMprogetto.domain.Operatore;
import it.univaq.disim.oop.MCMprogetto.domain.Sesso;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class RegistrazioneController implements Initializable, DataInitializable<Utente> {
	private UtenteService utenteservice;
	private ViewDispatcher dispatcher;
	private Utente utente;

	@FXML
	private ImageView logo;
	@FXML
	private TextField nomeRegistrazione;
	@FXML
	private TextField cognomeRegistrazione;
	@FXML
	private TextField usernameRegistrazione;
	@FXML
	private PasswordField passwordRegistrazione;
	@FXML
	private PasswordField confermaPassRegistrazione;
	@FXML
	private DatePicker dataDiNascitaRegistrazione;
	@FXML
	private Button confermaRegistrazione;
	@FXML
	private ChoiceBox<Sesso> sessoRegistrazione;
	@FXML
	private ChoiceBox<String> tipologiaRegistrazione;
	@FXML
	private Label labelErrore;

	public RegistrazioneController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		utenteservice = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (utente instanceof Amministratore) {
			logo.setVisible(false);
		}
		confermaRegistrazione.disableProperty()
				.bind(nomeRegistrazione.textProperty().isEmpty().or(cognomeRegistrazione.textProperty().isEmpty())
						.or(usernameRegistrazione.textProperty().isEmpty())
						.or(dataDiNascitaRegistrazione.promptTextProperty().isEmpty())
						.or(passwordRegistrazione.textProperty().isEmpty())
						.or(confermaPassRegistrazione.textProperty().isEmpty()));
	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = utente;

		sessoRegistrazione.getItems().add(Sesso.UOMO);
		sessoRegistrazione.getItems().add(Sesso.DONNA);
		sessoRegistrazione.setValue(Sesso.UOMO);
		tipologiaRegistrazione.getItems().add("Cliente");
		tipologiaRegistrazione.setValue("Cliente");
		if (utente instanceof Amministratore) {
			tipologiaRegistrazione.getItems().add("Operatore");
			logo.setVisible(false);
		}
	}

	@FXML // CONFERMA
	void ConfermaRegistrazione(ActionEvent event) throws BusinessException {
		try {
			Utente utente;

			if (tipologiaRegistrazione.getValue().equalsIgnoreCase("operatore")) {
				utente = new Operatore();
			} else {
				utente = new Cliente();
			}
			utente.setNome(nomeRegistrazione.getText());
			utente.setCognome(cognomeRegistrazione.getText());
			utente.setUsername(usernameRegistrazione.getText());
			utente.setPassword(passwordRegistrazione.getText());
			utente.setDataNascita(dataDiNascitaRegistrazione.getValue());
			utente.setSesso(sessoRegistrazione.getValue());

			if(!passwordRegistrazione.getText().equals(confermaPassRegistrazione.getText())) {
				labelErrore.setText("Le password non corrispondono!");
			} else {
				try {
					utenteservice.createUtente(utente);
				} catch (BusinessException e) {
					e.printStackTrace();
					labelErrore.setText(e.getMessage());
				}
				TornaIndietro(null);
			}

		} catch (NullPointerException e) {
			labelErrore.setText("Inserire la data");
		}
	}

	@FXML // ANNULLA
	void TornaIndietro(ActionEvent event) {
		if (utente instanceof Amministratore) {
			dispatcher.renderView("vistautenti", utente);
		} else {
			dispatcher.logout();
		}
	}
}
