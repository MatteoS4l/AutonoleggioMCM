package it.univaq.disim.oop.MCMprogetto;

import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaService;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.business.RAM.RAMTipologiaService;
import it.univaq.disim.oop.MCMprogetto.view.ViewDispatcher;
import it.univaq.disim.oop.MCMprogetto.view.ViewException;
import javafx.application.Application;
import javafx.stage.Stage;

public class MCMprogettoMain extends Application
{
	private UtenteService utenteservice;
	private TipologiaService tipologiaservice;
	private VeicoloService veicoloservice;

	public MCMprogettoMain ()
	{
		MCMProgettoFactory factory = MCMProgettoFactory.getInstance();
    	utenteservice = factory.getUtenteService();
    	tipologiaservice = factory.getTipologiaService();
    	veicoloservice = factory.getVeicoloService();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{	try
		{
			utenteservice.primaAggiunta();
			tipologiaservice.primaAggiunta();
			veicoloservice.primaAggiunta();
			ViewDispatcher dispatcher = ViewDispatcher.getInstance();
			dispatcher.login(stage);
		}
		catch (ViewException e)
		{	e.printStackTrace();	}
		
		
	}

}
