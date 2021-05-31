package it.univaq.disim.oop.MCMprogetto.business.file;

import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackService;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaService;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;

public class FileMCMProgettoFactory extends MCMProgettoFactory
{
	private UtenteService utenteservice;
	private TipologiaService tipologiaservice;
	private NoleggioService noleggioservice;
	private VeicoloService veicoloservice;
	private FeedbackService feedbackservice;
	private AssistenzaService assistenzaservice;
	
	public FileMCMProgettoFactory()
	{
		utenteservice = new FileUtenteService();
		tipologiaservice = new FileTipologiaService();
		veicoloservice = new FileVeicoloService();
		noleggioservice = new FileNoleggioService();
		feedbackservice = new FileFeedbackService();
		assistenzaservice = new FileAssistenzaService();
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
