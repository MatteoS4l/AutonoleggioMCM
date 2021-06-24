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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class VeicoliUtenteController implements Initializable, DataInitializable<Utente, Object> {
	private ViewDispatcher dispatcher;
	private VeicoloService veicoloservice;
	private TipologiaService tipologiaservice;
	private NoleggioService noleggioservice;
	private Utente utente;

	@FXML
	private DatePicker dataInizio;
	@FXML
	private DatePicker dataFine;
	@FXML
	private TextField txt_tariffaKMmax;
	@FXML
	private TextField txt_tariffaKMmin;
	@FXML
	private TextField txt_modello;
	@FXML
	private TextField txt_tariffaGiornomin;
	@FXML
	private TextField txt_tariffaGiornomax;
	@FXML
	private ComboBox<String> box_alimentazione;
	@FXML
	private ComboBox<String> box_tipologia;
	@FXML
	private TableView<Veicolo> lista_filtrata;
	@FXML
	private TableColumn<Veicolo, String> col_veicolo;
	@FXML
	private TableColumn<Veicolo, String> col_alimentazione;
	@FXML
	private TableColumn<Veicolo, String> col_tipologia;
	@FXML
	private TableColumn<Veicolo, Double> col_km;
	@FXML
	private TableColumn<Veicolo, Double> col_giorno;
	@FXML
	private TableColumn<Veicolo, Button> col_azioni;

	public VeicoliUtenteController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		veicoloservice = factory.getVeicoloService();
		tipologiaservice = factory.getTipologiaService();
		noleggioservice = factory.getNoleggioService();
	}

	@FXML // APPLICA FILTRI
	void applica_filtri(ActionEvent event) throws BusinessException {
		List<Veicolo> listacompleta = new ArrayList<Veicolo>();
		listacompleta = veicoloservice.FindListaVeicoliByStato("disponibile");
		List<Veicolo> listafiltrata = new ArrayList<Veicolo>();
		for (Veicolo tmp : listacompleta) {
			if (!tmp.getTipologia().equalsIgnoreCase("sostitutivo")) {
				listafiltrata.add(tmp);
			}
		}

		if (box_tipologia.getValue() != null) {
			listafiltrata = Intersezione(listafiltrata,
					veicoloservice.FindListaVeicoliByTipologia(box_tipologia.getValue()));
		}

		if (box_alimentazione.getValue() != null) {
			listafiltrata = Intersezione(listafiltrata,
					veicoloservice.FindListaVeicoliByAlimentazione(box_alimentazione.getValue()));
		}

		if (!txt_modello.getText().isEmpty()) {
			listafiltrata = Intersezione(listafiltrata,
					veicoloservice.FindListaVeicoliByModello(txt_modello.getText()));
		}

		if (dataInizio.getValue() != null && dataFine.getValue() != null) {
			listafiltrata = Intersezione(listafiltrata, noleggioservice.FindVeicoliDisponibiliForDate(
					dataInizio.getValue().atStartOfDay(), dataFine.getValue().atStartOfDay()));
		}

		if (!(txt_tariffaGiornomin.getText().isEmpty() && txt_tariffaGiornomax.getText().isEmpty())) {
			double min, max;
			if (txt_tariffaGiornomin.getText().isEmpty()) {
				min = 0;
			} else {
				min = Double.parseDouble(txt_tariffaGiornomin.getText());
			}
			if (txt_tariffaGiornomax.getText().isEmpty()) {
				max = 999999;
			} else {
				max = Double.parseDouble(txt_tariffaGiornomax.getText());
			}

			listafiltrata = Intersezione(listafiltrata, veicoloservice.FindListaVeicoliByTariffaPeriodo(min, max));
		}

		if (!(txt_tariffaKMmin.getText().isEmpty() && txt_tariffaKMmax.getText().isEmpty())) {
			double min, max;
			if (txt_tariffaKMmin.getText().isEmpty()) {
				min = 0;
			} else {
				min = Double.parseDouble(txt_tariffaKMmin.getText());
			}
			if (txt_tariffaKMmax.getText().isEmpty()) {
				max = 999999;
			} else {
				max = Double.parseDouble(txt_tariffaKMmax.getText());
			}

			listafiltrata = Intersezione(listafiltrata, veicoloservice.FindListaVeicoliByTariffaKM(min, max));
		}

		ObservableList<Veicolo> listavn1 = FXCollections.observableArrayList(listafiltrata);
		lista_filtrata.setItems(listavn1);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			box_alimentazione.getItems().addAll(veicoloservice.FindListAlimentazioneVeicolo());
			box_alimentazione.getItems().add("Tutte");
			try {
				box_tipologia.getItems().addAll(tipologiaservice.FindAllTipologia());
			} catch (TipologiaNonTrovataException e1) {
				/* lista vuota */ }
			box_tipologia.getItems().add("Tutte");

			col_veicolo.setStyle("-fx-alignment: CENTER;");
			col_alimentazione.setStyle("-fx-alignment: CENTER;");
			col_tipologia.setStyle("-fx-alignment: CENTER;");
			col_km.setStyle("-fx-alignment: CENTER;");
			col_giorno.setStyle("-fx-alignment: CENTER;");
			col_azioni.setStyle("-fx-alignment: CENTER;");

			col_veicolo.setCellValueFactory(new PropertyValueFactory<>("modello"));
			col_alimentazione.setCellValueFactory(new PropertyValueFactory<>("alimentazione"));
			col_tipologia.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
			col_km.setCellValueFactory(new PropertyValueFactory<>("tariffa_chilometraggio"));
			col_giorno.setCellValueFactory(new PropertyValueFactory<>("tariffa_periodo"));
			col_azioni.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<Veicolo, Button>, ObservableValue<Button>>() {
						@Override
						public ObservableValue<Button> call(CellDataFeatures<Veicolo, Button> param) {
							final Button noleggiaButton = new Button("noleggia");
							noleggiaButton.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									dispatcher.renderView("scheda_noleggio", param.getValue(), utente);
								}
							});
							return new SimpleObjectProperty<Button>(noleggiaButton);
						}
					});
			applica_filtri(null);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = utente;
	}

	// INTERSEZIONE FRA LE LISTE
	public List<Veicolo> Intersezione(List<Veicolo> list1, List<Veicolo> list2) {
		List<Veicolo> list = new ArrayList<Veicolo>();
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
