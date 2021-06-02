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

	@Override
	public void createFeedback(Feedback feedback) throws BusinessException 
	{
		listafeedback.add(feedback);
		
	}

	@Override
	public List<Feedback> getListFeedback(Veicolo veicolo) throws FeedbackNonTrovatoException
	{
		return listafeedback;
	}

}
