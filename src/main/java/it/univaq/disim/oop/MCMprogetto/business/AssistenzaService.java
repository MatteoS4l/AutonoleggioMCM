package it.univaq.disim.oop.MCMprogetto.business;

import java.util.List;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;

public interface AssistenzaService {

	Assistenza createAssistenza(Assistenza assistenza) throws BusinessException;

	void updateAssistenza(Assistenza assistenza) throws BusinessException;

	List<Assistenza> findAllAssistenza() throws BusinessException;

	Assistenza getAssistenzaByID(Integer id) throws AssistenzaNonTrovataException, BusinessException;

	List<Assistenza> findListaAssistenzaByServita(boolean servita) throws BusinessException;

}
