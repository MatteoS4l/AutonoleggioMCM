package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaService;
import it.univaq.disim.oop.MCMprogetto.domain.Tipologia;
import javafx.beans.property.SimpleObjectProperty;
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

public class ControllerVistaTipologie implements Initializable, DataInitializable<Object, Object>
{
	private TipologiaService tipologiaservice;
	
	
    @FXML
    private TableView<Tipologia> tabella_tipologie;
    @FXML
    private TableColumn<Tipologia, String> tipologie_col;
    @FXML
    private TableColumn<Tipologia, Button> azioni_col;
    @FXML
    private TextField new_tipologia;
    @FXML
    private Button aggiungi_button;
    @FXML
    private Label label_errore;


    public ControllerVistaTipologie()
    {
    	MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
    	tipologiaservice = factory.getTipologiaService();
    }



    @FXML	// AGGIUNGI TIPOLOGIA
    void AggiungiTipologia(ActionEvent event)
    {	try
    	{	
    		Tipologia t = new Tipologia();
    		t.setTipologia(new_tipologia.getText());
    		tipologiaservice.createTipologia(t);	
    		label_errore.setText("");
    	} 
    	catch (BusinessException e)
    	{	label_errore.setText("Tipologia già esistente !");}
    	
    	new_tipologia.clear();
    	initialize(null, null);
    }





	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		aggiungi_button.disableProperty().bind(new_tipologia.textProperty().isEmpty());
		tipologie_col.setStyle("-fx-alignment: CENTER;");
		azioni_col.setStyle("-fx-alignment: CENTER;");
		
		tipologie_col.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
		azioni_col.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Tipologia, Button>, ObservableValue<Button>>()
				{
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Tipologia, Button> param) {
						final Button eliminaButton = new Button("elimina");
						eliminaButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								try
	/* ELIMINA */				{	tipologiaservice.deleteTipologia(param.getValue());	  label_errore.setText("");   } 
								catch (NumberFormatException | BusinessException e)
								{	label_errore.setText("veicoli appartenenti a questa tipologia !");   }
								initialize(null, null);
							}
						});
						return new SimpleObjectProperty<Button>(eliminaButton);
					}
				});	
		List<Tipologia> listatipologie = null;
		try
		{	listatipologie = tipologiaservice.FindAllTipologiaT();	} 
		catch (BusinessException e)
		{	e.printStackTrace();	}
		ObservableList<Tipologia> listaT = FXCollections.observableList(listatipologie);
		tabella_tipologie.setItems(listaT);
		
	}

	
	
	
	
	
	
	
	
}
