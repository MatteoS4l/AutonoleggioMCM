package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackService;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
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
<<<<<<< HEAD
import javafx.scene.input.*;

public class ControllerCreazioneFeedback implements Initializable, DataInitializable <Veicolo, Utente> {

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
	    
	    private int tmp=0;
	    
	    
	    
	    @FXML
	    void inviafeedback(MouseEvent event) {

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
	    
	    
//	    											FINE GESTIONE STELLE
	    
	    
	    
	    

		@Override
		public void initialize (URL location, ResourceBundle resources) {
				 
			piena_1.setVisible(false);
			piena_2.setVisible(false);
			piena_3.setVisible(false);
			piena_4.setVisible(false);
			piena_5.setVisible(false);

			
		}
=======
import javafx.scene.input.MouseEvent;

public class ControllerCreazioneFeedback implements Initializable, DataInitializable<Veicolo, Utente> {
	
	private ViewDispatcher dispatcher;
	private Utente utente;
	private Veicolo veicolo;
	private FeedbackService feedbackservice;
	
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
    private ImageView piena_1;
    @FXML
    private ImageView piena_2;
    @FXML
    private ImageView piena_3;
    @FXML
    private ImageView piena_4;
    @FXML
    private ImageView piena_5;
    @FXML
    private Button invia_bottone;
    
    
    
    public ControllerCreazioneFeedback ()
    {
    	dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		feedbackservice = factory.getFeedbackService();
    }
    
    
    private int tmp_star = 0;        //variabile temporanea che poi si assegna ai feedback in inviafeedback(), serve in caso di modifica delle stelle nel mentre (poi vi spiego)
    
    
    
    
    
    
//            						INVIA FEEDBACK
    
    @FXML
    void inviafeedback(MouseEvent event) throws BusinessException 
    {
    	Feedback feedback = new Feedback();
    	
    	feedback.setUtente(utente);
    	feedback.setVeicolo(veicolo);
    	feedback.setUtente(utente);
    	feedback.setStelle(tmp_star);
    	feedback.setFeedback(recensione_txtfield.getText());
    	
    	feedbackservice.createFeedback(feedback);
    	
    	dispatcher.renderView("rilascio_feedback", utente, null);
    	
    }
    
    
    
    
    
    
//    								COMPARSA STELLE PIENE
    
    @FXML
    void show1(MouseEvent event) {
    	
    	piena_1.setVisible(true);
    	piena_2.setVisible(false);
    	piena_1.setVisible(false);
		piena_2.setVisible(false);
		piena_3.setVisible(false);
	
    }

    @FXML
    void show2(MouseEvent event) {
    	
    	piena_1.setVisible(true);
		piena_2.setVisible(true);
		piena_3.setVisible(false);
		piena_4.setVisible(false);
		piena_5.setVisible(false);
>>>>>>> 7642bde10d072dd588f673ecaec4dec12993e0c6
		
		
		@Override
		public void initializeData (Veicolo veicolo, Utente utente) {
			
				
			
			
		}
		
		
<<<<<<< HEAD
=======
    }

//    									FINE CONTROLLO COMPARSA
    
    
    
    
    	
//    									VALUTAZIONE STELLE
    						
    @FXML
    void valuta1(MouseEvent event) {	tmp_star=1;		}

    @FXML
    void valuta2(MouseEvent event) {	tmp_star=2;		}

    @FXML
    void valuta3(MouseEvent event) {	tmp_star=3;		}

    @FXML
    void valuta4(MouseEvent event) {	tmp_star=4;		}

    @FXML
    void valuta5(MouseEvent event) {	tmp_star=5;		}


//    									FINE CONTROLLO STELLE
    



	@Override
	public void initialize(URL location, ResourceBundle resources) {

		piena_1.setVisible(true);
		piena_2.setVisible(false);
		piena_3.setVisible(false);
		piena_4.setVisible(false);
		piena_5.setVisible(false);
		
		
	}
    
	@Override
	public void initializeData(Veicolo veicolo, Utente utente) 
	{
		this.veicolo = veicolo;
		this.utente = utente;
		
		
		
>>>>>>> 7642bde10d072dd588f673ecaec4dec12993e0c6
		
		
		

	}

    
    
    
    
    
    
    


