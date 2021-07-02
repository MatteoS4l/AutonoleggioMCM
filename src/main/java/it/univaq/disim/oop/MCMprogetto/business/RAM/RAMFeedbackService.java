package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackService;
import it.univaq.disim.oop.MCMprogetto.domain.Feedback;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public class RAMFeedbackService implements FeedbackService {
	private final List<Feedback> listaFeedback;
	int contFeedback = 1;

	public RAMFeedbackService(List<Feedback> listaFeedback) {
		this.listaFeedback = listaFeedback;
	}

	@Override // CREA
	public Feedback createFeedback(Feedback feedback) throws BusinessException {
		feedback.setID(contFeedback++);
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
	}

}
