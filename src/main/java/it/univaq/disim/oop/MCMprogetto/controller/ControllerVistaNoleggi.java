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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ControllerVistaNoleggi implements Initializable, DataInitializable<Utente,Object>
{
	private ViewDispatcher dispatcher;
	private NoleggioService noleggioservice;

    @FXML
    private TableView<Noleggio> tabella_noleggi;
    @FXML
    private TableColumn<Noleggio, Integer> id_noleggio;
    @FXML
    private TableColumn<Noleggio, LocalDateTime> data_inizio_noleggio;
    @FXML
    private TableColumn<Noleggio, LocalDateTime> data_fine_noleggio;
    @FXML
    private TableColumn<Noleggio, String> veicolo_noleggio;
    @FXML
    private TableColumn<Noleggio, String> cliente_noleggio;
    @FXML
    private TableColumn<Noleggio, Button> azioni_noleggio;
    @FXML
    private ComboBox<String> box_assistenza;
    @FXML
    private CheckBox check_noleggi_incorso;
    @FXML
    private Text text_dettagli;
    
    
    public ControllerVistaNoleggi()
	{	
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
	}
    
    
    
    @FXML	// APPLICA FILTRI
    void ApplicaFiltri(ActionEvent event) throws BusinessException
    {
    	List<Noleggio> listafiltrata = noleggioservice.FindAllNoleggi();
    	
		if(check_noleggi_incorso.isSelected())
		{	listafiltrata = Intersezione(listafiltrata, noleggioservice.FindNoleggiInCorso());	}
		
		if(box_assistenza.getValue() != null)
		{	listafiltrata = Intersezione(listafiltrata, noleggioservice.FindNoleggiByAssistenza(box_assistenza.getValue()));	}
		
    	ObservableList<Noleggio> listaNol = FXCollections.observableList(listafiltrata);
		tabella_noleggi.setItems(listaNol);
    }
    
    

    
    
    // VEDI DETTAGLI
    private void VediDettagli(Noleggio noleggio)
    {
    	if(noleggio.getAssistenza() == null)
    	{	text_dettagli.setText("Nessuna assistenza ricevuta per questo noleggio");	}
    	else
    	{	text_dettagli.setText(noleggio.getAssistenza().getDettagli());	}
    }
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		box_assistenza.getItems().add("SI");   box_assistenza.getItems().add("NO");   box_assistenza.getItems().add("Tutte");
		
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
						final Button dettagliButton = new Button("Vedi");
						dettagliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								VediDettagli(param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(dettagliButton);
					}
				});	
		
		List<Noleggio> listacompleta = null;
		try
		{	listacompleta = noleggioservice.FindAllNoleggi();	}
		catch (BusinessException e)
		{	e.printStackTrace();	}
		ObservableList<Noleggio> listaNol = FXCollections.observableList(listacompleta);
		tabella_noleggi.setItems(listaNol);
		
		
	}
	
	
	
//	INTERSEZIONE FRA LE LISTE
	public List<Noleggio> Intersezione(List<Noleggio> list1, List<Noleggio> list2)
	{
        List<Noleggio> list = new ArrayList<Noleggio>();
        for(int i=0; i<list1.size(); i++)
        {
        	for(int j=0; j<list2.size(); j++)
        	{
        		if(list1.get(i).getID() == list2.get(j).getID())
        		{	list.add(list1.get(i));	}
        	}
        }
        return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
