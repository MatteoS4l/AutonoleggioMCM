package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.domain.Amministratore;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
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


public class ControllerVistaUtenti implements Initializable, DataInitializable<Utente,Object>
{	
	private ViewDispatcher dispatcher;
	private UtenteService utenteservice;
	private Utente utente;
	
	@FXML
    private TableView<Utente> tabella_utenti;
    @FXML
    private TableColumn<Utente, Integer> ID_tabella_utenti;
    @FXML
    private TableColumn<Utente, String> nome_tabella_utenti;
    @FXML
    private TableColumn<Utente, String> cognome_tabella_utenti;
    @FXML
    private TableColumn<Utente, Integer> eta_tabella_utenti;
    @FXML
    private TableColumn<Utente, Character> sesso_tabella_utenti;
    @FXML
    private TableColumn<Utente, Button> azioni_tabella_utenti;

    
    public ControllerVistaUtenti()
    {
    	dispatcher = ViewDispatcher.getInstance();
    	MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
    	utenteservice = factory.getUtenteService();
    }
    
    
    
   

    @FXML
    void AggiungiUtente(ActionEvent event)
    {	dispatcher.renderView("registrazione", utente, null);    }
    
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		ID_tabella_utenti.setStyle("-fx-alignment: CENTER;");
		nome_tabella_utenti.setStyle("-fx-alignment: CENTER;");
		cognome_tabella_utenti.setStyle("-fx-alignment: CENTER;");
		eta_tabella_utenti.setStyle("-fx-alignment: CENTER;");
		sesso_tabella_utenti.setStyle("-fx-alignment: CENTER;");
		azioni_tabella_utenti.setStyle("-fx-alignment: CENTER;");
		
		ID_tabella_utenti.setCellValueFactory(new PropertyValueFactory<>("ID"));
		nome_tabella_utenti.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cognome_tabella_utenti.setCellValueFactory(new PropertyValueFactory<>("cognome"));
		eta_tabella_utenti.setCellValueFactory(new PropertyValueFactory<>("eta"));
		sesso_tabella_utenti.setCellValueFactory(new PropertyValueFactory<>("sesso"));
		azioni_tabella_utenti.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Utente, Button>, ObservableValue<Button>>()
				{
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Utente, Button> param) {
						final Button appelliButton = new Button("visualizza");
						appelliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("scheda_utente", param.getValue(), null);
							}
						});
						return new SimpleObjectProperty<Button>(appelliButton);
					}
				});		
	}



	@Override
	public void initializeData(Utente utente)
	{	
		this.utente = utente;
		
		try
		{
			//utenteservice.primaAggiunta();
			List<Utente> listautenti = utenteservice.FindAllUtenti((Amministratore)utente);
			ObservableList<Utente> listaU = FXCollections.observableList(listautenti);
			tabella_utenti.setItems(listaU);
		}
		catch (BusinessException e)
		{	e.printStackTrace();	}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
