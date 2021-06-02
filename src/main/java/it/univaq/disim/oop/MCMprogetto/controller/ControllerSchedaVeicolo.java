package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaNonTrovataException;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.fxml.Initializable;

public class ControllerSchedaVeicolo implements Initializable, DataInitializable<Veicolo,Utente>
{
	private ViewDispatcher dispatcher;
	private VeicoloService veicoloservice;
	private TipologiaService tipologiaservice;
	private Veicolo veicolo;
	private Utente utente;
	
    @FXML
    private TextField tf_modello;
    @FXML
    private TextField tf_alimentazione;
    @FXML
    private TextField tf_targa;
    @FXML
    private TextField tf_kilometraggio;
    @FXML
    private TextField tf_tariffaKM;
    @FXML
    private TextField tf_tariffaGiorno;
    @FXML
    private ChoiceBox<String> tf_stato;
    @FXML
    private ChoiceBox<String> tf_tipologia;
    @FXML
    private Button modifica_button;
    @FXML
    private Button elimina_button;

    
    public ControllerSchedaVeicolo()
    {
    	dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
    	veicoloservice = factory.getVeicoloService();
    	tipologiaservice = factory.getTipologiaService();
    }
    
    
    @FXML	// MODIFICA
    void ModificaVeicolo(ActionEvent event) throws VeicoloNonTrovatoException
    {
    	Veicolo veicolo_modificato = new Veicolo();
    	
    	veicolo_modificato.setID(veicolo.getID());
    	veicolo_modificato.setModello(tf_modello.getText());
    	veicolo_modificato.setTarga(tf_targa.getText());
    	veicolo_modificato.setTipologia(tf_tipologia.getValue());
    	veicolo_modificato.setAlimentazione(tf_alimentazione.getText());
    	veicolo_modificato.setChilometri(Integer.parseInt(tf_kilometraggio.getText()));
    	veicolo_modificato.setTariffa_chilometraggio(Double.parseDouble(tf_tariffaKM.getText()));
    	veicolo_modificato.setTariffa_periodo(Double.parseDouble(tf_tariffaGiorno.getText()));
    	veicolo_modificato.setStato(tf_stato.getValue());
    	switch(veicolo_modificato.getStato())
    	{
    		case "disponibile": veicolo_modificato.setManutenzione(false); veicolo_modificato.setDisponibilita(true);
    		case "non disponibile": veicolo_modificato.setManutenzione(true); veicolo_modificato.setDisponibilita(false);
    		case "in noleggio": veicolo_modificato.setManutenzione(false); veicolo_modificato.setDisponibilita(false);
    	}
    	
    	veicoloservice.updateVeicolo(veicolo_modificato);
    	dispatcher.renderView("vistaveicoli", utente, null);
    }
    
    
    
    @FXML	// ELIMINA
    void EliminaVeicolo(ActionEvent event) throws VeicoloNonTrovatoException
    {
    	veicoloservice.deleteVeicolo(veicolo);
    	dispatcher.renderView("vistaveicoli", utente, null);
    }
    
    @FXML	// VEDI FEEDBACK
    void VisualizzaFeedback(ActionEvent event) 
    {	dispatcher.renderView("feedback", veicolo, utente);    }

    
	

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		tf_stato.getItems().addAll(veicoloservice.GetListStatoVeicolo());
		try
		{	tf_tipologia.getItems().addAll(tipologiaservice.getAllTipologia());		}
		catch (TipologiaNonTrovataException e)
		{	e.printStackTrace();	}
		
		modifica_button.disableProperty().bind(tf_modello.textProperty().isEmpty()
				.or(tf_alimentazione.textProperty().isEmpty())
				.or(tf_targa.textProperty().isEmpty())
				.or(tf_kilometraggio.textProperty().isEmpty())
				.or(tf_tariffaKM.textProperty().isEmpty())
				.or(tf_tariffaGiorno.textProperty().isEmpty()));
		
	}

	
	
	
	@Override
	public void initializeData(Veicolo veicolo, Utente utente)
	{
		this.veicolo = veicolo;
		this.utente = utente;
		
		tf_modello.setText(veicolo.getModello());
		tf_tipologia.setValue(veicolo.getTipologia());
		tf_stato.setValue(veicolo.getStato());
		tf_alimentazione.setText(veicolo.getAlimentazione());
		tf_targa.setText(veicolo.getTarga());
		tf_kilometraggio.setText(String.valueOf(veicolo.getChilometri()));
		tf_tariffaKM.setText(String.valueOf(veicolo.getTariffa_chilometraggio()));
		tf_tariffaGiorno.setText(String.valueOf(veicolo.getTariffa_periodo()));
	}
	
	
	
	
	
	
	
	
	
	

}
