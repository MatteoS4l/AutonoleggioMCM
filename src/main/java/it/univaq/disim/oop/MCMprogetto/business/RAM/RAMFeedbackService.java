package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.util.ArrayList;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackService;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Feedback;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public class RAMFeedbackService implements FeedbackService {
	List<Feedback> listaFeedback = new ArrayList<Feedback>();
	NoleggioService noleggioService = new RAMNoleggioService();
	int cont_feedback = 1;

	@Override // CREA
	public Feedback createFeedback(Feedback feedback) throws BusinessException {
		feedback.setID(cont_feedback++);
		listaFeedback.add(feedback);
		return feedback;
	}
	
	@Override // FIND ALL FEEDBACK
	public List<Feedback> findAllFeedback() throws BusinessException {
		return listaFeedback;
	}

	@Override // GET LISTA FEEDBACK DEL VEICOLO PASSATO
	public List<Feedback> findAllFeedbackByVeicolo(Veicolo veicolo)
			throws FeedbackNonTrovatoException, BusinessException {
		try {
			List<Feedback> listacompleta = new ArrayList<Feedback>();

			for (Feedback f : findAllFeedback()) {
				if (f.getNoleggio().getVeicolo().getTarga().equalsIgnoreCase(veicolo.getTarga())) {
					listacompleta.add(f);
				}
			}

			return listacompleta;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override // RILASCIA FEEDBACK
	public void rilasciaFeedback(Feedback feedback) throws BusinessException {
		createFeedback(feedback);
		feedback.getNoleggio().setFeedLasciato(true);
		noleggioService.updateNoleggio(feedback.getNoleggio());
	}

	

}
