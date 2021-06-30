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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class VistaNoleggiController implements Initializable, DataInitializable<Utente> {
	private NoleggioService noleggioservice;

	@FXML
	private TableView<Noleggio> tabellaNoleggi;
	@FXML
	private TableColumn<Noleggio, Integer> idNoleggio;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> dataInizioNoleggio;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> dataFineNoleggio;
	@FXML
	private TableColumn<Noleggio, String> veicoloNoleggio;
	@FXML
	private TableColumn<Noleggio, String> clienteNoleggio;
	@FXML
	private TableColumn<Noleggio, Button> azioniNoleggio;
	@FXML
	private ComboBox<String> boxAssistenza;
	@FXML
	private CheckBox checkNoleggiIncorso;
	@FXML
	private Text textDettagli;

	public VistaNoleggiController() {
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		boxAssistenza.getItems().addAll("SI", "NO", "Tutte");

		idNoleggio.setStyle("-fx-alignment: CENTER;");
		dataInizioNoleggio.setStyle("-fx-alignment: CENTER;");
		dataFineNoleggio.setStyle("-fx-alignment: CENTER;");
		veicoloNoleggio.setStyle("-fx-alignment: CENTER;");
		clienteNoleggio.setStyle("-fx-alignment: CENTER;");
		azioniNoleggio.setStyle("-fx-alignment: CENTER;");

		idNoleggio.setCellValueFactory(new PropertyValueFactory<Noleggio, Integer>("ID"));
		dataInizioNoleggio.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("dataInizioNoleggio"));
		dataFineNoleggio.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("dataFineNoleggio"));
		clienteNoleggio.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Noleggio, String> tmp) {
						if (tmp.getValue().getCliente() == null) {
							return new SimpleStringProperty();
						} else {
							return new SimpleStringProperty(tmp.getValue().getCliente().getUsername());
						}
					}
				});
		veicoloNoleggio.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Noleggio, String> tmp) {
						if (tmp.getValue().getVeicolo() == null) {
							return new SimpleStringProperty();
						} else {
							return new SimpleStringProperty(tmp.getValue().getVeicolo().getModello());
						}
					}
				});
		azioniNoleggio.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Noleggio, Button> param) {
						final Button dettagliButton = new Button("Dettagli");
						dettagliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								VediDettagli(param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(dettagliButton);
					}
				});
	}

	@Override
	public void initializeData(Utente t) {
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();
		try {
			listacompleta = noleggioservice.findAllNoleggi();
		} catch (BusinessException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		ObservableList<Noleggio> listaNol = FXCollections.observableList(listacompleta);
		tabellaNoleggi.setItems(listaNol);
	}

	@FXML // APPLICA FILTRI
	void ApplicaFiltri(ActionEvent event) throws BusinessException {
		List<Noleggio> listafiltrata = noleggioservice.findAllNoleggi();

		if (checkNoleggiIncorso.isSelected()) {
			listafiltrata = Intersezione(listafiltrata, noleggioservice.findNoleggiInCorso());
		}
		if (boxAssistenza.getValue() != null && !boxAssistenza.getValue().equalsIgnoreCase("tutte")) {
			listafiltrata = Intersezione(listafiltrata,
					noleggioservice.findNoleggiByAssistenza(boxAssistenza.getValue()));
		}
		ObservableList<Noleggio> listaNol = FXCollections.observableList(listafiltrata);
		tabellaNoleggi.setItems(listaNol);
	}

	// VEDI DETTAGLI
	private void VediDettagli(Noleggio noleggio) {
		if (noleggio.getListaAssistenza().isEmpty()) {
			textDettagli.setText("Nessuna assistenza ricevuta per questo noleggio");
		} else {
			textDettagli.setText("da mettere il testo di tutte le richieste");
		}
	}

	// INTERSEZIONE FRA LE LISTE
	public List<Noleggio> Intersezione(List<Noleggio> list1, List<Noleggio> list2) {
		List<Noleggio> list = new ArrayList<Noleggio>();
		for (Noleggio n1 : list1) {
			for (Noleggio n2 : list2) {
				if (n1.getID() == n2.getID()) {
					list.add(n1);
				}
			}
		}
		return list;
	}

}
