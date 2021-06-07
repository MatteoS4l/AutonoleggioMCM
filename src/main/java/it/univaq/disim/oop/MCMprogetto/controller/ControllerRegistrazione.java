package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.domain.Amministratore;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


public class ControllerRegistrazione implements Initializable, DataInitializable<Utente,Object>
{	
	private UtenteService utenteservice;
	private ViewDispatcher dispatcher;
	private Utente utente;

	@FXML
    private ImageView logo;
	@FXML
	private TextField nome_registrazione;
	@FXML
	private TextField cognome_registrazione;
	@FXML
	private TextField username_registrazione;
	@FXML
	private PasswordField password_registrazione;
	@FXML
	private PasswordField conferma_pass_registrazione;
	@FXML
	private DatePicker data_di_nascita_registrazione;
	@FXML
	private Button conferma_registrazione;
	@FXML
	private ChoiceBox<Character> sesso_registrazione;
	@FXML
	private ChoiceBox<String> tipologia_registrazione;
	@FXML
	private Label label_errore;

    
	public ControllerRegistrazione()
	{	
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
    	utenteservice = factory.getUtenteService();
	}
    
    
    @FXML	// CONFERMA
    void Conferma_Registrazione(ActionEvent event) throws BusinessException
    {	try
    	{
	    	Utente utente = null;
	    	
	    	if(tipologia_registrazione.getValue().equalsIgnoreCase("operatore"))
	    	{	utente = new Operatore();  	}
	    	utente = new Cliente();
	    	
	    	utente.setNome(nome_registrazione.getText());
	    	utente.setCognome(cognome_registrazione.getText());
	    	utente.setUsername(username_registrazione.getText());
	    	utente.setPassword(password_registrazione.getText());
	    	utente.setData_nascita(data_di_nascita_registrazione.getValue());
	    	utente.setEta(utenteservice.CalcolaEta(data_di_nascita_registrazione.getValue()));
	    	utente.setSesso(sesso_registrazione.getValue());
	    	
	    	if (!password_registrazione.getText().equals(conferma_pass_registrazione.getText())) 
	    	{ label_errore.setText("Le password non corrispondono!"); }
	    	else 
	    	{
	    		try
	    		{	utenteservice.createUtente(utente);	}
	    		catch (BusinessException e)
	    		{	e.printStackTrace();		}
		    	
		    	System.out.println("utente aggiunto, la sua età è: " + utente.getEta());
		    	Torna_Indietro(null);
	    	}
	    	
    	}
	    catch(NullPointerException e)
	    {	label_errore.setText("Inserire la data");	}
    }

    
    
    @FXML	// ANNULLA
    void Torna_Indietro(ActionEvent event)
    {	
    	if(utente instanceof Amministratore)
    	{	dispatcher.renderView("vistautenti", utente, null);	}
    	else
    	{	dispatcher.logout();	}
    }
    
    

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		if(utente instanceof Amministratore)
		{	logo.setVisible(false); 	}
		conferma_registrazione.disableProperty().bind(nome_registrazione.textProperty().isEmpty()
				.or(cognome_registrazione.textProperty().isEmpty())
				.or(username_registrazione.textProperty().isEmpty())
				.or(data_di_nascita_registrazione.promptTextProperty().isEmpty())
				.or(password_registrazione.textProperty().isEmpty())
				.or(conferma_pass_registrazione.textProperty().isEmpty()));
	}



	@Override
	public void initializeData(Utente utente)
	{
		this.utente = utente;
		
		sesso_registrazione.getItems().add('M');
		sesso_registrazione.getItems().add('F');
		sesso_registrazione.setValue('M');
		tipologia_registrazione.getItems().add("Cliente");
		tipologia_registrazione.setValue("Cliente");
		if(utente instanceof Amministratore)
		{
			System.out.println("deve stampare solo se entro con admin"); // non lo stampa quindi va bene
			tipologia_registrazione.getItems().add("Operatore");
			logo.setVisible(false);
		}
	}
	
	
	
	
}
