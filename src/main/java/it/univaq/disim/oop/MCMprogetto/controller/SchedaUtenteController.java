package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.domain.Cliente;
import it.univaq.disim.oop.MCMprogetto.domain.Operatore;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SchedaUtenteController implements Initializable, DataInitializable<Utente, Object> {
	private UtenteService utenteservice;
	private ViewDispatcher dispatcher;
	private Utente utente;

	@FXML
	private Label label_errore;
	@FXML
	private TextField nome;
	@FXML
	private TextField username;
	@FXML
	private TextField cognome;
	@FXML
	private TextField password;
	@FXML
	private DatePicker data_nascita;
	@FXML
	private Button modifica_button;
	@FXML
	private ChoiceBox<Character> sesso;
	@FXML
	private ChoiceBox<String> tipologia;
	@FXML
	private Button elimina_button;

	public SchedaUtenteController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		utenteservice = factory.getUtenteService();
	}

	@FXML // MODIFICA
	void ConfermaModifica(ActionEvent event) throws BusinessException {
		Utente utente_modificato;
		if (tipologia.getValue().equalsIgnoreCase("cliente")) {
			utente_modificato = new Cliente();
		} else {
			utente_modificato = new Operatore();
		}
		try {
			utente_modificato.setEta(utenteservice.CalcolaEta(data_nascita.getValue()));
			utente_modificato.setID(utente.getID());
			utente_modificato.setNome(nome.getText());
			utente_modificato.setCognome(cognome.getText());
			utente_modificato.setUsername(username.getText());
			utente_modificato.setPassword(password.getText());
			utente_modificato.setData_nascita(data_nascita.getValue());
			utente_modificato.setSesso(sesso.getValue());

			utenteservice.updateUtente(utente_modificato);
			dispatcher.renderView("vistautenti", null, null);
		} catch (NullPointerException e) {
			label_errore.setText("Inserire la data");
		}
	}

	@FXML // ELIMINA
	void Elimina(ActionEvent event) throws BusinessException {
		utenteservice.deleteUtente(utente);
		dispatcher.renderView("vistautenti", null, null);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sesso.getItems().add('M');
		sesso.getItems().add('F');
		tipologia.getItems().add("cliente");
		tipologia.getItems().add("operatore");

		modifica_button.disableProperty().bind(nome.textProperty().isEmpty().or(cognome.textProperty().isEmpty())
				.or(username.textProperty().isEmpty()).or(password.textProperty().isEmpty()));

		elimina_button.disableProperty().bind(nome.textProperty().isEmpty().or(cognome.textProperty().isEmpty())
				.or(username.textProperty().isEmpty()).or(password.textProperty().isEmpty()));

	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = utente;

		nome.setText(utente.getNome());
		cognome.setText(utente.getCognome());
		username.setText(utente.getUsername());
		password.setText(utente.getPassword());
		data_nascita.setValue(utente.getData_nascita());
		sesso.setValue(utente.getSesso());
		if (utente instanceof Cliente) {
			tipologia.setValue("cliente");
		} else {
			tipologia.setValue("operatore");
		}

	}

}