package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ControllerAggModVeicolo implements Initializable, DataInitializable<Utente,Object>
{
	private ViewDispatcher dispatcher;
	private VeicoloService veicoloservice;
	private TipologiaService tipologiaservice;
	private Utente utente;
	
	@FXML
	private AnchorPane anchorpane;
	@FXML
	private ImageView sfondo;
	@FXML
	private Label label_aggiungi_veicolo;
	@FXML
	private ChoiceBox<String> choicebox_tipologia;
	@FXML
	private ChoiceBox<String> choicebox_alimentazione;
	@FXML	
	private TextField textfield_modello;
	@FXML
	private TextField textfield_targa;
	@FXML	
	private Label label_errore;
	@FXML
	private TextField textfield_km;
	@FXML
	private TextField textfield_giorno;
	@FXML
	private Button bottone_aggiungi;
	@FXML
	private Button bottone_annulla;
	@FXML
	private TextField textfield_KmTot;
	@FXML
	private ChoiceBox<String> choicebox_Stato;
	    
	
	public ControllerAggModVeicolo()
	{
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
    	veicoloservice = factory.getVeicoloService();
    	tipologiaservice = factory.getTipologiaService();
	}
	
	
	

    
    @FXML	// CREA VEICOLO
    void crea_veicolo(ActionEvent event) throws BusinessException
    {	try
    	{
    		Veicolo veicolo= new Veicolo();
        	
        	veicolo.setTarga(textfield_targa.getText());
        	veicolo.setTipologia(choicebox_tipologia.getValue());
        	veicolo.setModello(textfield_modello.getText());
        	veicolo.setAlimentazione(choicebox_alimentazione.getValue());
        	veicolo.setStato(choicebox_Stato.getValue());
        	switch(veicolo.getStato())
        	{
        		case "disponibile": veicolo.setManutenzione(false); veicolo.setDisponibilita(true);
        		case "non disponibile": veicolo.setManutenzione(true); veicolo.setDisponibilita(false);
        		case "in noleggio": veicolo.setManutenzione(false); veicolo.setDisponibilita(false);
        	}
        	veicolo.setChilometri(Integer.parseInt(textfield_KmTot.getText()));
        	veicolo.setTariffa_chilometraggio(Double.parseDouble(textfield_km.getText()));
        	veicolo.setTariffa_periodo(Double.parseDouble(textfield_giorno.getText()));
        	
        	veicoloservice.createVeicolo(veicolo);
        	dispatcher.renderView("vistaveicoli", utente, null);
    	}
    	catch(NumberFormatException e)	// praticamente se metto delle lettere dove ci vogliono solo dei numeri fa questo
    	{	label_errore.setText("ERRORE: Compila correttamente i campi !");	} // anzichè lanciare 56416541 eccezioni
    	catch(BusinessException e)
    	{	label_errore.setText("ERRORE: Veicolo già esistente");	}
    }

    
    @FXML	// TORNA INDIETRO
    void TornaIndietro(ActionEvent event)
    {	dispatcher.renderView("vistaveicoli", utente, null);    }
    
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		label_errore.setText("");
		bottone_aggiungi.disableProperty().bind(textfield_targa.textProperty().isEmpty()
						.or(textfield_modello.textProperty().isEmpty())
						.or(textfield_KmTot.textProperty().isEmpty())
						.or(textfield_km.textProperty().isEmpty())
						.or(textfield_giorno.textProperty().isEmpty()));
		
		try
		{	
			choicebox_tipologia.getItems().addAll(tipologiaservice.FindAllTipologia());
			choicebox_tipologia.setValue("berlina");
			choicebox_alimentazione.getItems().addAll(veicoloservice.GetListAlimentazioneVeicolo());
			choicebox_alimentazione.setValue("benzina");
			choicebox_Stato.getItems().addAll(veicoloservice.GetListStatoVeicolo());
			choicebox_Stato.setValue("disponibile");
		}
		catch (BusinessException e)
		{	e.printStackTrace();	}
		
		
	}





	@Override
	public void initializeData(Utente utente)
	{
		this.utente = utente;
	}
	
	
	
	

}



