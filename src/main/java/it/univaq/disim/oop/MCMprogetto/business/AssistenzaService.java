package it.univaq.disim.oop.MCMprogetto.business;

import java.util.List;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;

public interface AssistenzaService {

	Assistenza createAssistenza(Assistenza assistenza) throws BusinessException;

	void updateAssistenza(Assistenza assistenza) throws BusinessException;
	
	void deleteAssistenza (Assistenza assistenza) throws BusinessException;

	List<Assistenza> findAllAssistenza() throws BusinessException;
	
	Assistenza richiediAssistenza (String richiesta, Noleggio noleggio) throws BusinessException;
	
	List<Assistenza> findListaAssistenzaByServita(boolean servita) throws BusinessException;

	Assistenza getAssistenzaByID(Integer id) throws AssistenzaNonTrovataException, BusinessException;
	
	public List<Assistenza> findAssistenzeByIds(List<Integer> ids) throws BusinessException;
}
