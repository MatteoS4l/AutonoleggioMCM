package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ControllerSchedaNoleggio implements Initializable, DataInitializable<Veicolo,Utente>
{
	private ViewDispatcher dispatcher;
	private NoleggioService noleggioservice;
	private Veicolo veicolo;
	private Utente utente;
	private Noleggio noleggio;
	
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
    @FXML
    private Label label_preventivo;
    @FXML
    private CheckBox selezione_tariffa_periodo;
    @FXML
    private CheckBox selezione_tariffa_km;
    
    
    
    
    
    public ControllerSchedaNoleggio()
    {
    	dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
    	noleggioservice = factory.getNoleggioService();
    }
    

    @FXML	// CALCOLA PREVENTIVO 
    double calcolaPreventivo(ActionEvent event) throws BusinessException 
    {  	try
    	{	
    		if (selezione_tariffa_km.isSelected())
    		{ return 0;	}
    		else
    		{
    		long daysBetween = ChronoUnit.DAYS.between(data_inizio_nol.getValue(),  data_fine_nol.getValue());
    		label_preventivo.setText(String.valueOf(daysBetween * veicolo.getTariffa_periodo()));
    		label_preventivo.setVisible(true);
        	return Double.parseDouble(label_preventivo.getText());
    		}
    	}
    	catch(NullPointerException e)
    	{	label_preventivo.setTextFill(Color.RED);
			label_preventivo.setText("Inserisci correttamente le date !");
		}
    	
    	throw new BusinessException();

    }

    @FXML	// NOLEGGIA AUTO
    Noleggio noleggiaAuto(ActionEvent event)
    {	try
    	{
    		// ci va il metodo che vede se si può prenotare attraverso le date
        	
        	Noleggio noleggio = new Noleggio();
        
        	noleggio.setPrezzo_noleggio(calcolaPreventivo(null));
        	noleggio.setAssistenza(null);
        	noleggio.setCliente(utente);
        	noleggio.setVeicolo(veicolo);
        	noleggio.setData_inizio_noleggio(data_inizio_nol.getValue().atStartOfDay());
        	noleggio.setData_fine_noleggio(data_fine_nol.getValue().atStartOfDay());
        	noleggio.setKM_percorsi(0);
        	noleggio.setServita(false);
			noleggioservice.createNoleggio(noleggio);
			dispatcher.renderView("home", utente, null);
		} 
    	catch (BusinessException e)
    	{	label_preventivo.setTextFill(Color.RED);
			label_preventivo.setText("Inserisci correttamente le date !");	
		}
	return noleggio;

    }
    
    
    @FXML	// SELEZIONA PAGAMENTO
    void SelezionaPagamento(ActionEvent event) 
    {
    	if (selezione_tariffa_km.isSelected())
    	{
    		selezione_tariffa_periodo.disableProperty();
    		bottone_preventivo.setVisible(false);
    		label_preventivo.setVisible(false);
    	}
    	
    	if (selezione_tariffa_periodo.isSelected())
    	{
    		selezione_tariffa_km.disableProperty();
    		bottone_preventivo.setVisible(true);
    	}
    	

    }
    
    
    

    @FXML	//	FEEDBACK	
    void visualizzaFeed(ActionEvent event) 
    {	dispatcher.renderView("feedback", veicolo, utente);    }
    
    
    

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		

	}

	@Override
	public void initializeData(Veicolo veicolo, Utente utente) 
	{
		this.veicolo = veicolo;
		this.utente = utente;
		label_modello_macchina.setText(veicolo.getModello());
		label_preventivo.setVisible(false);
	}

	

}
