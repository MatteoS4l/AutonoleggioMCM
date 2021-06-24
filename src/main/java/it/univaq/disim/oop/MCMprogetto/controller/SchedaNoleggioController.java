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
	private Button bottoneNoleggio;
	@FXML
	private Label labelModelloMacchina;
	@FXML
	private Label labelErrore;
	@FXML
	private Button bottonePreventivo;
	@FXML
	private DatePicker dataInizioNol;
	@FXML
	private DatePicker dataFineNol;
	@FXML
	private Label labelPreventivo;
	@FXML
	private CheckBox selezioneTariffaPeriodo;
	@FXML
	private CheckBox selezioneTariffaKm;

	public SchedaNoleggioController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selezioneTariffaKm.setSelected(true);
		labelErrore.setVisible(false);
		bottoneNoleggio.disableProperty()
				.bind(dataInizioNol.valueProperty().isNull().or(dataFineNol.valueProperty().isNull()));
	}

	@Override
	public void initializeData(Veicolo veicolo, Utente utente) {
		this.veicolo = veicolo;
		this.utente = utente;
		labelModelloMacchina.setText(veicolo.getModello());
		labelPreventivo.setVisible(false);
	}

	@FXML // CALCOLA PREVENTIVO
	double CalcolaPreventivo(ActionEvent event) throws BusinessException {
		try {
			if (dataInizioNol.getValue() == null || dataFineNol.getValue() == null) {
				labelErrore.setText("Seleziona correttamente le date !");
				labelErrore.setVisible(true);
			} else {
				if (selezioneTariffaKm.isSelected()) {
					labelPreventivo.setText("0");
					labelPreventivo.setVisible(true);
					return 0;
				} else {
					labelErrore.setVisible(false);
					long daysBetween = ChronoUnit.DAYS.between(dataInizioNol.getValue(), dataFineNol.getValue());
					labelPreventivo.setText(String.valueOf(daysBetween * veicolo.getTariffa_periodo()));
					labelPreventivo.setVisible(true);
					return Double.parseDouble(labelPreventivo.getText());
				}
			}
		} catch (NullPointerException e) {
			labelErrore.setText("Seleziona correttamente le date !");
			labelErrore.setVisible(true);
		}

		return 0;

	}

	@FXML // NOLEGGIA AUTO
	Noleggio NoleggiaAuto(ActionEvent event) throws BusinessException {
		if (noleggioservice.disponibilitaDate(dataInizioNol.getValue().atStartOfDay(),
				dataFineNol.getValue().atStartOfDay(), veicolo)
				&& dataFineNol.getValue().isAfter(dataInizioNol.getValue())) {
			try {
				Noleggio noleggio = new Noleggio();

				noleggio.setPrezzo_noleggio(CalcolaPreventivo(null));
				noleggio.setAssistenza(null);
				noleggio.setCliente(utente);
				noleggio.setVeicolo(veicolo);
				noleggio.setData_inizio_noleggio(dataInizioNol.getValue().atStartOfDay());
				noleggio.setData_fine_noleggio(dataFineNol.getValue().atStartOfDay());
				noleggio.setKM_percorsi(0);
				noleggio.setServita(false);
				noleggioservice.createNoleggio(noleggio);
				dispatcher.renderView("home", utente, null);
			} catch (BusinessException e) {
				labelErrore.setText("Seleziona correttamente le date !");
				labelErrore.setVisible(true);
			}

			return noleggio;
		} else {
			labelErrore.setText("Non disponibile per queste date !");
			labelErrore.setVisible(true);
		}

		return null;
	}

	@FXML // SELEZIONA PAGAMENTO
	void SelezionaPagamento(ActionEvent event) {
		if (event.getSource().equals(selezioneTariffaKm)) {
			selezioneTariffaPeriodo.setSelected(false);
		}

		else {
			selezioneTariffaKm.setSelected(false);
		}
	}

	@FXML // FEEDBACK
	void VisualizzaFeed(ActionEvent event) {
		dispatcher.renderView("feedback", veicolo, utente);
	}

}
