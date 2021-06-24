package it.univaq.disim.oop.MCMprogetto;

import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import it.univaq.disim.oop.MCMprogetto.view.ViewException;
import javafx.application.Application;
import javafx.stage.Stage;

public class MCMprogettoMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			ViewDispatcher dispatcher = ViewDispatcher.getInstance();
			dispatcher.login(stage);
		} catch (ViewException e) {
			e.printStackTrace();
		}

	}

}
