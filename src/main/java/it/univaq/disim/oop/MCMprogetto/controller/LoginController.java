package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.UtenteNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import it.univaq.disim.oop.MCMprogetto.view.ViewException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable, DataInitializable<Utente, Object> {
	private ViewDispatcher dispatcher;
	private Utente utente;
	private UtenteService utenteservice;

	@FXML
	private TextField usernameLogin;
	@FXML
	private PasswordField passwordLogin;
	@FXML
	private Label erroreLabel;
	@FXML
	private Button accediButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		erroreLabel.setVisible(false);
		accediButton.disableProperty()
				.bind(usernameLogin.textProperty().isEmpty().or(passwordLogin.textProperty().isEmpty()));
	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = utente;
	}

	public LoginController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		utenteservice = factory.getUtenteService();
	}

	@FXML // ACCEDI
	void Accesso(ActionEvent event) throws ViewException {
		try {
			Utente utente = utenteservice.Autenticazione(usernameLogin.getText(), passwordLogin.getText());
			dispatcher.loggedIn(utente);
		} catch (UtenteNonTrovatoException e) {
			erroreLabel.setText("Utente non trovato");
			erroreLabel.setVisible(true);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML // REGISTRATI
	void Registrati(ActionEvent event) {
		dispatcher.GetView("registrazione", utente, null);
	}

}
