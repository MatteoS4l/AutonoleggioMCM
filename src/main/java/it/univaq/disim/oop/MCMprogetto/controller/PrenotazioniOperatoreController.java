package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Operatore;
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
import javafx.util.Callback;

public class PrenotazioniOperatoreController implements Initializable, DataInitializable<Noleggio, Operatore> {
	private NoleggioService noleggioservice;
	private Noleggio noleggio;

	@FXML
	private TableView<Noleggio> tabellaPrenotazioni;
	@FXML
	private TableColumn<Noleggio, String> clientePren;
	@FXML
	private TableColumn<Noleggio, String> veicoloPren;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> dataInizioPren;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> dataFinePren;
	@FXML
	private TableColumn<Noleggio, Button> azioniPren;
	@FXML
	private TextField textRisposta;
	@FXML
	private Button bottoneRisposta;
	@FXML
	private Label labelRisposta;

	public PrenotazioniOperatoreController() {
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelRisposta.setVisible(false);
		textRisposta.setVisible(false);
		bottoneRisposta.setVisible(false);

		clientePren.setStyle("-fx-alignment: CENTER;");
		veicoloPren.setStyle("-fx-alignment: CENTER;");
		dataInizioPren.setStyle("-fx-alignment: CENTER;");
		dataFinePren.setStyle("-fx-alignment: CENTER;");
		azioniPren.setStyle("-fx-alignment: CENTER;");

		clientePren.setCellValueFactory(
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
		veicoloPren.setCellValueFactory(
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
		dataInizioPren.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("data_inizio_noleggio"));
		dataFinePren.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("data_fine_noleggio"));
		azioniPren.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Noleggio, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Noleggio, Button> param) {
						final Button dettagliButton = new Button("Invia");
						dettagliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								mostraCose(param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(dettagliButton);
					}
				});

		List<Noleggio> listacompleta1 = null;
		try {
			listacompleta1 = noleggioservice.FindNoleggiByDettagli();
			for (Noleggio tmp : listacompleta1) {
				if (tmp.getData_inizio_noleggio().isBefore(LocalDateTime.now())) {
					listacompleta1.remove(tmp);
				}
			}
		} catch (BusinessException e) {
			/* lista vuota */ } catch (ConcurrentModificationException e) {
			System.out.println("errore");
		}

		ObservableList<Noleggio> listaNol1 = FXCollections.observableList(listacompleta1);
		tabellaPrenotazioni.setItems(listaNol1);
	}

	@FXML
	void InviaRisposta(ActionEvent event) throws BusinessException {

		noleggio.setDettagli(textRisposta.getText());
		noleggioservice.updateNoleggio(noleggio);
		labelRisposta.setVisible(true);
		initialize(null, null);

	}

	void mostraCose(Noleggio noleggio) {
		this.noleggio = noleggio;
		textRisposta.setVisible(true);
		bottoneRisposta.setVisible(true);
	}

}
