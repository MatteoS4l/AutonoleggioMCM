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
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class VistaNoleggiController implements Initializable, DataInitializable<Utente, Object> {
	private NoleggioService noleggioservice;

	@FXML
	private TableView<Noleggio> tabellaNoleggi;
	@FXML
	private TableColumn<Noleggio, Integer> idNoleggio;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> dataInizioIoleggio;
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

	@FXML // APPLICA FILTRI
	void ApplicaFiltri(ActionEvent event) throws BusinessException {
		List<Noleggio> listafiltrata = noleggioservice.FindAllNoleggi();

		if (checkNoleggiIncorso.isSelected()) {
			listafiltrata = Intersezione(listafiltrata, noleggioservice.FindNoleggiInCorso());
		}

		if (boxAssistenza.getValue() != null) {
			listafiltrata = Intersezione(listafiltrata,
					noleggioservice.FindNoleggiByAssistenza(boxAssistenza.getValue()));
		}

		ObservableList<Noleggio> listaNol = FXCollections.observableList(listafiltrata);
		tabellaNoleggi.setItems(listaNol);
	}

	// VEDI DETTAGLI
	private void VediDettagli(Noleggio noleggio) {
		if (noleggio.getAssistenza() == null) {
			textDettagli.setText("Nessuna assistenza ricevuta per questo noleggio");
		} else {
			textDettagli.setText(noleggio.getAssistenza().getDettagli());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		boxAssistenza.getItems().add("SI");
		boxAssistenza.getItems().add("NO");
		boxAssistenza.getItems().add("Tutte");

		idNoleggio.setStyle("-fx-alignment: CENTER;");
		dataInizioIoleggio.setStyle("-fx-alignment: CENTER;");
		dataFineNoleggio.setStyle("-fx-alignment: CENTER;");
		veicoloNoleggio.setStyle("-fx-alignment: CENTER;");
		clienteNoleggio.setStyle("-fx-alignment: CENTER;");
		azioniNoleggio.setStyle("-fx-alignment: CENTER;");

		idNoleggio.setCellValueFactory(new PropertyValueFactory<Noleggio, Integer>("ID"));
		dataInizioIoleggio
				.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("data_inizio_noleggio"));
		dataFineNoleggio.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("data_fine_noleggio"));
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

		List<Noleggio> listacompleta = new ArrayList<Noleggio>();
		try {
			listacompleta = noleggioservice.FindAllNoleggi();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		ObservableList<Noleggio> listaNol = FXCollections.observableList(listacompleta);
		tabellaNoleggi.setItems(listaNol);

	}

//	INTERSEZIONE FRA LE LISTE
	public List<Noleggio> Intersezione(List<Noleggio> list1, List<Noleggio> list2) {
		List<Noleggio> list = new ArrayList<Noleggio>();
		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list2.size(); j++) {
				if (list1.get(i).getID() == list2.get(j).getID()) {
					list.add(list1.get(i));
				}
			}
		}
		return list;
	}

}
