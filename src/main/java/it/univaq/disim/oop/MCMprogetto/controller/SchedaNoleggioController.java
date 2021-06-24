package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class SchedaNoleggioController implements Initializable, DataInitializable<Veicolo, Utente> {
	private ViewDispatcher dispatcher;
	private NoleggioService noleggioservice;
	private Veicolo veicolo;
	private Utente utente;
	private Noleggio noleggio;

	@FXML
	private Button bottone_noleggio;
	@FXML
	private Label label_modello_macchina;
	@FXML
	private Label label_errore;
	@FXML
	private Button bottone_preventivo;
	@FXML
	private DatePicker data_inizio_nol;
	@FXML
	private DatePicker data_fine_nol;
	@FXML
	private Label label_preventivo;
	@FXML
	private CheckBox selezione_tariffa_periodo;
	@FXML
	private CheckBox selezione_tariffa_km;

	public SchedaNoleggioController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
	}

	@FXML // CALCOLA PREVENTIVO
	double calcolaPreventivo(ActionEvent event) throws BusinessException {
		try {
			if (data_inizio_nol.getValue() == null || data_fine_nol.getValue() == null) {
				label_errore.setText("Seleziona correttamente le date !");
				label_errore.setVisible(true);
			} else {
				if (selezione_tariffa_km.isSelected()) {
					label_preventivo.setText("0");
					label_preventivo.setVisible(true);
					return 0;
				} else {
					label_errore.setVisible(false);
					long daysBetween = ChronoUnit.DAYS.between(data_inizio_nol.getValue(), data_fine_nol.getValue());
					label_preventivo.setText(String.valueOf(daysBetween * veicolo.getTariffa_periodo()));
					label_preventivo.setVisible(true);
					return Double.parseDouble(label_preventivo.getText());
				}
			}
		} catch (NullPointerException e) {
			label_errore.setText("Seleziona correttamente le date !");
			label_errore.setVisible(true);
		}

		return 0;

	}

	@FXML // NOLEGGIA AUTO
	Noleggio noleggiaAuto(ActionEvent event) throws BusinessException {
		if (noleggioservice.DisponibilitaDate(data_inizio_nol.getValue().atStartOfDay(),
				data_fine_nol.getValue().atStartOfDay(), veicolo)
				&& data_fine_nol.getValue().isAfter(data_inizio_nol.getValue())) {
			try {
				Noleggio noleggio = new Noleggio();

				noleggio.setPrezzo_noleggio(calcolaPreventivo(null));
				noleggio.setAssistenza(null);
				noleggio.setCliente(utente);
				noleggio.setVeicolo(veicolo);
				noleggio.setData_inizio_noleggio(data_inizio_nol.getValue().atStartOfDay());
				noleggio.setData_fine_noleggio(data_fine_nol.getValue().atStartOfDay());
				noleggio.setKM_percorsi(0);
				noleggio.setServita(false);
				noleggioservice.createNoleggio(noleggio);
				dispatcher.renderView("home", utente, null);
			} catch (BusinessException e) {
				label_errore.setText("Seleziona correttamente le date !");
				label_errore.setVisible(true);
			}

			return noleggio;
		} else {
			label_errore.setText("Non disponibile per queste date !");
			label_errore.setVisible(true);
		}

		return null;
	}

	@FXML // SELEZIONA PAGAMENTO
	void SelezionaPagamento(ActionEvent event) {
		if (event.getSource().equals(selezione_tariffa_km)) {
			selezione_tariffa_periodo.setSelected(false);
		}

		else {
			selezione_tariffa_km.setSelected(false);
		}
	}

	@FXML // FEEDBACK
	void visualizzaFeed(ActionEvent event) {
		dispatcher.renderView("feedback", veicolo, utente);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selezione_tariffa_km.setSelected(true);
		label_errore.setVisible(false);
		bottone_noleggio.disableProperty()
				.bind(data_inizio_nol.valueProperty().isNull().or(data_fine_nol.valueProperty().isNull()));

	}

	@Override
	public void initializeData(Veicolo veicolo, Utente utente) {
		this.veicolo = veicolo;
		this.utente = utente;
		label_modello_macchina.setText(veicolo.getModello());
		label_preventivo.setVisible(false);
	}

}
