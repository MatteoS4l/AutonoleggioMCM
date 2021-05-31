package it.univaq.disim.oop.MCMprogetto.business;

import java.util.List;
import it.univaq.disim.oop.MCMprogetto.domain.Tipologia;


public interface TipologiaService 
{
	
	void createTipologia(Tipologia tipologia) throws BusinessException;	
	
	void deleteTipologia(Tipologia tipologia) throws NumberFormatException, TipologiaNonTrovataException, BusinessException;
	
	List<String> getAllTipologia() throws TipologiaNonTrovataException;
	
	List<Tipologia> getAllTipologiaT() throws TipologiaNonTrovataException;
 
}
