package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
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
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class ControllerAssistenzaOperatore implements Initializable,DataInitializable<Utente, Assistenza>
{
	private ViewDispatcher dispatcher;
	private AssistenzaService assistenzaservice;
	private Assistenza assistenza;

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
    	text_risposta_assistenza.setVisible(true);
		bottone_invio.setVisible(true);
    	
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		text_risposta_assistenza.setVisible(false);
		label_risposta_assistenza.setVisible(false);
		bottone_invio.setVisible(false);
		
		cliente_assistenza_op.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Assistenza, String>, ObservableValue<String>>()
	    {
			@Override
			public ObservableValue<String> call (CellDataFeatures<Assistenza, String> tmp)
			{	return new SimpleStringProperty (tmp.getValue().getCliente().getUsername());	}
		});
		
		veicolo_assistenza_op.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Assistenza, String>, ObservableValue<String>>()
	    {
			@Override
			public ObservableValue<String> call (CellDataFeatures<Assistenza, String> tmp)
			{	return new SimpleStringProperty (tmp.getValue().getVeicolo().getModello());	}
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

}
