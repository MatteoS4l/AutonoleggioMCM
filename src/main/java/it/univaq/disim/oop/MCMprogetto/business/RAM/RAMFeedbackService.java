package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.util.ArrayList;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackService;
import it.univaq.disim.oop.MCMprogetto.domain.Feedback;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public class RAMFeedbackService implements FeedbackService
{
	List<Feedback> listafeedback = new ArrayList<Feedback>();
	int cont_feedback = 1;

	
	@Override	// CREA
	public void createFeedback(Feedback feedback) throws BusinessException 
	{
		feedback.setID(cont_feedback++);
		listafeedback.add(feedback);
		
	}

	@Override	// FIND ALL FEEDBACK
	public List<Feedback> findAllFeedbackByVeicolo(Veicolo veicolo) throws FeedbackNonTrovatoException, BusinessException
	{	return listafeedback;	}

}
