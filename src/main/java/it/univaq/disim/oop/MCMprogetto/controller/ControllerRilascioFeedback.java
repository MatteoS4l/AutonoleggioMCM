package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Feedback;
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
	private Utente utente;
	private Feedback feedback;

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
    
    public ControllerRilascioFeedback ()
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
	public void initializeData(Utente utente, Object e) 
	{
		this.utente = utente;
		
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
						final Button dettagliButton = new Button("Lascia feedback");
						dettagliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) 
							{
								System.out.println("ciao");
								dispatcher.renderView("creazione_feedback", param.getValue().getVeicolo(), utente );
							}
						});
						return new SimpleObjectProperty<Button>(dettagliButton);
					}
				});	
		
		try {
			 List<Noleggio> listaNol2 = new ArrayList<Noleggio>();
			 listaNol2 = noleggioservice.FindNoleggiByUtente(utente);
			
			 for (Noleggio n: listaNol2)
			 {
				 if (n.getData_fine_noleggio().isAfter(LocalDateTime.now()))
				 {
					 listaNol2.remove(n);
				 }
			 }
			 ObservableList<Noleggio> listaNol3 = FXCollections.observableList(listaNol2);
			 tabella_feedback_utente.setItems(listaNol3);

		} catch (BusinessException |NullPointerException a) {
			a.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
	}
}
	
	

