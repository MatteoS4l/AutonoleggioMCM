package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


public class ControllerHome implements Initializable, DataInitializable<Utente,Object>
{
	@FXML
	private Label benvenutoLabel;

	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
		
		
	}


	@Override
	public void initializeData(Utente utente)
	{
		if(utente.getSesso() == 'M')
		{	benvenutoLabel.setText("Benvenuto " + utente.getNome() + " " + utente.getCognome());	}
		
		if(utente.getSesso() == 'F')
		{	benvenutoLabel.setText("Benvenuta " + utente.getNome() + " " + utente.getCognome());	}
		
		
		
	}
	
	

}
