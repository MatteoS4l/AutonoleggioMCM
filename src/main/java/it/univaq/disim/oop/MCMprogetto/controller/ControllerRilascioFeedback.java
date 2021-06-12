package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;


public class ControllerRilascioFeedback implements Initializable,DataInitializable<Utente, Object>
{
	private ViewDispatcher dispatcher;
	private NoleggioService noleggioservice;

    @FXML
    private TableView<Noleggio> tabella_feedback_utente;
    @FXML
    private TableColumn<Noleggio, Integer> id_feedback_utente;
    @FXML
    private TableColumn<Noleggio, String> veicolo_feedback_utente;
    @FXML
    private TableColumn<Noleggio, LocalDateTime> data_in_feedback_utente;
    @FXML
    private TableColumn<Noleggio, LocalDateTime> data_fi_feedback_utente;
    @FXML
    private TableColumn<Noleggio, Button> azioni_feedback_utente;
    
    public ControllerRilascioFeedback()
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
	{
		id_feedback_utente.setStyle("-fx-alignment: CENTER;");
		veicolo_feedback_utente.setStyle("-fx-alignment: CENTER;");
		data_in_feedback_utente.setStyle("-fx-alignment: CENTER;");
		data_fi_feedback_utente.setStyle("-fx-alignment: CENTER;");
		azioni_feedback_utente.setStyle("-fx-alignment: CENTER;");
		
		id_feedback_utente.setCellValueFactory(new PropertyValueFactory<Noleggio,Integer>("ID"));
		data_in_feedback_utente.setCellValueFactory(new PropertyValueFactory<Noleggio,LocalDateTime>("data_inizio_noleggio"));
		data_fi_feedback_utente.setCellValueFactory(new PropertyValueFactory<Noleggio,LocalDateTime>("data_fine_noleggio"));
		veicolo_feedback_utente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>()
	    {
			@Override
			public ObservableValue<String> call (CellDataFeatures<Noleggio, String> tmp)
			{	return new SimpleStringProperty (tmp.getValue().getVeicolo().getModello());	}
		});
		azioni_feedback_utente.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, Button>, ObservableValue<Button>>()
				{
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Noleggio, Button> param) {
						final Button feedButton = new Button("Lascia feedback");
						feedButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) 
							{	dispatcher.renderView("creazione_feedback",utente, param.getValue());		}
						});
						return new SimpleObjectProperty<Button>(feedButton);
					}
				});	
		
		try
		{
			 List<Noleggio> listaNol2 = new ArrayList<Noleggio>();
			 listaNol2 = noleggioservice.FindNoleggiByUtente(utente);
			 List<Noleggio> listacompleta = new ArrayList<Noleggio>();
			
			 for (Noleggio tmp: listaNol2)
			 {
				 if (tmp.getData_fine_noleggio().isBefore(LocalDateTime.now()) && tmp.isFeed_lasciato() == false)
				 {	listacompleta.add(tmp);	}
			 }
			 ObservableList<Noleggio> listaNol3 = FXCollections.observableList(listacompleta);
			 tabella_feedback_utente.setItems(listaNol3);
		} 
		catch (BusinessException | NullPointerException a)
		{	a.printStackTrace();	}
		
		
		
		
		
		
		
		
		
		
	}
}
	
	

