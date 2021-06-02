package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ControllerNoleggi_Utenti implements Initializable, DataInitializable<Utente, Object>
{
	private ViewDispatcher dispatcher;
	private Utente utente;
	private Noleggio noleggio;
	private AssistenzaService assistenzaservice;
	private NoleggioService noleggioservice;
	
    @FXML
    private TableView<Noleggio> tabella_noleggi;
    @FXML
    private TableColumn<Noleggio, Integer> id_noleggio;
    @FXML
    private TableColumn<Noleggio, String> veicolo_noleggio;
    @FXML
    private TableColumn<Noleggio, LocalDateTime> data_inizio_noleggio;
    @FXML
    private TableColumn<Noleggio, LocalDateTime> data_fine_noleggio;
    @FXML
    private TableColumn<Noleggio, String> cliente_noleggio;
    @FXML
    private TableColumn<Noleggio, Button> azioni_noleggio;
    @FXML
    private TextField text_dettagli_richiesta;
    @FXML
    private Label label_avviso;
    @FXML
    private Button invia_button;

    
    public ControllerNoleggi_Utenti()
    {
    	dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		assistenzaservice = factory.getAssistenzaService();
		noleggioservice = factory.getNoleggioService();
    }
    
    
    
    
    
    @FXML	// INVIA RICHIESTA
    void InviaRichiesta(ActionEvent event)
    {	try
    	{	
    		noleggio.getAssistenza().setDettagli(text_dettagli_richiesta.getText());
    		assistenzaservice.createAssistenza(text_dettagli_richiesta.getText());
    		label_avviso.setVisible(true);
    	} 
    	catch (BusinessException e)
    	{	/* errore */	}
    	text_dettagli_richiesta.clear();
    }
    
    
    
    //	RICHIEDI
    private void RichiediAssistenza(Noleggio nol)
    {
    	this.noleggio = nol;
    	text_dettagli_richiesta.setVisible(true);
		invia_button.setVisible(true);
    }


    
    

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		label_avviso.setVisible(false);
		text_dettagli_richiesta.setVisible(false);
		invia_button.setVisible(false);
		
		invia_button.disableProperty().bind(text_dettagli_richiesta.textProperty().isEmpty());
		
		id_noleggio.setStyle("-fx-alignment: CENTER;");
		data_inizio_noleggio.setStyle("-fx-alignment: CENTER;");
		data_fine_noleggio.setStyle("-fx-alignment: CENTER;");
		veicolo_noleggio.setStyle("-fx-alignment: CENTER;");
		cliente_noleggio.setStyle("-fx-alignment: CENTER;");
		azioni_noleggio.setStyle("-fx-alignment: CENTER;");
		
		id_noleggio.setCellValueFactory(new PropertyValueFactory<Noleggio,Integer>("ID"));
		data_inizio_noleggio.setCellValueFactory(new PropertyValueFactory<Noleggio,LocalDateTime>("data_inizio_noleggio"));
		data_fine_noleggio.setCellValueFactory(new PropertyValueFactory<Noleggio,LocalDateTime>("data_fine_noleggio"));
		cliente_noleggio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>()
	    {
			@Override
			public ObservableValue<String> call (CellDataFeatures<Noleggio, String> tmp)
			{	return new SimpleStringProperty (tmp.getValue().getCliente().getUsername());	}
		});
		veicolo_noleggio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>()
	    {
			@Override
			public ObservableValue<String> call (CellDataFeatures<Noleggio, String> tmp)
			{	return new SimpleStringProperty (tmp.getValue().getVeicolo().getModello());	}
		});
		azioni_noleggio.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, Button>, ObservableValue<Button>>()
				{
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Noleggio, Button> param) {
						final Button dettagliButton = new Button("Richiedi");
						dettagliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								RichiediAssistenza(param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(dettagliButton);
					}
				});	
	}



	@Override
	public void initializeData(Utente utente)
	{
		this.utente = utente;
		
		System.out.println(utente.getNome());
		List<Noleggio> listacompleta = null;
		try
		{	listacompleta = noleggioservice.FindNoleggiByUtente(utente);	}
		catch (BusinessException e)
		{	/* lista vuota */	}
		ObservableList<Noleggio> listaNol = FXCollections.observableList(listacompleta);
		tabella_noleggi.setItems(listaNol);
	}

	
	
	
	
	
}
