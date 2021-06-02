package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.MCMprogetto.business.AssistenzaNonTrovataException;
import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;

public class RAMAssistenzaService implements AssistenzaService
{
	List<Assistenza> listassistenza = new ArrayList<Assistenza>();

	
	@Override	// CREA
	public void createAssistenza(String dettagli) throws BusinessException
	{	
		Assistenza a = new Assistenza();
		a.setDettagli(dettagli);
		listassistenza.add(a);
	}

	
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
	

}
