package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;

public class RAMNoleggioService implements NoleggioService
{
	int cont;
	List<Noleggio> listanoleggi = new ArrayList<Noleggio>();

	@Override	// CREA 
	public void createNoleggio(Noleggio noleggio) throws BusinessException 
	{	listanoleggi.add(noleggio);	}
	
	
	@Override	// TROVA NOLEGGIO
	public Noleggio FindNoleggioByID(int ID) throws NoleggioNonTrovatoException, NumberFormatException, BusinessException
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
		
	
	
	
	@Override	// LISTA NOLEGGI BY ASSISTENZA
	public List<Noleggio> FindNoleggiByAssistenza(String assistenza) throws BusinessException
	{	
		List<Noleggio> lista = new ArrayList<Noleggio>();
		for(Noleggio tmp : listanoleggi)
		{
			if(tmp.getAssistenza() != null)
			{	lista.add(tmp);	  }
		}
		return lista;
	}
		
		
	@Override	// LISTA NOLEGGI IN CORSO
	public List<Noleggio> FindNoleggiInCorso() throws BusinessException
	{
		List<Noleggio> lista = new ArrayList<Noleggio>();
		for(Noleggio tmp : listanoleggi)
		{
			if(tmp.getData_inizio_noleggio().isBefore(LocalDateTime.now())
			&& tmp.getData_fine_noleggio().isAfter(LocalDateTime.now()))
			{	lista.add(tmp); 	}
		}
		return lista;
	}


	@Override	// LISTA NOLEGGI UTENTE
	public List<Noleggio> FindNoleggiByUtente(Utente utente) throws BusinessException
	{
		List<Noleggio> lista = new ArrayList<Noleggio>();
		for(Noleggio tmp : listanoleggi)
		{
			if(tmp.getCliente().getID() == utente.getID())
			{	lista.add(tmp); 	}
		}
		return lista;
		
	}


	/*@Override	// LISTA NOLEGGI DATE
	public List<Veicolo> FindNoleggiByData(LocalDateTime datainizio, LocalDateTime datafine) throws BusinessException
	{
		return null;
	}*/
	
	
	
	
	
	
}
