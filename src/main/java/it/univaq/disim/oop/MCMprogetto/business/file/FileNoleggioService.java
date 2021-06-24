package it.univaq.disim.oop.MCMprogetto.business.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;


public class FileNoleggioService implements NoleggioService
{
	private UtenteService utenteservice= new FileUtenteService();
	private VeicoloService veicoloservice = new FileVeicoloService();
	private AssistenzaService assistenzaservice = new FileAssistenzaService();

	
	@Override	// CREA
	public void createNoleggio(Noleggio noleggio) throws BusinessException 
	{//id, prezzo, id assistenza, id cliente, id veicolo, data inizio, data fine, km fatti, servita(true false), risposta, feedback lasciato (true false)
		try
		{
			FileData fileData = Utility.readAllRows("noleggi");
			try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "noleggi" + Utility.FORMATO)))
			{
				int contatore = fileData.getCont();
				writer.println((contatore + 1));
				
				for (String[] righe : fileData.getRighe())
				{	writer.println(String.join(Utility.SEPARATORE, righe));		}
				
				StringBuilder row = new StringBuilder();
				row.append(fileData.getCont());
				row.append(Utility.SEPARATORE);
				row.append(noleggio.getPrezzo_noleggio());
				row.append(Utility.SEPARATORE);
				row.append(0);
				row.append(Utility.SEPARATORE);
				row.append(noleggio.getCliente().getID());
				row.append(Utility.SEPARATORE);
				row.append(noleggio.getVeicolo().getID());
				row.append(Utility.SEPARATORE);
				row.append(noleggio.getData_inizio_noleggio().toString().replace("T", " "));
				row.append(Utility.SEPARATORE);
				row.append(noleggio.getData_fine_noleggio().toString().replace("T", " "));
				row.append(Utility.SEPARATORE);
				row.append(0);
				row.append(Utility.SEPARATORE);
				row.append(noleggio.isServita());
				row.append(Utility.SEPARATORE);
				row.append("null");
				row.append(Utility.SEPARATORE);
				row.append(noleggio.isFeed_lasciato());
				
				writer.println(row.toString());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("noleggio non creato");
		}
	}


	@Override	// MODIFICA
	public void updateNoleggio(Noleggio noleggio) throws BusinessException
	{	try
		{
			FileData fileData = Utility.readAllRows("noleggi");
			try(PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "noleggi" + Utility.FORMATO)))
			{
				writer.println(fileData.getCont());
				for(String[] righe : fileData.getRighe())
				{
					if(Integer.parseInt(righe[0]) == noleggio.getID())
					{
						StringBuilder row = new StringBuilder();
						
						row.append(noleggio.getID());
						row.append(Utility.SEPARATORE);
						row.append(noleggio.getPrezzo_noleggio());
						row.append(Utility.SEPARATORE);
						if(noleggio.getAssistenza() == null)
						{	row.append(0);	}
						else
						{	row.append(noleggio.getAssistenza().getID());	}
						row.append(Utility.SEPARATORE);
						row.append(noleggio.getCliente().getID());
						row.append(Utility.SEPARATORE);
						row.append(noleggio.getVeicolo().getID());
						row.append(Utility.SEPARATORE);
						row.append(noleggio.getData_inizio_noleggio().toString().replace("T", " "));
						row.append(Utility.SEPARATORE);
						row.append(noleggio.getData_fine_noleggio().toString().replace("T", " "));
						row.append(Utility.SEPARATORE);
						row.append(noleggio.getKM_percorsi());
						row.append(Utility.SEPARATORE);
						row.append(noleggio.isServita());
						row.append(Utility.SEPARATORE);
						if (noleggio.getDettagli()!="null")
						{ row.append(noleggio.getDettagli()); }
						else
						{  row.append("null");  }
						row.append(Utility.SEPARATORE);
						row.append(noleggio.isFeed_lasciato());
						
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
	
	
	
	
	
	@Override	// ALL NOLEGGI
	public List<Noleggio> findAllNoleggi() throws BusinessException
	{	try
		{
			FileData filedata = Utility.readAllRows("noleggi");
			List<Noleggio> listanoleggi = new ArrayList<Noleggio>();
			
			for (String[] colonne : filedata.getRighe()) 
			{
				Noleggio noleggio = new Noleggio();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
				
				noleggio.setID(Integer.parseInt(colonne[0]));
				noleggio.setPrezzo_noleggio(Double.parseDouble(colonne[1]));
				noleggio.setAssistenza(assistenzaservice.getAssistenzaByID(Integer.parseInt(colonne[2])));
				noleggio.setCliente(utenteservice.findUtenteById(Integer.parseInt(colonne[3])));
				noleggio.setVeicolo(veicoloservice.findVeicoloById(Integer.parseInt(colonne[4])));
				noleggio.setData_inizio_noleggio(LocalDateTime.parse(colonne[5], formatter));
				noleggio.setData_fine_noleggio(LocalDateTime.parse(colonne[6], formatter));
				noleggio.setKM_percorsi(Integer.parseInt(colonne[7]));
				noleggio.setServita(Boolean.parseBoolean(colonne[8]));
				noleggio.setDettagli(colonne[9]);
				noleggio.setFeed_lasciato(Boolean.parseBoolean(colonne[10]));

				listanoleggi.add(noleggio);
			}
			return listanoleggi;
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	
	
	@Override	// TROVA NOLEGGIO BY ID
	public Noleggio findNoleggioByID(int ID) throws BusinessException
	{	
		List<Noleggio> lista = findAllNoleggi();
		
		for(Noleggio tmp : lista)
		{
			if(tmp.getID() == ID)
			{	return tmp; 	}
		}
		throw new NoleggioNonTrovatoException();
	}		
	
	
	


	@Override	// LISTA NOLEGGI BY ASSISTENZA
	public List<Noleggio> findNoleggiByAssistenza(String assistenza) throws BusinessException
	{	
		if(assistenza.equalsIgnoreCase("tutte"))
		{	return findAllNoleggi();	}
		
		List<Noleggio> lista = findAllNoleggi();
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();
		
		for(Noleggio tmp : lista)
		{
			if(assistenza.equalsIgnoreCase("si"))
			{
				if(tmp.getAssistenza() != null)
				{	listacompleta.add(tmp); 	}
			}
			
			if(assistenza.equalsIgnoreCase("no"))
			{
				if(tmp.getAssistenza() == null)
				{	listacompleta.add(tmp);  	}
			}
		}
		return listacompleta;
	}
		
		
		
	@Override	// LISTA NOLEGGI IN CORSO
	public List<Noleggio> findNoleggiInCorso() throws BusinessException
	{	
		List<Noleggio> lista = findAllNoleggi();
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();
		
		for(Noleggio tmp : lista)
		{
			if(tmp.getData_inizio_noleggio().isBefore(LocalDateTime.now())
			&& tmp.getData_fine_noleggio().isAfter(LocalDateTime.now()))
			{	listacompleta.add(tmp); 	}
		}
		return listacompleta;
	}



	@Override	// LISTA NOLEGGI UTENTE
	public List<Noleggio> findNoleggiByUtente(Utente utente) throws BusinessException
	{	
		List<Noleggio> lista = findAllNoleggi();
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();
		
		for(Noleggio tmp : lista)
		{
			try
			{
				if(utente.getID() == tmp.getCliente().getID())
				{	listacompleta.add(tmp);	}
			}
			catch(NullPointerException e)
			{	continue;	}
			
		}
		return listacompleta;
	}
	
	
	@Override	// LISTA NOLEGGI DATA FINE
	public List<Noleggio> findNoleggiByDataFine(LocalDate data_fine) throws BusinessException 
	{
		List<Noleggio> lista = findAllNoleggi();
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();
		
		for(Noleggio tmp : lista)
		{
			if(tmp.getData_fine_noleggio().isEqual(data_fine.atStartOfDay()))
			{	listacompleta.add(tmp);	}
		}
		return listacompleta;
	}
	
	
	
	@Override	// LISTA NOLEGGI NON PAGATI
	public List<Noleggio> findNoleggiByPagamento() throws BusinessException
	{
		List<Noleggio> lista = findAllNoleggi();
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();
		
		for(Noleggio tmp : lista)
		{
			if(tmp.getPrezzo_noleggio() == 0 && tmp.getData_fine_noleggio().isBefore(LocalDateTime.now()))			
			{	listacompleta.add(tmp);	}
		}
		return listacompleta;
	}
	
	
	@Override	// NOLEGGI BY DETTAGLI
	public List<Noleggio> findNoleggiByDettagli() throws BusinessException 
	{
		List<Noleggio> listanol=findAllNoleggi();
		List<Noleggio> listacompleta=new ArrayList<Noleggio>();
		
		for(Noleggio tmp: listanol)
		{
			if(tmp.getDettagli().equalsIgnoreCase("null"))
			{ listacompleta.add(tmp);   }
			
		}
		return listacompleta;
	}
	
	

	@Override	// CHECK STATO VEICOLI
	public void checkStatoVeicoli() throws BusinessException
	{
		List<Noleggio> listanoleggi = findAllNoleggi();
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
	public List<Veicolo> findVeicoliDisponibiliForDate(LocalDateTime datainizio, LocalDateTime datafine) throws BusinessException
	{
		List<Veicolo> listaveicoli = veicoloservice.findListaVeicoliByStato("tutte");
		List<Noleggio> listanoleggi = findAllNoleggi();
		List<Veicolo> veicolidaeliminare = new ArrayList<Veicolo>();
		
		for(Noleggio tmp : listanoleggi)
		{
			if(datainizio.isAfter(tmp.getData_inizio_noleggio()) && datainizio.isBefore(tmp.getData_fine_noleggio()))
			{	veicolidaeliminare.add(tmp.getVeicolo());	continue;	}
			
			if(datafine.isAfter(tmp.getData_inizio_noleggio()) && datafine.isBefore(tmp.getData_fine_noleggio()))
			{	veicolidaeliminare.add(tmp.getVeicolo());	continue;	}
			
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
	public Noleggio findNoleggioByIdAssistenza(int ID) throws BusinessException 
	{
		List<Noleggio> lista = findAllNoleggi();
		
		for(Noleggio tmp : lista)
		{
			if(tmp.getAssistenza().getID() == ID)
			{	return tmp; 	}
		}
		throw new NoleggioNonTrovatoException();
	}


	@Override	// DISPONIBILITA DATE
	public boolean disponibilitaDate(LocalDateTime inizio, LocalDateTime fine, Veicolo veicolo) throws BusinessException
	{
		List<Noleggio> lista = findAllNoleggi();
		
		for(Noleggio tmp : lista)
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
	
	
	
	
	
	
	
	
	
	
	
	
	


		
	

















