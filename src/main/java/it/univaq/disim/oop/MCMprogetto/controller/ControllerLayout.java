package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.controller.MenuElement;
import it.univaq.disim.oop.MCMprogetto.domain.Amministratore;
import it.univaq.disim.oop.MCMprogetto.domain.Cliente;
import it.univaq.disim.oop.MCMprogetto.domain.Operatore;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class ControllerLayout implements Initializable, DataInitializable<Utente,Object>
{
	private ViewDispatcher dispatcher;
	private Utente utente;
	
	private static final MenuElement MENU_HOME = new MenuElement("Home", "home");
	
	private static final MenuElement[] MENU_ADMIN =
	{ 	
		new MenuElement("Lista utenti", "vistautenti"),
		new MenuElement("Lista veicoli", "vistaveicoli"),
		new MenuElement("Noleggi", "vistanoleggi"),
		new MenuElement("Tipologie veicoli", "vistatipologie")		
	};
	
	private static final MenuElement[] MENU_CLIENTE =
	{
		new MenuElement("Noleggia veicolo", "veicoli_utente"),
		new MenuElement("I miei noleggi", "noleggi_utente"),
		new MenuElement("Notifiche", "notifiche"),
		new MenuElement("Lascia un feedback", "rilascio_feedback")
	};
	
	private static final MenuElement[] MENU_OPERATORE =
	{
		new MenuElement("Lista Veicoli", "vistaveicoli"),
		new MenuElement("Gestione Noleggi","noleggi_operatore"),
		new MenuElement("Prenotazioni","prenotazioni_operatore"),
		new MenuElement("Centro assistenza", "assistenza_operatore")
		
	};
	
	
	
	@FXML
    private VBox barra_menu;
	
	
	
	
	public ControllerLayout()
	{	dispatcher = ViewDispatcher.getInstance();	}
	
	
	@FXML	// LOGOUT
    void Logout(MouseEvent event) 
	{	dispatcher.logout();    }
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
	}
	
	
	@Override
	public void initializeData(Utente utente)
	{
		System.out.println(utente.getNome());
		this.utente = utente;
		barra_menu.getChildren().add(createButton(MENU_HOME));
		barra_menu.getChildren().add(new Separator());
		
		if(utente instanceof Amministratore)
		{
			for (MenuElement menuElement : MENU_ADMIN)
			{	barra_menu.getChildren().add(createButton(menuElement));	}
		}
		
		if(utente instanceof Operatore)
		{
			for (MenuElement menuElement : MENU_OPERATORE)
			{	barra_menu.getChildren().add(createButton(menuElement));	}
		}
		
		if(utente instanceof Cliente)
		{
			for (MenuElement m: MENU_CLIENTE)
			{	barra_menu.getChildren().add(createButton(m));	}	
		}
	}
	
	
	
	// metodo che serve per creare un bottone nella BARRA_MENU
	private Button createButton(MenuElement viewItem)
	{
		Button button = new Button(viewItem.getNome());
		button.setStyle("-fx-background-color: transparent; -fx-font-size: 14;");
		button.setTextFill(Paint.valueOf("white"));
		button.setPrefHeight(20);
		button.setPrefWidth(150);
		button.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{	dispatcher.renderView(viewItem.getVista(), utente ,null);		}
		});
		return button;
	}

}
