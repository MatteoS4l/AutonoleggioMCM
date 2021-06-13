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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ControllerNoleggi_Operatore implements Initializable, DataInitializable<Noleggio, Object>
{
	private NoleggioService noleggioservice;
	
    @FXML
    private TableView<Noleggio> tabella_noleggi_op;
    @FXML
    private TableColumn<Noleggio, Integer> id_noleggio;
    @FXML
    private TableColumn<Noleggio, String> veicolo_noleggio;
    @FXML
    private TableColumn<Noleggio, LocalDateTime> data_in_nol;
    @FXML
    private TableColumn<Noleggio, LocalDateTime> data_fi_nol;
    @FXML
    private TableColumn<Noleggio, String> cliente_nol;
    @FXML
    private CheckBox spunta_noleggi;
    @FXML
    private DatePicker ricerca_per_data;
    @FXML
    private Button bottone_applica;
    
    
    
    public ControllerNoleggi_Operatore()
    {
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
    }

    
    
    @FXML	// APPLICA FILTRI
    void ApplicaFiltri(ActionEvent event) throws BusinessException 
    {
    	List<Noleggio> listafiltrata = noleggioservice.FindAllNoleggi();
    	
    	if (spunta_noleggi.isSelected())
    	{	listafiltrata = Intersezione(listafiltrata, noleggioservice.FindNoleggiInCorso());	}
    	
    	if (ricerca_per_data.getValue() != null)
    	{	listafiltrata = Intersezione(listafiltrata, noleggioservice.FindNoleggiByDataFine(ricerca_per_data.getValue()));	}
    	
    	ObservableList<Noleggio> listaNol = FXCollections.observableList(listafiltrata);
		tabella_noleggi_op.setItems(listaNol);
    }

    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		id_noleggio.setStyle("-fx-alignment: CENTER;");
		veicolo_noleggio.setStyle("-fx-alignment: CENTER;");
		data_in_nol.setStyle("-fx-alignment: CENTER;");
		data_fi_nol.setStyle("-fx-alignment: CENTER;");
		cliente_nol.setStyle("-fx-alignment: CENTER;");
		
		id_noleggio.setCellValueFactory(new PropertyValueFactory<Noleggio,Integer>("ID"));
		data_in_nol.setCellValueFactory(new PropertyValueFactory<Noleggio,LocalDateTime>("data_inizio_noleggio"));
		data_fi_nol.setCellValueFactory(new PropertyValueFactory<Noleggio,LocalDateTime>("data_fine_noleggio"));
		cliente_nol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>()
	    {
			@Override
			public ObservableValue<String> call (CellDataFeatures<Noleggio, String> tmp)
			{	
				if(tmp.getValue().getCliente() == null)
				{	return new SimpleStringProperty();	}
				else
				{	return new SimpleStringProperty (tmp.getValue().getCliente().getUsername());	}
			}
		});
		veicolo_noleggio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>()
	    {
			@Override
			public ObservableValue<String> call (CellDataFeatures<Noleggio, String> tmp)
			{	
				if(tmp.getValue().getVeicolo() == null)
				{	return new SimpleStringProperty();	}
				else
				{	return new SimpleStringProperty (tmp.getValue().getVeicolo().getModello());	}
			}
		});
		
		
		List<Noleggio> listacompleta1 = null;
		try
		{	listacompleta1 = noleggioservice.FindAllNoleggi();	}
		catch (BusinessException e)
		{	/* lista vuota */	}
		ObservableList<Noleggio> listaNol1 = FXCollections.observableList(listacompleta1);
		tabella_noleggi_op.setItems(listaNol1);
	}
	
	
	//	INTERSEZIONE
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

