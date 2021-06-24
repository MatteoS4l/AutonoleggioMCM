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

public class VistaTipologieController implements Initializable, DataInitializable<Object, Object> {
	private TipologiaService tipologiaservice;

	@FXML
	private TableView<Tipologia> tabellaTipologie;
	@FXML
	private TableColumn<Tipologia, String> tipologieCol;
	@FXML
	private TableColumn<Tipologia, Button> azioniCol;
	@FXML
	private TextField nuovaTipologia;
	@FXML
	private Button aggiungiButton;
	@FXML
	private Label labelErrore;

	public VistaTipologieController() {
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		tipologiaservice = factory.getTipologiaService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		aggiungiButton.disableProperty().bind(nuovaTipologia.textProperty().isEmpty());
		tipologieCol.setStyle("-fx-alignment: CENTER;");
		azioniCol.setStyle("-fx-alignment: CENTER;");

		tipologieCol.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
		azioniCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Tipologia, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Tipologia, Button> param) {
						final Button eliminaButton = new Button("elimina");
						eliminaButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								try
								/* ELIMINA */ {
									tipologiaservice.deleteTipologia(param.getValue());
									labelErrore.setText("");
								} catch (NumberFormatException | BusinessException e) {
									labelErrore.setText("veicoli appartenenti a questa tipologia !");
								}
								initialize(null, null);
							}
						});
						return new SimpleObjectProperty<Button>(eliminaButton);
					}
				});
		List<Tipologia> listatipologie = null;
		try {
			listatipologie = tipologiaservice.FindAllTipologiaT();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		ObservableList<Tipologia> listaT = FXCollections.observableList(listatipologie);
		tabellaTipologie.setItems(listaT);
		tabellaTipologie.setItems(listaT);
	}

	@FXML // AGGIUNGI TIPOLOGIA
	void AggiungiTipologia(ActionEvent event) {
		try {
			Tipologia t = new Tipologia();
			t.setTipologia(nuovaTipologia.getText());
			tipologiaservice.createTipologia(t);
			labelErrore.setText("");
		} catch (BusinessException e) {
			labelErrore.setText("Tipologia già esistente !");
		}

		nuovaTipologia.clear();
		initialize(null, null);
	}
}
