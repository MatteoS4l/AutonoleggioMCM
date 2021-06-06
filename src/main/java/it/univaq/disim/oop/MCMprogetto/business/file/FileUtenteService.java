package it.univaq.disim.oop.MCMprogetto.business.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.UtenteNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.domain.Amministratore;
import it.univaq.disim.oop.MCMprogetto.domain.Cliente;
import it.univaq.disim.oop.MCMprogetto.domain.Operatore;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;

public class FileUtenteService implements UtenteService
{		
	

	@Override	// AUTENTICAZIONE
	public Utente Autenticazione(String username, String password) throws UtenteNonTrovatoException, BusinessException
	{	
		if(username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin"))
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
		try
		{	FileData filedata = Utility.readAllRows("utenti");
			
			for (String[] colonne : filedata.getRighe())
			{
				if(colonne[4].equalsIgnoreCase(username) && colonne[5].equals(password))
				{
					Utente utente = null;
					switch(colonne[1])
					{
						case "cliente":
						{	utente = new Cliente();	  } break;
						
						case "operatore":
						{	utente = new Operatore();	} break;

						default: break;
					}
					
					utente.setID(Integer.parseInt(colonne[0]));
					utente.setNome(colonne[2]);
					utente.setCognome(colonne[3]);
					utente.setUsername(colonne[4]);
					utente.setPassword(colonne[5]);
					utente.setSesso(colonne[6].charAt(0));
					utente.setEta(Integer.parseInt(colonne[7]));
					
					return utente;
				}
			}
			throw new UtenteNonTrovatoException();
		}
		
		catch (IOException e) {	
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	
	
	
	@Override	// GET LISTA UTENTI
	public List<Utente> GetListaAllUtenti(Amministratore admin) throws BusinessException
	{	try
		{
			FileData filedata = Utility.readAllRows("utenti");		// elimina anche gli spazi
			List<Utente> listacompleta = new ArrayList<Utente>();
			
			for (String[] colonne : filedata.getRighe())
			{
				Utente utente = null;
				switch(colonne[1])
				{
					case "cliente":
					{	utente = new Cliente();	  } break;
					
					case "operatore":
					{	utente = new Operatore();	} break;
					
					default: break;
				}
				
				utente.setID(Integer.parseInt(colonne[0]));
				utente.setNome(colonne[2]);
				utente.setCognome(colonne[3]);
				utente.setUsername(colonne[4]);
				utente.setPassword(colonne[5]);
				utente.setSesso(colonne[6].charAt(0));
				utente.setEta(Integer.parseInt(colonne[7]));
				utente.setData_nascita(LocalDate.parse(colonne[8]));
				
				listacompleta.add(utente);
			}
			
			if(! listacompleta.isEmpty())
			{	return listacompleta;	}
			
			throw new UtenteNonTrovatoException();
		}
		
		catch (IOException e) {	
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	
	
	@Override	// CREA
	public void createUtente(Utente utente) throws BusinessException	// sarebbe da modificare e inserire in file di testo anche data di nascita
	{
		try {
			FileData fileData = Utility.readAllRows("utenti");
			try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "utenti" + Utility.FORMATO)))
			{
				int contatore = fileData.getCont();
				writer.println((contatore + 1));
				
				for (String[] righe : fileData.getRighe())
				{	writer.println(String.join(Utility.SEPARATORE, righe));		}
				
				StringBuilder row = new StringBuilder();
				row.append(fileData.getCont());
				row.append(Utility.SEPARATORE);
				
				if(utente instanceof Cliente)
				{	row.append("cliente");   row.append(Utility.SEPARATORE);	}
				if(utente instanceof Operatore)
				{	row.append("operatore");   row.append(Utility.SEPARATORE);	}
				
				row.append(utente.getNome());
				row.append(Utility.SEPARATORE);
				row.append(utente.getCognome());
				row.append(Utility.SEPARATORE);
				row.append(utente.getUsername());
				row.append(Utility.SEPARATORE);
				row.append(utente.getPassword());
				row.append(Utility.SEPARATORE);
				row.append(utente.getSesso());
				row.append(Utility.SEPARATORE);
				row.append(utente.getEta());
				row.append(Utility.SEPARATORE);
				row.append(utente.getData_nascita().toString().replace("T", " "));
				writer.println(row.toString());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}



	@Override	// MODIFICA
	public void updateUtente(Utente utente) throws UtenteNonTrovatoException, BusinessException
	{	try
		{
			FileData fileData = Utility.readAllRows("utenti");
			try(PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "utenti" + Utility.FORMATO)))
			{
				writer.println(fileData.getCont());
				for(String[] righe : fileData.getRighe())
				{
					if(Integer.parseInt(righe[0]) == utente.getID())
					{
						StringBuilder row = new StringBuilder();
						
						row.append(utente.getID());
						row.append(Utility.SEPARATORE);
						
						if(utente instanceof Cliente)
						{	row.append("cliente");   row.append(Utility.SEPARATORE);	}
						if(utente instanceof Operatore)
						{	row.append("operatore");   row.append(Utility.SEPARATORE);	}
						if(utente instanceof Amministratore)
						{	row.append("amministratore");   row.append(Utility.SEPARATORE);	}
						
						row.append(utente.getNome());
						row.append(Utility.SEPARATORE);
						row.append(utente.getCognome());
						row.append(Utility.SEPARATORE);
						row.append(utente.getUsername());
						row.append(Utility.SEPARATORE);
						row.append(utente.getPassword());
						row.append(Utility.SEPARATORE);
						row.append(utente.getSesso());
						row.append(Utility.SEPARATORE);
						row.append(utente.getEta());
						row.append(Utility.SEPARATORE);
						row.append(utente.getData_nascita().toString().replace("T", " "));
						writer.println(row.toString());
					}
					else
					{	writer.println(String.join(Utility.SEPARATORE, righe));	}
				}
			}
		}
		catch (IOException e) {	
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	
	
	
	@Override	// ELIMINA
	public void deleteUtente (Utente utente) throws UtenteNonTrovatoException, BusinessException
	{	try
		{
			FileData fileData = Utility.readAllRows("utenti");
			try(PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "utenti" + Utility.FORMATO)))
			{	
				writer.println(fileData.getCont());
				for(String[] righe : fileData.getRighe())
				{
					if(Integer.parseInt(righe[0]) == utente.getID())
					{	System.out.println("eliminato");	}
					else
					{	writer.println(String.join(Utility.SEPARATORE, righe));	}
				}
			}
		}
		catch (IOException e) {	
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}



	@Override	// CALCOLA ETA
	public int CalcolaEta(LocalDate data) throws BusinessException, NullPointerException
	{
		int eta=0;
		try
		{	eta = LocalDate.now().getYear() - data.getYear();	}
		catch(NullPointerException e)
		{	throw new NullPointerException();	}
		
		if(data.getMonthValue() > LocalDate.now().getMonthValue())
		{	return --eta;	}
		
		if(data.getMonthValue() == LocalDate.now().getMonthValue() &&
		   data.getDayOfMonth() > LocalDate.now().getDayOfMonth())
		{	return --eta;	}
		
		return eta;
	}



	@Override	// GET UTENTE
	public Utente getUtenteById(int id) throws UtenteNonTrovatoException, BusinessException
	{	try
		{
			FileData fileData = Utility.readAllRows("utenti");	
			for(String[] righe : fileData.getRighe())
			{
				if(Integer.parseInt(righe[0]) == id)
				{	Utente utente = new Utente();
				
					utente.setID(Integer.parseInt(righe[0]));
					utente.setNome(righe[2]);
					utente.setCognome(righe[3]);
					utente.setUsername(righe[4]);
					utente.setPassword(righe[5]);
					utente.setSesso(righe[6].charAt(0));
					utente.setEta(Integer.parseInt(righe[7]));
					utente.setData_nascita(LocalDate.parse(righe[8]));
				
					return utente;
				}
			}
			return null;
		}
		catch (IOException e) {	
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
