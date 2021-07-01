package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class AssistenzaOperatoreController implements Initializable, DataInitializable<Utente> {
	private ViewDispatcher dispatcher;
	private Utente utente;
	private AssistenzaService assistenzaservice;
	private NoleggioService noleggioservice;
	private Assistenza assistenza;

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
	int i;

	public AssistenzaOperatoreController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		assistenzaservice = factory.getAssistenzaService();
		noleggioservice = factory.getNoleggioService();
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
						try {
							tmp.getValue().setNoleggio(noleggioservice.findNoleggioByID(tmp.getValue().getIdNoleggio()));
						} catch (NoleggioNonTrovatoException e) {
							e.printStackTrace();
						} catch (BusinessException e) {
							e.printStackTrace();
						}
						return new SimpleStringProperty(tmp.getValue().getNoleggio().getCliente().getUsername());
					}
				});
		veicoloAssistenzaOp.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Assistenza, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Assistenza, String> tmp) {
						return new SimpleStringProperty(tmp.getValue().getNoleggio().getVeicolo().getModello());
					}
				});
		dettagliAssistenzaOp.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Assistenza, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Assistenza, String> tmp) {
						return new SimpleStringProperty(tmp.getValue().getRichiesta());
					}
				});
		azioniAssistenzaOp.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Assistenza, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Assistenza, Button> param) {
						final Button dettagliButton = new Button("Rispondi");
						dettagliButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								MostraCose(param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(dettagliButton);
					}
				});
	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = utente;
		try {
			ObservableList<Assistenza> ListNolOb = FXCollections.observableList(assistenzaservice.findListaAssistenzaByServita(false));
			tabellaAssistenzaOp.setItems(ListNolOb);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML // INVIA RISPOSTA
	void InviaRispostaAssistenza(ActionEvent event) throws BusinessException {
		assistenza.setRisposta(textRispostaAssistenza.getText());
		assistenzaservice.updateAssistenza(assistenza);

		textRispostaAssistenza.clear();
		textRispostaAssistenza.setVisible(false);
		labelRispostaAssistenza.setVisible(true);
		bottoneInvio.setVisible(false);

		initializeData(utente);
	}

	// MOSTRA COSE
	void MostraCose(Assistenza assistenza) {
		this.assistenza = assistenza;
		sceltaAssistenza.setVisible(true);
		bottoneConferma.setVisible(true);
	}

	@FXML
	void ConfermaSelezione(ActionEvent event) {
		try {
			if (sceltaAssistenza.getValue().equalsIgnoreCase("Manutenzione")) {
				textRispostaAssistenza.setVisible(true);
				bottoneInvio.setVisible(true);
			} else {
				dispatcher.renderView("sostituzione", assistenza);
			}
		} catch (NullPointerException e) {
			System.out.println("errore");
		}

	}

}
