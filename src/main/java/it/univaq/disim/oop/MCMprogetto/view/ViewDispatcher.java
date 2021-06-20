package it.univaq.disim.oop.MCMprogetto.view;

import java.io.IOException;
import it.univaq.disim.oop.MCMprogetto.controller.DataInitializable;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import it.univaq.disim.oop.MCMprogetto.view.ViewException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class ViewDispatcher
{
	private static final String FXML = ".fxml";
	private static final String VISTE = "/viste/";
	private static ViewDispatcher instance = new ViewDispatcher();
	private Stage stage;
	private BorderPane layout;

	
	
	private ViewDispatcher()
	{		}
	
	public static ViewDispatcher getInstance()
	{	return instance;	}
	
	
	// metodo che serve per il login
	public void login(Stage stage) throws ViewException
	{
		this.stage = stage;
		Parent loginView = loadView("login").getView();
		Scene scene = new Scene(loginView);
		stage.setScene(scene);
		stage.show();
	}

	
	// metodo che carica schermata di layout
	public void loggedIn(Utente utente)		
	{	try
		{
			View<Utente,Object> layoutView = loadView("layout");
			DataInitializable<Utente,Object> layoutController = layoutView.getController();
			layoutController.initializeData(utente);
	
			layout = (BorderPane) layoutView.getView();
			renderView("home", utente,null);
			Scene scene = new Scene(layout);
			stage.setScene(scene);
		}
		catch (ViewException e)
		{
			renderError(e);
		}
	}
	
	
	// metodo che apre una schermata generica
	public <T,S> void GetView( String viewName, T data, S data2)
	{	try
		{	View<T,S> view = loadView(viewName);
			Scene scene = new Scene(view.getView());
			stage.setScene(scene);
			DataInitializable<T,S> controller = view.getController();
			controller.initializeData(data);
		}
		catch (ViewException e)
		{	renderError(e);	  }
	}
	
	
	// metodo che serve per tornare alla pagina di login
	public void logout()
	{	try
		{
			Parent loginView = loadView("login").getView();
			Scene scene = new Scene(loginView);
			stage.setScene(scene);
		}
		catch (ViewException e)
		{	renderError(e);		}
	}

	
	// metodo che carica una generica vista
	public <T,S> void renderView(String viewName, T data, S data2)
	{	try
		{
			View<T,S> view = loadView(viewName);
			DataInitializable<T,S> controller = view.getController();
			controller.initializeData(data, data2);
			controller.initializeData(data);
			layout.setCenter(view.getView());
		}
		catch (ViewException e)
		{	renderError(e);	  }
	}

	
	// prende una vista dalla cartella per caricarla
	private <T,S> View<T,S> loadView(String viewName) throws ViewException
	{	try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource(VISTE + viewName + FXML));
			Parent parent = (Parent) loader.load();
			return new View<T,S>(parent, loader.getController());
		}
		catch (IOException ex)
		{	throw new ViewException(ex);	}
	}
	
	
	public void renderError(Exception e)
	{
		e.printStackTrace();
		System.exit(1);
	}

}
