package it.univaq.disim.oop.MCMprogetto;

import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaService;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import it.univaq.disim.oop.MCMprogetto.view.ViewException;
import javafx.application.Application;
import javafx.stage.Stage;

public class MCMprogettoMain extends Application {

	VeicoloService v;
	UtenteService u;
	TipologiaService t;

	public MCMprogettoMain() {
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
		t = factory.getTipologiaService();
		u = factory.getUtenteService();
		v = factory.getVeicoloService();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			v.prima();
			u.prima();
			t.prima();
			ViewDispatcher dispatcher = ViewDispatcher.getInstance();
			dispatcher.login(stage);
		} catch (ViewException e) {
			e.printStackTrace();
		}

	}

}
