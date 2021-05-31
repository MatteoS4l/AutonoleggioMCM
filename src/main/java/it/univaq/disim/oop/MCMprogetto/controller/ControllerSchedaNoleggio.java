package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class ControllerSchedaNoleggio implements Initializable, DataInitializable<Veicolo,Utente>
{
	private ViewDispatcher dispatcher;
	private VeicoloService veicoloservice;
	private UtenteService utenteservice;
	private Veicolo veicolo;
	private Utente utente;

	
	@FXML
    private Button bottone_noleggio;
    @FXML
    private Label label_modello_macchina;
    @FXML
    private Button bottone_preventivo;
    @FXML
    private DatePicker data_inizio_nol;
    @FXML
    private DatePicker data_fine_nol;
    
    
    
    
    public ControllerSchedaNoleggio ()
    {
    	dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
    	veicoloservice = factory.getVeicoloService();
    	utenteservice = factory.getUtenteService();
    }
    

    @FXML
    void calcolaPreventivo(ActionEvent event) 
    {
    	

    }

    @FXML
    void noleggiaAuto(ActionEvent event) 
    {
    	LocalDate a = data_inizio_nol.getValue();
    	LocalDate b = data_fine_nol.getValue();
    	

    }

    @FXML
    void visualizzaFeed(ActionEvent event) 
    {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{

	}

	@Override
	public void initializeData(Veicolo veicolo, Utente utente) 
	{
		this.veicolo = veicolo;
		this.utente = utente;
	}

	

}
