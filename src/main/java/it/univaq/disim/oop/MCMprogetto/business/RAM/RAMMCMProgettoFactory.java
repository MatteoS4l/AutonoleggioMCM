package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackService;
import it.univaq.disim.oop.MCMprogetto.business.MCMProgettoFactory;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaService;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
import it.univaq.disim.oop.MCMprogetto.domain.Feedback;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Tipologia;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public class RAMMCMProgettoFactory extends MCMProgettoFactory {
	private final UtenteService utenteservice;
	private final TipologiaService tipologiaservice;
	private final NoleggioService noleggioservice;
	private final VeicoloService veicoloservice;
	private final FeedbackService feedbackservice;
	private final AssistenzaService assistenzaservice;
	
	private static final List<Noleggio> listaNoleggi = new ArrayList<Noleggio>();
	private static final List<Veicolo> listaVeicoli = new ArrayList<Veicolo>();
	private static final List<Utente> listaUtenti = new ArrayList<Utente>();
	private static final List<Tipologia> listaTipologie = new ArrayList<Tipologia>();
	private static final List<Feedback> listaFeedback = new ArrayList<Feedback>();
	private static final List<Assistenza> listaAssistenza = new ArrayList<Assistenza>();

	public RAMMCMProgettoFactory() {
		utenteservice = new RAMUtenteService(listaUtenti);
		veicoloservice = new RAMVeicoloService(listaVeicoli);
		feedbackservice = new RAMFeedbackService(listaFeedback);
		assistenzaservice = new RAMAssistenzaService(listaAssistenza);
		noleggioservice = new RAMNoleggioService(listaNoleggi, veicoloservice, assistenzaservice);
		tipologiaservice = new RAMTipologiaService(listaTipologie, veicoloservice);
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
