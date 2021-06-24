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

public class LoginController implements Initializable, DataInitializable<Utente,Object>
{
	private ViewDispatcher dispatcher;
	private Utente utente;
	private UtenteService utenteservice;
	
	@FXML
    private TextField username_login;
    @FXML
    private PasswordField password_login;
    @FXML
    private Label errore_label;
    @FXML
    private Button accedi_button;
    
    
    public LoginController()
	{	
    	dispatcher = ViewDispatcher.getInstance();
    	MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
    	utenteservice = factory.getUtenteService();	
	}
	
	
	
    @FXML		//ACCEDI
    void Accesso(ActionEvent event) throws ViewException
    {  	try
    	{
			Utente utente = utenteservice.Autenticazione(username_login.getText(), password_login.getText());
			dispatcher.loggedIn(utente);
		}
    	catch (UtenteNonTrovatoException e)
    	{
			errore_label.setText("Utente non trovato");
			errore_label.setVisible(true);
		}
    	catch (BusinessException e)
    	{	dispatcher.renderError(e);	}
    }
    
    
    @FXML		// REGISTRATI
    void Registrati(ActionEvent event)
    {	dispatcher.GetView("registrazione", utente ,null);    }
    
    
    

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		errore_label.setVisible(false);
		accedi_button.disableProperty().bind(username_login.textProperty().isEmpty()
										.or(password_login.textProperty().isEmpty()));
	}
	
	
	
	@Override
	public void initializeData(Utente utente)
	{	this.utente = utente;	}


	

}
