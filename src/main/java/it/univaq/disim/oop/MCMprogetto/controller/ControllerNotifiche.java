package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControllerNotifiche implements Initializable,DataInitializable<Noleggio, Assistenza>
{

    @FXML
    private TableView<Noleggio> tabella_notifiche_prenotazioni;
    @FXML
    private TableColumn<Noleggio, String> dettagli_prenotazioni;
    @FXML
    private TableView<Assistenza> tabella_notifiche_assistenza;
    @FXML
    private TableColumn<Assistenza, String> dettagli_assistenza;
    
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
	}

}

