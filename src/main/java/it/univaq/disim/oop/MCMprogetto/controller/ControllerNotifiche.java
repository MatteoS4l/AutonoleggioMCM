package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ControllerNotifiche implements Initializable,DataInitializable<Utente, Object>
{
	private ViewDispatcher dispatcher;
	private NoleggioService noleggioservice;
	
	
	@FXML
    private TableView<Noleggio> tabella_notifiche_prenotazioni;
    @FXML
    private TableColumn<Noleggio,String > veicolo_prenotazioni;
    @FXML
    private TableColumn<Noleggio, String> dettagli_prenotazioni;
    @FXML
    private TableView<Noleggio> tabella_notifiche_assistenza;
    @FXML
    private TableColumn<Noleggio, String> dettagli_assistenza;
    @FXML
    private TableColumn<Noleggio, String> veicolo_assistenza;
    
    
    public ControllerNotifiche ()
    {
    	dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
    }
    
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{	
		
		
		
	}
	
	
	
	@Override
	public void initializeData(Utente utente) 
	{	try
		{	 
			List<Noleggio> listanoleggi = noleggioservice.FindNoleggiByUtente(utente);
			List<Noleggio> listacompleta = new ArrayList<Noleggio>();
			
			for(Noleggio tmp : listanoleggi)
			{
				if(!tmp.getDettagli().equalsIgnoreCase("null"))
				{	listacompleta.add(tmp);	}
			}
			
			dettagli_prenotazioni.setCellValueFactory(new PropertyValueFactory<Noleggio, String>("dettagli"));
			veicolo_prenotazioni.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>()
		    {
				@Override
				public ObservableValue<String> call (CellDataFeatures<Noleggio, String> tmp)
				{	return new SimpleStringProperty (tmp.getValue().getVeicolo().getModello());	}
			});
			ObservableList<Noleggio> listaNol1 = FXCollections.observableList(listacompleta);
			tabella_notifiche_prenotazioni.setItems(listaNol1);
		}
		catch (BusinessException e)
		{	System.out.println("ciao");	}
		
		
		try
		{	 
			List<Noleggio> lista_nol = noleggioservice.FindNoleggiByAssistenza("si");
			List<Noleggio> listacompleta = new ArrayList<Noleggio>();
			
			for(Noleggio tmp : lista_nol)
			{
				if(!tmp.getAssistenza().getRisposta().equalsIgnoreCase("null"))
				{	listacompleta.add(tmp);	}
			}
			
			dettagli_assistenza.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>()
		    {
				@Override
				public ObservableValue<String> call (CellDataFeatures<Noleggio, String> tmp)
				{	return new SimpleStringProperty (tmp.getValue().getAssistenza().getRisposta());	}
			});
			veicolo_assistenza.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>()
		    {
				@Override
				public ObservableValue<String> call (CellDataFeatures<Noleggio, String> tmp)
				{	return new SimpleStringProperty (tmp.getValue().getVeicolo().getModello());	}
			});
			ObservableList<Noleggio> listaNol2 = FXCollections.observableList(listacompleta);
			tabella_notifiche_assistenza.setItems(listaNol2);
		}
		catch (BusinessException e)
		{	System.out.println("ciao");	}
		
		
		
		 
	}
	
	
	
	
	
	
	
	
	
	
	
	
}

