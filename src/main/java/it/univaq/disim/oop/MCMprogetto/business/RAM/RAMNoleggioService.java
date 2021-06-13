package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public class RAMNoleggioService implements NoleggioService
{
	List<Noleggio> listanoleggi = new ArrayList<Noleggio>();
	int cont_noleggi;
	private VeicoloService veicoloservice = new RAMVeicoloService();
	
	
	
	
	@Override	// CREA 
	public void createNoleggio(Noleggio noleggio) throws BusinessException 
	{	
		noleggio.setID(cont_noleggi++);
		listanoleggi.add(noleggio);
	}
	
	
	
	@Override	// MODIFICA
	public void updateNoleggio(Noleggio noleggio) throws BusinessException
	{	try
		{	for(Noleggio tmp : listanoleggi)
			{
				if(tmp.getID() == noleggio.getID())
				{	
					listanoleggi.remove(tmp);
					listanoleggi.add(noleggio);
				}
			}
		}catch(ConcurrentModificationException e)
		{	/* errore checked */	}
		
	}
	
	
	@Override	// ALL NOLEGGI
	public List<Noleggio> FindAllNoleggi() throws BusinessException
	{	return listanoleggi;	}
	
	
	
	@Override	// TROVA NOLEGGIO BY ID
	public Noleggio FindNoleggioByID(int ID) throws BusinessException
	{
		for(Noleggio tmp : listanoleggi)
		{
			if(tmp.getID() == ID)
			{	return tmp; 	}
		}
		throw new NoleggioNonTrovatoException();
	}
	
	
	
	@Override	// LISTA NOLEGGI BY ASSISTENZA
	public List<Noleggio> FindNoleggiByAssistenza(String assistenza) throws BusinessException
	{
		List<Noleggio> lista = new ArrayList<Noleggio>();
		for(Noleggio tmp : listanoleggi)
		{
			if(assistenza.equalsIgnoreCase("tutte"))
			{	return listanoleggi;	}
			
			if(assistenza.equalsIgnoreCase("si"))
			{
				if(tmp.getAssistenza() != null)
				{	lista.add(tmp);	  }
			}
			if(assistenza.equalsIgnoreCase("no"))
			{
				if(tmp.getAssistenza() == null)
				{	lista.add(tmp);	  }
			}
			
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


	@Override	// LISTA NOLEGGI DATA FINE
	public List<Noleggio> FindNoleggiByDataFine(LocalDate data_fine) throws BusinessException 
	{
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();
		
		for(Noleggio tmp : listanoleggi)
		{
			if(tmp.getData_fine_noleggio().isEqual(data_fine.atStartOfDay()))
			{	listacompleta.add(tmp);	}
		}
		return listacompleta;
	}
	
	
	@Override	// LISTA NOLEGGI NON PAGATI
	public List<Noleggio> FindNoleggiByPagamento() throws BusinessException
	{
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();
		
		for(Noleggio tmp : listanoleggi)
		{
			if(tmp.getPrezzo_noleggio() == 0 && tmp.getData_fine_noleggio().isBefore(LocalDateTime.now()))			
			{	listacompleta.add(tmp);	}
		}
		return listacompleta;
	}

	
	
	@Override	// NOLEGGI BY DETTAGLI
	public List<Noleggio> FindNoleggiByDettagli() throws BusinessException 
	{
		List<Noleggio> listacompleta=new ArrayList<Noleggio>();
		
		for(Noleggio tmp: listanoleggi)
		{
			if(tmp.getDettagli() == null)
			{ listacompleta.add(tmp);   }
			
		}
		return listacompleta;
	}
	
	
	@Override	// CHECK STATO VEICOLI
	public void CheckStatoVeicoli() throws BusinessException
	{
		for(Noleggio tmp : listanoleggi)
		{
			Veicolo veicolo = new Veicolo();
			
			veicolo.setID(tmp.getVeicolo().getID());
			veicolo.setTarga(tmp.getVeicolo().getTarga());
			veicolo.setTipologia(tmp.getVeicolo().getTipologia());
			veicolo.setModello(tmp.getVeicolo().getModello());
			veicolo.setAlimentazione(tmp.getVeicolo().getAlimentazione());
			veicolo.setChilometri(tmp.getVeicolo().getChilometri());
			veicolo.setTariffa_chilometraggio(tmp.getVeicolo().getTariffa_chilometraggio());
			veicolo.setTariffa_periodo(tmp.getVeicolo().getTariffa_periodo());
			
			if(tmp.getData_inizio_noleggio().isBefore(LocalDateTime.now())
			&& tmp.getData_fine_noleggio().isAfter(LocalDateTime.now()))
			{	
				veicolo.setManutenzione(false);
				veicolo.setDisponibilita(false);
				veicolo.setStato("in noleggio");
			}
			else
			{
				veicolo.setManutenzione(false);
				veicolo.setDisponibilita(true);
				veicolo.setStato("disponibile");
			}
			veicoloservice.updateVeicolo(veicolo);
		}
	}
	
	
	
	@Override	// VEICOLI DISPONIBILI PER DATA
	public List<Veicolo> FindVeicoliDisponibiliForDate(LocalDateTime datainizio, LocalDateTime datafine) throws BusinessException
	{
		List<Veicolo> listaveicoli = veicoloservice.FindListaVeicoliByStato("tutte");
		List<Veicolo> veicolidaeliminare = new ArrayList<Veicolo>();
		
		for(Noleggio tmp : listanoleggi)
		{
			if(datainizio.isAfter(tmp.getData_inizio_noleggio()) && datainizio.isBefore(tmp.getData_fine_noleggio()))
			{	veicolidaeliminare.add(tmp.getVeicolo());	}
			
			if(datafine.isAfter(tmp.getData_inizio_noleggio()) && datafine.isBefore(tmp.getData_fine_noleggio()))
			{	veicolidaeliminare.add(tmp.getVeicolo());	}
			
			if(tmp.getData_inizio_noleggio().isAfter(datainizio) && tmp.getData_inizio_noleggio().isBefore(datafine))
			{	veicolidaeliminare.add(tmp.getVeicolo());	continue;	}
			
			if(tmp.getData_fine_noleggio().isAfter(datainizio) && tmp.getData_fine_noleggio().isBefore(datafine))
			{	veicolidaeliminare.add(tmp.getVeicolo());	continue;	}
		}
		
		for(int i=0; i<listaveicoli.size(); i++)
		{	
			for(int j=0; j<veicolidaeliminare.size(); j++)
			{
				if(veicolidaeliminare.get(j).getID() == listaveicoli.get(i).getID())
				{	listaveicoli.remove(i);		}
			}
		}
		return listaveicoli;
	}
	
	
	
	@Override	// FIND NOLEGGIO BY ASSISTENZA ID
	public Noleggio FindNoleggioByIdAssistenza(int ID) throws BusinessException 
	{
		for(Noleggio tmp : listanoleggi)
		{
			if(tmp.getAssistenza().getID() == ID)
			{	return tmp; 	}
		}
		throw new NoleggioNonTrovatoException();
	}
	
	
	@Override	// DISPONIBILITA DATE
	public boolean DisponibilitaDate(LocalDateTime inizio, LocalDateTime fine, Veicolo veicolo) throws BusinessException
	{
		for(Noleggio tmp : listanoleggi)
		{
			if(tmp.getVeicolo().getID() == veicolo.getID())
			{
				if(inizio.isAfter(tmp.getData_inizio_noleggio()) && inizio.isBefore(tmp.getData_fine_noleggio()))
				{	return false;	}
				
				if(fine.isAfter(tmp.getData_inizio_noleggio()) && fine.isBefore(tmp.getData_fine_noleggio()))
				{	return false;	}
				
				if(tmp.getData_inizio_noleggio().isAfter(inizio) && tmp.getData_inizio_noleggio().isBefore(fine))
				{	return false;	}
				
				if(tmp.getData_fine_noleggio().isAfter(inizio) && tmp.getData_fine_noleggio().isBefore(inizio))
				{	return false;	}
			}
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
