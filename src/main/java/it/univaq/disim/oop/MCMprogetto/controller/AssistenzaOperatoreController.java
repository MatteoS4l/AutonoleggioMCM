package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
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
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class AssistenzaOperatoreController implements Initializable, DataInitializable<Utente> {
	private ViewDispatcher dispatcher;
	private NoleggioService noleggioservice;
	private Assistenza assistenza;
	private Noleggio noleggio;
	private Utente utente;

	@FXML
	private TableView<Noleggio> tabellaAssistenzaOp;
	@FXML
	private TableColumn<Noleggio, String> clienteAssistenzaOp;
	@FXML
	private TableColumn<Noleggio, String> veicoloAssistenzaOp;
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

		try {
			for (Noleggio n : noleggioservice.findAllNoleggi()) {

				System.out.println("listaa   " + n.getListaAssistenza().size());
				clienteAssistenzaOp.setCellValueFactory(
						new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>() {
							@Override
							public ObservableValue<String> call(CellDataFeatures<Noleggio, String> tmp) {
								return new SimpleStringProperty(tmp.getValue().getCliente().getUsername());

							}
						});

				veicoloAssistenzaOp.setCellValueFactory(
						new Callback<TableColumn.CellDataFeatures<Noleggio, String>, ObservableValue<String>>() {
							@Override
							public ObservableValue<String> call(CellDataFeatures<Noleggio, String> tmp) {
								return new SimpleStringProperty(tmp.getValue().getVeicolo().getModello());

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

		} catch (

		BusinessException e) {
			labelRispostaAssistenza.setTextFill(Color.RED);
			labelRispostaAssistenza.setText("Errore generico");
		}
	}

	@Override
	public void initializeData(Utente utente) {
		try {
			this.utente = utente;
			List<Noleggio> listaAss = new ArrayList<Noleggio>();
			for (Noleggio n : noleggioservice.findAllNoleggi()) {
				if (!n.getListaAssistenza().isEmpty()) {
					for (Assistenza a : n.getListaAssistenza()) {
						if (a.getRisposta() == null) {
							listaAss.add(n);
							System.out.println(a.getRisposta());
						}

					}
				}
			}
			ObservableList<Noleggio> listaAssOb = FXCollections.observableList(listaAss);
			System.out.println(listaAssOb.size());
			tabellaAssistenzaOp.setItems(listaAssOb);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML // INVIA RISPOSTA
	void InviaRispostaAssistenza(ActionEvent event) throws BusinessException {
		assistenza.setRisposta(textRispostaAssistenza.getText());
		noleggioservice.aggiornaAssistenza(assistenza);

		textRispostaAssistenza.clear();
		textRispostaAssistenza.setVisible(false);
		labelRispostaAssistenza.setVisible(true);
		bottoneInvio.setVisible(false);

		initialize(null, null);
	}

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
				dispatcher.renderView("sostituzione", noleggio);
			}
		} catch (NullPointerException e) {
			System.out.println("errore");
		}

	}

}
