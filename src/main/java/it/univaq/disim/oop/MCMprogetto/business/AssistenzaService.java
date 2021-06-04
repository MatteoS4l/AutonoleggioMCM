package it.univaq.disim.oop.MCMprogetto.business;

import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;

public interface AssistenzaService
{
	int createAssistenza(Assistenza assistenza) throws BusinessException;
	
	Assistenza getAssistenzaByID(int id) throws AssistenzaNonTrovataException, BusinessException;
	

}
