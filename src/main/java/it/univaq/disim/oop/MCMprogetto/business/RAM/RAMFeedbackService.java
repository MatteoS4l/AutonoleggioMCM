package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.util.ArrayList;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackService;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Feedback;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public class RAMFeedbackService implements FeedbackService
{
	NoleggioService noleggioservice = new RAMNoleggioService();
	List<Feedback> listafeedback = new ArrayList<Feedback>();
	int cont_feedback = 1;

	
	@Override	// CREA
	public Feedback createFeedback(Feedback feedback) throws BusinessException 
	{
		feedback.setID(cont_feedback++);
		listafeedback.add(feedback);
		return feedback;
		
	}

	@Override	// FIND ALL FEEDBACK
	public List<Feedback> findAllFeedbackByVeicolo(Veicolo veicolo) throws FeedbackNonTrovatoException, BusinessException
	{	return listafeedback;	}


	@Override
	public void rilasciaFeedback(Feedback feedback) throws BusinessException {
		createFeedback(feedback);
		feedback.getNoleggio().setFeedLasciato(true);
		noleggioservice.updateNoleggio(feedback.getNoleggio());
	}

	@Override
	public List<Feedback> findAllFeedback() throws BusinessException {
		return listafeedback;
	}

}
