package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import it.univaq.disim.oop.MCMprogetto.business.AssistenzaNonTrovataException;
import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;

public class RAMAssistenzaService implements AssistenzaService
{
	List<Assistenza> listassistenza = new ArrayList<Assistenza>();
	int cont_assistenza = 1;

	
	@Override	// CREA
	public int createAssistenza(Assistenza assistenza) throws BusinessException
	{	
		assistenza.setID(cont_assistenza++);
		listassistenza.add(assistenza);
		return listassistenza.size()-1;
	}
	
	@Override	// MODIFICA
	public void updateAssistenza(Assistenza assistenza) throws BusinessException
	{	try
		{	for(Assistenza tmp : listassistenza)
			{
				if(tmp.getID() == assistenza.getID())
				{	
					listassistenza.remove(tmp);
					listassistenza.add(assistenza);
				}
			}
		}catch(ConcurrentModificationException e)
		{	/* errore checked */	}
		
	}
	
	
	
	@Override	// FIND ALL ASSISTENZA
	public List<Assistenza> FindAllAssistenza() throws BusinessException
	{	return listassistenza;	}

	
	
	
	@Override	// GET ASSISTENZA ID
	public Assistenza getAssistenzaByID(int id) throws AssistenzaNonTrovataException
	{
		for(Assistenza tmp : listassistenza)
		{
			if(tmp.getID() == id)
			{	return tmp;		}
		}
		throw new AssistenzaNonTrovataException();
	}





	@Override	// GET ASSISTENZA SERVITA
	public List<Assistenza> getListaAssistenzaByServita(boolean servita) throws BusinessException
	{
		return null;
	}
	

}
