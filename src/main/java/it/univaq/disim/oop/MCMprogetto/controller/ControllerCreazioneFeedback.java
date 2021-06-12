package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackService;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Feedback;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;


public class ControllerCreazioneFeedback implements Initializable, DataInitializable <Utente, Veicolo>
{
	private ViewDispatcher dispatcher;

	@FXML
	private Label label_nome;
	@FXML
	private TextField recensione_txtfield;
	@FXML
	private ImageView vuota_1;
	@FXML
	private ImageView vuota_2;
	@FXML
	private ImageView vuota_3;
	@FXML
	private ImageView vuota_4;
	@FXML
	private ImageView vuota_5;
	@FXML
	private Button invia_bottone;
	@FXML
	private ImageView piena_1;
	@FXML
	private ImageView piena_2;
	@FXML
	private ImageView piena_3;
	@FXML
	private ImageView piena_4;
	@FXML
	private ImageView piena_5;
	   
	Veicolo veicolo;
	Utente utente;
	VeicoloService veicoloservice;
	FeedbackService feedbackservice;
	  
	private int tmp=0;
	  
	   
	public ControllerCreazioneFeedback()
	{
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		veicoloservice = factory.getVeicoloService();
		feedbackservice = factory.getFeedbackService();
		
	}
	   
	    
	    
	@FXML	// INVIA FEEDBACK
	void inviafeedback(MouseEvent event)
	{
		try 
		{
		Feedback feedback = new Feedback();
		feedback.setFeedback(recensione_txtfield.getText());
		feedback.setStelle(tmp);
		feedback.setUtente(utente);
		feedback.setVeicolo(veicolo);
		feedbackservice.createFeedback(feedback);
		dispatcher.renderView("rilascio_feedback", utente, null);
		}
		catch (Exception e)
		{ e.printStackTrace();  }
		
	}

	    
	    
//	    											GESTIONE STELLE
	    
	    
	    @FXML
	    void show1(MouseEvent event) {
	    
	    	piena_1.setVisible(true);
	    	vuota_1.setVisible(false);
	    	piena_2.setVisible(false);
	    	vuota_2.setVisible(true);
	    	piena_3.setVisible(false);
	    	vuota_3.setVisible(true);
	    	piena_4.setVisible(false);
	    	vuota_4.setVisible(true);
	    	piena_5.setVisible(false);
	    	vuota_5.setVisible(true);
	    	tmp=1;
	    	System.out.println(tmp);

	    }

	    @FXML
	    void show2(MouseEvent event) {
	    	
	    	piena_1.setVisible(true);
	    	vuota_1.setVisible(false);
	    	piena_2.setVisible(true);
	    	vuota_2.setVisible(false);
	    	piena_3.setVisible(false);
	    	vuota_3.setVisible(true);
	    	piena_4.setVisible(false);
	    	vuota_4.setVisible(true);
	    	piena_5.setVisible(false);
	    	vuota_5.setVisible(true);
	    	tmp=2;
	    	System.out.println(tmp);
	    }

	    @FXML
	    void show3(MouseEvent event) {
	    	
	    	piena_1.setVisible(true);
	    	vuota_1.setVisible(false);
	    	piena_2.setVisible(true);
	    	vuota_2.setVisible(false);
	    	piena_3.setVisible(true);
	    	vuota_3.setVisible(false);
	    	piena_4.setVisible(false);
	    	vuota_4.setVisible(true);
	    	piena_5.setVisible(false);
	    	vuota_5.setVisible(true);
	    	
	    	tmp=3;
	    	System.out.println(tmp);
	    	
	    }

	    @FXML
	    void show4(MouseEvent event) {
	   
	    	piena_1.setVisible(true);
	    	vuota_1.setVisible(false);
	    	piena_2.setVisible(true);
	    	vuota_2.setVisible(false);
	    	piena_3.setVisible(true);
	    	vuota_3.setVisible(false);
	    	piena_4.setVisible(true);
	    	vuota_4.setVisible(false);
	    	piena_5.setVisible(false);
	    	vuota_5.setVisible(true);
	  
	    	tmp=4;
	    	System.out.println(tmp);
	    	
	    }

	    @FXML
	    void show5(MouseEvent event) {
	    	
	    	piena_1.setVisible(true);
	    	vuota_1.setVisible(false);
	    	piena_2.setVisible(true);
	    	vuota_2.setVisible(false);
	    	piena_3.setVisible(true);
	    	vuota_3.setVisible(false);
	    	piena_4.setVisible(true);
	    	vuota_4.setVisible(false);
	    	piena_5.setVisible(true);
	    	vuota_5.setVisible(false);
	    	tmp=5;
	    	System.out.println(tmp);
	    	
	    }



		@Override
		public void initialize(URL location, ResourceBundle resources)
		{
			piena_1.setVisible(false);
			piena_2.setVisible(false);
			piena_3.setVisible(false);
			piena_4.setVisible(false);
			piena_5.setVisible(false);

		}



		@Override
		public void initializeData(Utente utente,Veicolo veicolo) 
		{
			this.veicolo = veicolo;
			this.utente = utente;
			label_nome.setText(veicolo.getModello());
		}
	    
	    
//	    											FINE GESTIONE STELLE
	    
	    
	    
}