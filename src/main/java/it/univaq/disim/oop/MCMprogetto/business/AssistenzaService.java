package it.univaq.disim.oop.MCMprogetto.business;

import java.util.List;

import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;

public interface AssistenzaService
{
	int createAssistenza(Assistenza assistenza) throws BusinessException;
	
	Assistenza getAssistenzaByID(int id) throws AssistenzaNonTrovataException, BusinessException;
	
	List<Assistenza> getListaAssistenzaByServita(boolean servita) throws BusinessException;
	
	public void updateAssistenza(Assistenza assistenza) throws BusinessException;

}
