package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaNonTrovataException;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Alimentazione;
import it.univaq.disim.oop.MCMprogetto.domain.Tipologia;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox; //Da questa schermata si noleggia nella tabella
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class VeicoliUtenteController implements Initializable, DataInitializable<Utente> {
	private ViewDispatcher dispatcher;
	private VeicoloService veicoloservice;
	private TipologiaService tipologiaservice; ///////////////////////
	private NoleggioService noleggioservice;

	@FXML
	private DatePicker dataInizio;
	@FXML
	private DatePicker dataFine;
	@FXML
	private TextField txtTariffaKmMax;
	@FXML
	private TextField txtTariffaKmMin;
	@FXML
	private TextField txtModello;
	@FXML
	private TextField txtTariffaGiornoMin;
	@FXML
	private TextField txtTariffaGiornoMax;
	@FXML
	private ComboBox<Alimentazione> boxAlimentazione;
	@FXML
	private ComboBox<Tipologia> boxTipologia;
	@FXML
	private TableView<Veicolo> listaFiltrata;
	@FXML
	private TableColumn<Veicolo, String> colVeicolo;
	@FXML
	private TableColumn<Veicolo, String> colAlimentazione;
	@FXML
	private TableColumn<Veicolo, String> colTipologia;
	@FXML
	private TableColumn<Veicolo, Double> colKm;
	@FXML
	private TableColumn<Veicolo, Double> colGiorno;
	@FXML
	private TableColumn<Veicolo, Button> colAzioni;

	public VeicoliUtenteController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		veicoloservice = factory.getVeicoloService();
		tipologiaservice = factory.getTipologiaService();
		noleggioservice = factory.getNoleggioService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			boxAlimentazione.getItems().addAll(Alimentazione.values());
			try {
				boxTipologia.getItems().addAll(tipologiaservice.findAllTipologia());
			} catch (TipologiaNonTrovataException e1) {
				System.err.println(e1.getMessage());
			}

			colVeicolo.setStyle("-fx-alignment: CENTER;");
			colAlimentazione.setStyle("-fx-alignment: CENTER;");
			colTipologia.setStyle("-fx-alignment: CENTER;");
			colKm.setStyle("-fx-alignment: CENTER;");
			colGiorno.setStyle("-fx-alignment: CENTER;");
			colAzioni.setStyle("-fx-alignment: CENTER;");

			colVeicolo.setCellValueFactory(new PropertyValueFactory<>("modello"));
			colAlimentazione.setCellValueFactory(new PropertyValueFactory<>("alimentazione"));
			colTipologia.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
			colKm.setCellValueFactory(new PropertyValueFactory<>("tariffaChilometraggio"));
			colGiorno.setCellValueFactory(new PropertyValueFactory<>("tariffaPeriodo"));
			colAzioni.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<Veicolo, Button>, ObservableValue<Button>>() {
						@Override
						public ObservableValue<Button> call(CellDataFeatures<Veicolo, Button> param) {
							final Button noleggiaButton = new Button("noleggia");
							noleggiaButton.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									dispatcher.renderView("scheda_noleggio", param.getValue());
								}
							});
							return new SimpleObjectProperty<Button>(noleggiaButton);
						}
					});
			ApplicaFiltri(null);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initializeData(Utente utente) {
	}

	@FXML // APPLICA FILTRI
	void ApplicaFiltri(ActionEvent event) throws BusinessException {
		List<Veicolo> listacompleta = new ArrayList<Veicolo>();
		listacompleta = veicoloservice.findListaVeicoliByStato("disponibile");
		List<Veicolo> listafiltrata = new ArrayList<Veicolo>();
		for (Veicolo tmp : listacompleta) {
			if (!tmp.getTipologia().getNome().equalsIgnoreCase("sostitutivo")) {
				listafiltrata.add(tmp);
			}
		}
		if (boxTipologia.getValue() != null) {
			listafiltrata = Intersezione(listafiltrata,
					veicoloservice.findListaVeicoliByTipologia(boxTipologia.getValue().getNome()));
		}
		if (boxAlimentazione.getValue() != null) {
			listafiltrata = Intersezione(listafiltrata,
					veicoloservice.findListaVeicoliByAlimentazione(boxAlimentazione.getValue()));
		}
		if (!txtModello.getText().isEmpty()) {
			listafiltrata = Intersezione(listafiltrata, veicoloservice.findListaVeicoliByModello(txtModello.getText()));
		}
		if (dataInizio.getValue() != null && dataFine.getValue() != null) {
			listafiltrata = Intersezione(listafiltrata, noleggioservice.findVeicoliDisponibiliForDate(
					dataInizio.getValue(), dataFine.getValue()));
		}
		if (!(txtTariffaGiornoMin.getText().isEmpty() && txtTariffaGiornoMax.getText().isEmpty())) {
			double min, max;
			if (txtTariffaGiornoMin.getText().isEmpty()) {
				min = 0;
			} else {
				min = Double.parseDouble(txtTariffaGiornoMin.getText());
			}
			if (txtTariffaGiornoMax.getText().isEmpty()) {
				max = 999999;
			} else {
				max = Double.parseDouble(txtTariffaGiornoMax.getText());
			}

			listafiltrata = Intersezione(listafiltrata, veicoloservice.findListaVeicoliByTariffaPeriodo(min, max));
		}

		if (!(txtTariffaKmMin.getText().isEmpty() && txtTariffaKmMax.getText().isEmpty())) {
			double min, max;
			if (txtTariffaKmMin.getText().isEmpty()) {
				min = 0;
			} else {
				min = Double.parseDouble(txtTariffaKmMin.getText());
			}
			if (txtTariffaKmMax.getText().isEmpty()) {
				max = 999999;
			} else {
				max = Double.parseDouble(txtTariffaKmMax.getText());
			}

			listafiltrata = Intersezione(listafiltrata, veicoloservice.findListaVeicoliByTariffaKM(min, max));
		}

		ObservableList<Veicolo> listavn1 = FXCollections.observableArrayList(listafiltrata);
		listaFiltrata.setItems(listavn1);
	}

	// INTERSEZIONE FRA LE LISTE
	public List<Veicolo> Intersezione(List<Veicolo> list1, List<Veicolo> list2) {
		List<Veicolo> list = new ArrayList<Veicolo>();
		for (int i = 0; i < list1.size(); i++) {
			for (int j = 0; j < list2.size(); j++) {
				if (list1.get(i).getTarga().equalsIgnoreCase(list2.get(j).getTarga())) {
					list.add(list1.get(i));
				}
			}
		}
		return list;
	}

}
