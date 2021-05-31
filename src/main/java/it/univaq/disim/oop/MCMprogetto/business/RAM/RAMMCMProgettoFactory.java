package it.univaq.disim.oop.MCMprogetto.business.RAM;

import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackService;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaService;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;

public class RAMMCMProgettoFactory extends MCMProgettoFactory
{
	private UtenteService utenteservice;
	private TipologiaService tipologiaservice;
	private NoleggioService noleggioservice;
	private VeicoloService veicoloservice;
	private FeedbackService feedbackservice;
	private AssistenzaService assistenzaservice;
	
	
	public RAMMCMProgettoFactory()
	{
		utenteservice = new RAMUtenteService();
		tipologiaservice = new RAMTipologiaService();
		veicoloservice = new RAMVeicoloService();
		noleggioservice = new RAMNoleggioService();
		feedbackservice = new RAMFeedbackService();
		assistenzaservice = new RAMAssistenzaService();
	}
	
	
	@Override
	public UtenteService getUtenteService() {
		return utenteservice;
	}
	@Override
	public VeicoloService getVeicoloService() {
		return veicoloservice;
	}
	@Override
	public TipologiaService getTipologiaService() {
		return tipologiaservice;
	}
	@Override
	public NoleggioService getNoleggioService() {
		return noleggioservice;
	}
	@Override
	public FeedbackService getFeedbackService() {
		return feedbackservice;
	}
	@Override
	public AssistenzaService getAssistenzaService() {
		return assistenzaservice;
	}

	
	
	
	
	
}
