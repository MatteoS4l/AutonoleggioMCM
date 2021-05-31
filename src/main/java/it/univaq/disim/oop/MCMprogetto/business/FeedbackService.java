package it.univaq.disim.oop.MCMprogetto.business;

import java.util.List;
import it.univaq.disim.oop.MCMprogetto.domain.Feedback;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public interface FeedbackService 
{
	
	void createFeedback(Feedback feedback) throws BusinessException;
	
	List<Feedback> getListFeedback(Veicolo veicolo) throws FeedbackNonTrovatoException;
}
