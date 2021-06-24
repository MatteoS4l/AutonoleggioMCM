package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class AssistenzaOperatoreController implements Initializable, DataInitializable<Utente, Assistenza> {
	private ViewDispatcher dispatcher;
	private AssistenzaService assistenzaservice;
	private Assistenza assistenza;
	private Utente utente;

	@FXML
	private TableView<Assistenza> tabellaAssistenzaOp;
	@FXML
	private TableColumn<Assistenza, String> clienteAssistenzaOp;
	@FXML
	private TableColumn<Assistenza, String> veicoloAssistenzaOp;
	@FXML
	private TableColumn<Assistenza, String> dettagliAssistenzaOp;
	@FXML
	private TableColumn<Assistenza, Button> azioniAssistenzaOp;
	@FXML
	private TextField textRispostaAssistenza;
	@FXML
	private Button bottoneInvio;
	@FXML
	private Label labelRispostaAssistenza;
	@FXML
	private ComboBox<String> sceltaAssistenza;
	@FXML
	private Button bottoneConferma;

	public AssistenzaOperatoreController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		assistenzaservice = factory.getAssistenzaService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sceltaAssistenza.getItems().addAll("Manutenzione", "Sostituzione");
		sceltaAssistenza.setVisible(false);
		textRispostaAssistenza.setVisible(false);
		labelRispostaAssistenza.setVisible(false);
		bottoneInvio.setVisible(false);
		bottoneConferma.setVisible(false);

		clienteAssistenzaOp.setStyle("-fx-alignment: CENTER;");
		veicoloAssistenzaOp.setStyle("-fx-alignment: CENTER;");
		dettagliAssistenzaOp.setStyle("-fx-alignment: CENTER;");
		azioniAssistenzaOp.setStyle("-fx-alignment: CENTER;");

		clienteAssistenzaOp.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Assistenza, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Assistenza, String> tmp) {
						if (tmp.getValue().getCliente() == null) {
							return new SimpleStringProperty();
						} else {
							return new SimpleStringProperty(tmp.getValue().getCliente().getUsername());
						}
					}
				});

		veicoloAssistenzaOp.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Assistenza, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Assistenza, String> tmp) {
						if (tmp.getValue().getVeicolo() == null) {
							return new SimpleStringProperty();
						} else {
							return new SimpleStringProperty(tmp.getValue().getVeicolo().getModello());
						}
					}
				});

		dettagliAssistenzaOp.setCellValueFactory(new PropertyValueFactory<Assistenza, String>("dettagli"));
		azioniAssistenzaOp.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Assistenza, Button>, ObservableValue<Button>>() {
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
		try {
			List<Assistenza> lista_ass = new ArrayList<Assistenza>();
			lista_ass = assistenzaservice.getListaAssistenzaByServita(false);
			ObservableList<Assistenza> lista_a = FXCollections.observableList(lista_ass);
			tabellaAssistenzaOp.setItems(lista_a);
		} catch (BusinessException e) {
			labelRispostaAssistenza.setTextFill(Color.RED);
			labelRispostaAssistenza.setText("Errore generico");
		}
	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = utente;
	}

	@FXML // INVIA RISPOSTA
	void inviaRispostaAssistenza(ActionEvent event) throws BusinessException {
		assistenza.setRisposta(textRispostaAssistenza.getText());
		assistenza.setServita(true);
		assistenzaservice.updateAssistenza(assistenza);

		textRispostaAssistenza.clear();
		textRispostaAssistenza.setVisible(false);
		labelRispostaAssistenza.setVisible(true);
		bottoneInvio.setVisible(false);

		initialize(null, null);
	}

	void mostraCose(Assistenza assistenza) {
		this.assistenza = assistenza;
		sceltaAssistenza.setVisible(true);
		bottoneConferma.setVisible(true);
	}

	@FXML
	void confermaSelezione(ActionEvent event) {
		try {
			if (sceltaAssistenza.getValue().equalsIgnoreCase("Manutenzione")) {
				textRispostaAssistenza.setVisible(true);
				bottoneInvio.setVisible(true);
			} else {
				dispatcher.renderView("sostituzione", utente, assistenza);
			}
		} catch (NullPointerException e) {
			System.out.println("errore");
		}

	}

}
