package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;


public class ControllerAssistenzaOperatore implements Initializable,DataInitializable<Utente, Assistenza>
{
	private ViewDispatcher dispatcher;
	private AssistenzaService assistenzaservice;
	private Assistenza assistenza;
	private Utente utente;

    @FXML
    private TableView<Assistenza> tabella_assistenza_op;
    @FXML
    private TableColumn<Assistenza, String> cliente_assistenza_op;
    @FXML
    private TableColumn<Assistenza, String> veicolo_assistenza_op;
    @FXML
    private TableColumn<Assistenza, String> dettagli_assistenza_op;
    @FXML
    private TableColumn<Assistenza, Button> azioni_assistenza_op;
    @FXML
    private TextField text_risposta_assistenza;
    @FXML
    private Button bottone_invio;
    @FXML
    private Label label_risposta_assistenza;
    @FXML
    private ComboBox<String> scelta_assistenza;
    @FXML
    private Button bottone_conferma;
    
    
    
    public ControllerAssistenzaOperatore()
    {
    	dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		assistenzaservice = factory.getAssistenzaService();
    }
    
    @FXML	// INVIA RISPOSTA
    void inviaRispostaAssistenza(ActionEvent event) throws BusinessException 
    {
    	assistenza.setRisposta(text_risposta_assistenza.getText());
    	assistenza.setServita(true);
    	assistenzaservice.updateAssistenza(assistenza);
    	
    	text_risposta_assistenza.clear();
    	text_risposta_assistenza.setVisible(false);
		label_risposta_assistenza.setVisible(true);
		bottone_invio.setVisible(false);
		
		initialize(null, null);
    }
    
    // Mostra cose
	void mostraCose(Assistenza assistenza)
    {
		this.assistenza = assistenza;
		scelta_assistenza.setVisible(true);
		bottone_conferma.setVisible(true);
    }
    
    
    
    @FXML
    void confermaSelezione(ActionEvent event) 
    {	try
    	{
    		if (scelta_assistenza.getValue().equalsIgnoreCase("Manutenzione"))
        	{
    			text_risposta_assistenza.setVisible(true);
    			bottone_invio.setVisible(true);
        	}
    	    else
    	    {	dispatcher.renderView("sostituzione", utente, assistenza);    }
    	}
    	catch(NullPointerException e)
    	{	System.out.println("errore");	}
    	
    }
    
    
    
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		scelta_assistenza.getItems().addAll("Manutenzione", "Sostituzione");
		scelta_assistenza.setVisible(false);
		text_risposta_assistenza.setVisible(false);
		label_risposta_assistenza.setVisible(false);
		bottone_invio.setVisible(false);
		bottone_conferma.setVisible(false);
		
		cliente_assistenza_op.setStyle("-fx-alignment: CENTER;");
		veicolo_assistenza_op.setStyle("-fx-alignment: CENTER;");
		dettagli_assistenza_op.setStyle("-fx-alignment: CENTER;");
		azioni_assistenza_op.setStyle("-fx-alignment: CENTER;");
		
		cliente_assistenza_op.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Assistenza, String>, ObservableValue<String>>()
	    {
			@Override
			public ObservableValue<String> call (CellDataFeatures<Assistenza, String> tmp)
			{	
				if(tmp.getValue().getCliente() == null)
				{	return new SimpleStringProperty();	}
				else
				{	return new SimpleStringProperty (tmp.getValue().getCliente().getUsername());	}
			}
		});
		
		veicolo_assistenza_op.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Assistenza, String>, ObservableValue<String>>()
	    {
			@Override
			public ObservableValue<String> call (CellDataFeatures<Assistenza, String> tmp)
			{	
				if(tmp.getValue().getVeicolo() == null)
				{	return new SimpleStringProperty();	}
				else
				{	return new SimpleStringProperty (tmp.getValue().getVeicolo().getModello());	}
			}
		});
		
		dettagli_assistenza_op.setCellValueFactory(new PropertyValueFactory<Assistenza,String>("dettagli"));
		azioni_assistenza_op.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Assistenza, Button>, ObservableValue<Button>>()
				{
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Assistenza, Button> param) {
						final Button dettagliButton = new Button("Rispondi");
						dettagliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								mostraCose(param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(dettagliButton);
					}
				});	
		try
		{
			List<Assistenza> lista_ass = new ArrayList<Assistenza>();
			lista_ass = assistenzaservice.getListaAssistenzaByServita(false);
			ObservableList<Assistenza> lista_a = FXCollections.observableList(lista_ass);
			tabella_assistenza_op.setItems(lista_a);
		}
		catch (BusinessException e)
		{	label_risposta_assistenza.setTextFill(Color.RED);
			label_risposta_assistenza.setText("Errore generico");
		}
	}

	@Override
	public void initializeData(Utente utente)
	{	this.utente = utente;	}
	
	
	
	
	
	
	
	
	
	
	

}
