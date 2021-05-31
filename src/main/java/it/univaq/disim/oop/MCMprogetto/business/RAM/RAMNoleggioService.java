package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.util.ArrayList;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;

public class RAMNoleggioService implements NoleggioService
{
	int cont;
	List<Noleggio> listanoleggi = new ArrayList<Noleggio>();

	@Override	// CREA 
	public void createNoleggio(Noleggio noleggio) throws BusinessException 
	{	listanoleggi.add(noleggio);	}
		
	

	@Override	// MODIFICA
	public void updateNoleggio(Noleggio noleggio) throws NoleggioNonTrovatoException 
	{
		for (Noleggio tmp : listanoleggi)
		{
			if (tmp.getID()==noleggio.getID())
			{	tmp = noleggio;		}
		}
	}

	@Override	// ELIMINA
	public void deleteNoleggio(Noleggio noleggio) throws NoleggioNonTrovatoException 
	{	listanoleggi.remove(noleggio);	}

	
	
	@Override	// TROVA NOLEGGIO
	public Noleggio FindNoleggio(int ID) throws NoleggioNonTrovatoException
	{
		for(Noleggio tmp : listanoleggi)
		{
			if(tmp.getID() == ID)
			{	return tmp; 	}
		}
		throw new NoleggioNonTrovatoException();
	}

	@Override	// GET LISTA NOLEGGI
	public List<Noleggio> FindAllNoleggi() throws BusinessException
	{	return listanoleggi;	}
		
	
		
		
}
