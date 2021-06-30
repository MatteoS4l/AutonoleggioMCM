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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class NoleggiOperatoreController implements Initializable, DataInitializable<Noleggio> {
	private NoleggioService noleggioservice;

	@FXML
	private TableView<Noleggio> tabellaNoleggiOp;
	@FXML
	private TableColumn<Noleggio, Integer> idNoleggio;
	@FXML
	private TableColumn<Noleggio, String> veicoloNoleggio;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> dataInNol;
	@FXML
	private TableColumn<Noleggio, LocalDateTime> dataFiNol;
	@FXML
	private TableColumn<Noleggio, String> clienteNol;
	@FXML
	private CheckBox spuntaNoleggi;
	@FXML
	private DatePicker ricercaPerData;
	@FXML
	private Button bottoneApplica;

	public NoleggiOperatoreController() {
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idNoleggio.setStyle("-fx-alignment: CENTER;");
		veicoloNoleggio.setStyle("-fx-alignment: CENTER;");
		dataInNol.setStyle("-fx-alignment: CENTER;");
		dataFiNol.setStyle("-fx-alignment: CENTER;");
		clienteNol.setStyle("-fx-alignment: CENTER;");

		idNoleggio.setCellValueFactory(new PropertyValueFactory<Noleggio, Integer>("ID"));
		dataInNol.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("dataInizioNoleggio"));
		dataFiNol.setCellValueFactory(new PropertyValueFactory<Noleggio, LocalDateTime>("dataFineNoleggio"));
		clienteNol.setCellValueFactory(
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

		List<Noleggio> listacompleta1 = null;
		try {
			listacompleta1 = noleggioservice.findAllNoleggi();
		} catch (BusinessException e) {
			System.err.println(e.getMessage());
		}
		ObservableList<Noleggio> listaNol1 = FXCollections.observableList(listacompleta1);
		tabellaNoleggiOp.setItems(listaNol1);
	}

	@FXML // APPLICA FILTRI
	void ApplicaFiltri(ActionEvent event) throws BusinessException {
		List<Noleggio> listafiltrata = noleggioservice.findAllNoleggi();

		if (spuntaNoleggi.isSelected()) {
			listafiltrata = Intersezione(listafiltrata, noleggioservice.findNoleggiInCorso());
		}
		if (ricercaPerData.getValue() != null) {
			List<Noleggio> listaDataFine = new ArrayList<Noleggio>();
			for(Noleggio tmp : noleggioservice.findAllNoleggi()) {
				if (tmp.getDataFineNoleggio().isEqual(ricercaPerData.getValue())) { 
					listaDataFine.add(tmp);
				}
			}
			listafiltrata = Intersezione(listafiltrata, listaDataFine);
		}
		ObservableList<Noleggio> listaNol = FXCollections.observableList(listafiltrata);
		tabellaNoleggiOp.setItems(listaNol);
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
