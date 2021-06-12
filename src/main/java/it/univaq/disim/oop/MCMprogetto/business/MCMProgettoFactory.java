package it.univaq.disim.oop.MCMprogetto.business;

import it.univaq.disim.oop.MCMprogetto.business.RAM.RAMMCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.file.FileMCMProgettoFactory;

public abstract class MCMProgettoFactory
{
	private static MCMProgettoFactory factory = new RAMMCMProgettoFactory();
//	private static MCMProgettoFactory factory = new FileMCMProgettoFactory();
	
	public static MCMProgettoFactory getInstance()
	{	return factory; 	}
	
	
	public abstract UtenteService getUtenteService();
	public abstract VeicoloService getVeicoloService();
	public abstract TipologiaService getTipologiaService();
	public abstract NoleggioService getNoleggioService();
	public abstract FeedbackService getFeedbackService();
	public abstract AssistenzaService getAssistenzaService();
	
	
	
	
	
}
