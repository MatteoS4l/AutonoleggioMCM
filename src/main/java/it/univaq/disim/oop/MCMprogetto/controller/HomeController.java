package it.univaq.disim.oop.MCMprogetto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Sesso;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class HomeController implements Initializable, DataInitializable<Utente> {
	ViewDispatcher dispatcher;
	NoleggioService noleggioservice;

	// aggiustare un po la grafica
	@FXML
	private Label benvenutoLabel;

	public HomeController() {
		dispatcher = ViewDispatcher.getInstance();
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		noleggioservice = factory.getNoleggioService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		try {
//			noleggioservice.checkStatoVeicoli();
//		} catch (BusinessException e) {
//			System.out.println("errore");
//		}

	}

	@Override
	public void initializeData(Utente utente) {
		if (utente.getSesso() == Sesso.UOMO) {
			benvenutoLabel.setText("Benvenuto " + utente.getNome() + " " + utente.getCognome());
		}

		if (utente.getSesso() == Sesso.DONNA) {
			benvenutoLabel.setText("Benvenuta " + utente.getNome() + " " + utente.getCognome());
		}

	}

}
