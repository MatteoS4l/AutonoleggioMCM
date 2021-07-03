package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DettagliAssistenzeAdminController implements Initializable, DataInitializable<Noleggio> {

	@FXML
	private TableView<Assistenza> tabellaAssistenzeNoleggio;
	@FXML
	private TableColumn<Assistenza, String> richiesteAssistenze;
	@FXML
	private TableColumn<Assistenza, String> risposteAssistenze;
	
	public DettagliAssistenzeAdminController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		richiesteAssistenze.setStyle("-fx-alignment: CENTER;");
		risposteAssistenze.setStyle("-fx-alignment: CENTER;");
		richiesteAssistenze.setCellValueFactory(new PropertyValueFactory<>("richiesta"));
		risposteAssistenze.setCellValueFactory(new PropertyValueFactory<>("risposta"));
	}

	@Override
	public void initializeData(Noleggio noleggio) {
		List<Assistenza> lista = new ArrayList<Assistenza>();
		for (Assistenza tmp : noleggio.getListaAssistenza()) {
			lista.add(tmp);
		}
		ObservableList<Assistenza> listaOb = FXCollections.observableList(lista);
		tabellaAssistenzeNoleggio.setItems(listaOb);
	}

}
