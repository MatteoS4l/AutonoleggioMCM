package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ControllerVistaFeedback implements Initializable, DataInitializable<Veicolo, Utente> {

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
    
    
    private int tmp_star = 0;        //variabile temporanea che poi si assegna ai feedback in inviafeedback(), serve in caso di modifica delle stelle nel mentre (poi vi spiego)
    
    
    
    
    
    
//            						INVIA FEEDBACK
    
    @FXML
    void inviafeedback(MouseEvent event) {
    	
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
		
    }

    @FXML
    void show3(MouseEvent event) {
    	
    	piena_1.setVisible(true);
		piena_2.setVisible(true);
		piena_3.setVisible(true);
		piena_4.setVisible(false);
		piena_5.setVisible(false);
		
    }

    @FXML
    void show4(MouseEvent event) {
    	
    	piena_1.setVisible(true);
		piena_2.setVisible(true);
		piena_3.setVisible(true);
		piena_4.setVisible(true);
		piena_5.setVisible(false);
		
    }

    @FXML
    void show5(MouseEvent event) {
    	
    	piena_1.setVisible(true);
		piena_2.setVisible(true);
		piena_3.setVisible(true);
		piena_4.setVisible(true);
		piena_5.setVisible(true);
		
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

		piena_1.setVisible(false);
		piena_2.setVisible(false);
		piena_3.setVisible(false);
		piena_4.setVisible(false);
		piena_5.setVisible(false);
		
		
	}
    
	@Override
	public void initializeData(Veicolo veicolo, Utente utente) {
		
		
		
	}
    
    
    
    
    
    
    
    
    
    
    

}
