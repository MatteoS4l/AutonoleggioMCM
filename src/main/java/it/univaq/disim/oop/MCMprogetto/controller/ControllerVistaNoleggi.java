package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    
    public ControllerVistaNoleggi()
	{	
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
	}
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		id_noleggio.setCellValueFactory(new PropertyValueFactory<Noleggio,Integer>("ID"));
		data_inizio_noleggio.setCellValueFactory(new PropertyValueFactory<Noleggio,LocalDateTime>("data_inizio_noleggio"));
		data_fine_noleggio.setCellValueFactory(new PropertyValueFactory<Noleggio,LocalDateTime>("data_fine_noleggio"));
		
		List<Noleggio> listacompleta = null;
		try
		{	listacompleta = noleggioservice.FindAllNoleggi();	}
		catch (BusinessException e)
		{	/* lista vuota */	}
		ObservableList<Noleggio> listaNol = FXCollections.observableList(listacompleta);
		tabella_noleggi.setItems(listaNol);
		
		
	}

}
