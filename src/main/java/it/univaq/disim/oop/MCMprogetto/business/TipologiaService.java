package it.univaq.disim.oop.MCMprogetto.business;

import java.util.List;
import it.univaq.disim.oop.MCMprogetto.domain.Tipologia;

public interface TipologiaService {

	Tipologia createTipologia(Tipologia tipologia) throws BusinessException;

	void deleteTipologia(Tipologia tipologia) throws TipologiaNonTrovataException, BusinessException;

	List<Tipologia> findAllTipologia() throws TipologiaNonTrovataException, BusinessException;
	
	void prima() throws BusinessException;
	
}
