package it.univaq.disim.oop.MCMprogetto.business;

import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;

public interface AssistenzaService
{
	void createAssistenza(Assistenza assistenza) throws BusinessException;
	
	Assistenza getAssistenzaByID(int id) throws BusinessException;
	

}
