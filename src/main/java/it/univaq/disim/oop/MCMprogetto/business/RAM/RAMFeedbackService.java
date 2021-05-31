package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.util.List;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackService;
import it.univaq.disim.oop.MCMprogetto.domain.Feedback;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public class RAMFeedbackService implements FeedbackService
{

	@Override
	public void createFeedback(Feedback feedback) throws BusinessException {
		
	}

	@Override
	public List<Feedback> getListFeedback(Veicolo veicolo) throws FeedbackNonTrovatoException {
		return null;
	}

}
