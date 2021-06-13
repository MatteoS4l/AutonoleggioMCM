package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.UtenteNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.domain.Amministratore;
import it.univaq.disim.oop.MCMprogetto.domain.Cliente;
import it.univaq.disim.oop.MCMprogetto.domain.Operatore;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;


public class RAMUtenteService implements UtenteService
{
	private static int cont=1;
	List<Utente> listautenti = new ArrayList<Utente>();	// vorse si deve mettere set
	private boolean ute = false; //da togliere
	

	@Override		// metodo invocato al momento di login
	public Utente Autenticazione(String username, String password) throws BusinessException
	{
		if(username.equalsIgnoreCase("admin") && password.equals("admin"))
		{
			Utente admin = new Amministratore();
			admin.setNome("Carmine");
			admin.setCognome("Pittella");
			admin.setUsername(username);
			admin.setPassword(password);
			admin.setEta(21);
			admin.setSesso('M');
			return admin;
		}
		
		for(Utente utente : listautenti)
		{
			if(username.equalsIgnoreCase(utente.getUsername()) && password.equals(utente.getPassword()))
			{	return utente;	}
		}
		
		throw new UtenteNonTrovatoException();
	}


	@Override	// GET LISTA UTENTI
	public List<Utente> FindAllUtenti(Amministratore admin) throws BusinessException
	{	return listautenti; 	}


	
	
	@Override	// CREA
	public void createUtente(Utente utente) throws BusinessException
	{	utente.setID(cont++);     listautenti.add(utente);	}


	@Override	// UPDATE
	public void updateUtente(Utente utente) throws BusinessException
	{	try
		{	for(Utente tmp : listautenti)
			{
				if(tmp.getID() == utente.getID())
				{
					listautenti.remove(tmp);
					listautenti.add(utente);
				}
			}
		}
		catch(ConcurrentModificationException e)
		{	/* errore checked */	}
		
	}
	
	
	@Override	// ELIMINA
	public void deleteUtente(Utente utente) throws UtenteNonTrovatoException
	{	listautenti.remove(utente);		}


	@Override	// CALCOLA ETA
	public int CalcolaEta(LocalDate data) throws BusinessException
	{
		int eta=0;
		try
		{	eta = LocalDate.now().getYear() - data.getYear();	}
		catch(NullPointerException e)
		{	System.out.println("Inserire data");	}
		
		if(data.getMonthValue() > LocalDate.now().getMonthValue())
		{	return --eta;	}
		
		if(data.getMonthValue() == LocalDate.now().getMonthValue() &&
		   data.getDayOfMonth() > LocalDate.now().getDayOfMonth())
		{	return --eta;	}
		
		return eta;
	}


	


	@Override	// GET UTENTE BY ID
	public Utente FindUtenteById(int id) throws UtenteNonTrovatoException, BusinessException
	{
		for (Utente tmp : listautenti)
		{	
			if (tmp.getID() == id)
			{	return tmp;	 }
		}
		throw new UtenteNonTrovatoException();
	}
	
	public void primaAggiunta ()
	{
		if (!ute) 
		{
			LocalDate data = LocalDate.now();
			Cliente cliente =  new Cliente(1,"a","a","a","a",'M',0,data);
			Operatore operatore = new Operatore(2,"o","o","o","o",'M',0,data);
			listautenti.add(cliente);
			listautenti.add(operatore);
			
			ute = true;
		}
	}
	
	
	
	
	
	
}
