package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackService;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.domain.Amministratore;
import it.univaq.disim.oop.MCMprogetto.domain.Feedback;
import it.univaq.disim.oop.MCMprogetto.domain.Operatore;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class ControllerFeedback implements Initializable, DataInitializable<Veicolo,Utente>
{
	private ViewDispatcher dispatcher;
	private FeedbackService feedbackservice;
	private Veicolo veicolo;
	private Utente utente;
	
    @FXML
    private TableView<Feedback> tabella_feedback;
    @FXML
    private TableColumn<Feedback, String> utente_tabella;
    @FXML
    private TableColumn<Feedback, String> feedback_tabella;
    @FXML
    private Label label_veicolo;
    
    
    public ControllerFeedback()
    {
    	dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
    	feedbackservice = factory.getFeedbackService();
    }
    
    
    
    
    
    @FXML	// TORNA INDIETRO
    void TornaIndietro(MouseEvent event)
    {	
    	if(utente instanceof Amministratore)
    	{	dispatcher.renderView("scheda_veicolo", veicolo ,null);	 }
    	
    	if(utente instanceof Operatore)
    	{	dispatcher.renderView("vistaveicoli", utente ,null);	}
    }



    @Override
	public void initialize(URL location, ResourceBundle resources)
    {  	utente_tabella.setStyle("-fx-alignment: CENTER;");	}
    

	@Override
	public void initializeData(Veicolo veicolo, Utente utente)
	{
		this.veicolo = veicolo;
		this.utente = utente;
		
		label_veicolo.setText("Feedback Di: " + veicolo.getModello());
		
		feedback_tabella.setCellValueFactory(new PropertyValueFactory<Feedback,String>("feedback"));
		utente_tabella.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Feedback, String>, ObservableValue<String>>()
	    {
			@Override
			public ObservableValue<String> call (CellDataFeatures<Feedback, String> tmp)
			{	return new SimpleStringProperty (tmp.getValue().getUtente().getUsername());	}
		});
		
		List<Feedback> listafeedback = null;
    	try
    	{	listafeedback = feedbackservice.getListFeedback(veicolo);	} 
    	catch (BusinessException e)
    	{	/* nessun feedback per questo veicolo */	}
    	ObservableList<Feedback> listaFeed = FXCollections.observableList(listafeedback);
    	tabella_feedback.setItems(listaFeed);
						
	
	}








	

}
