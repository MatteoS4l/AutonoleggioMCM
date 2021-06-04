package it.univaq.disim.oop.MCMprogetto.business.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
	{//id, prezzo, id assistenza, id cliente, id veicolo, data inizio, data fine, km fatti, 
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
				row.append("null");
				
				writer.println(row.toString());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
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
						row.append("null");
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
	
	
	
	@Override	// TROVA NOLEGGIO BY ID
	public Noleggio FindNoleggioByID(int ID) throws NoleggioNonTrovatoException, BusinessException
	{	try
		{
			FileData filedata = Utility.readAllRows("noleggi");
			
			for (String[] colonne : filedata.getRighe()) 
			{
				if(ID == Integer.parseInt(colonne[0]))
				{
					Noleggio noleggio = new Noleggio();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
					
					noleggio.setID(Integer.parseInt(colonne[0]));
					noleggio.setPrezzo_noleggio(Double.parseDouble(colonne[1]));
					noleggio.setAssistenza(assistenzaservice.getAssistenzaByID(Integer.parseInt(colonne[2])));
					noleggio.setCliente(utenteservice.getUtenteById(Integer.parseInt(colonne[3])));
					noleggio.setVeicolo(veicoloservice.getVeicoloById(Integer.parseInt(colonne[4])));
					noleggio.setData_inizio_noleggio(LocalDateTime.parse(colonne[5], formatter));
					noleggio.setData_fine_noleggio(LocalDateTime.parse(colonne[6], formatter));
					noleggio.setKM_percorsi(Integer.parseInt(colonne[7]));
					noleggio.setServita(Boolean.parseBoolean(colonne[8]));
					noleggio.setDettagli(colonne[9]);
					
					return noleggio;
				}
			}
			throw new NoleggioNonTrovatoException();
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	
	
	@Override	// GET LISTA NOLEGGI
	public List<Noleggio> FindAllNoleggi() throws NoleggioNonTrovatoException, BusinessException
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
				noleggio.setCliente(utenteservice.getUtenteById(Integer.parseInt(colonne[3])));
				noleggio.setVeicolo(veicoloservice.getVeicoloById(Integer.parseInt(colonne[4])));
				noleggio.setData_inizio_noleggio(LocalDateTime.parse(colonne[5], formatter));
				noleggio.setData_fine_noleggio(LocalDateTime.parse(colonne[6], formatter));
				noleggio.setKM_percorsi(Integer.parseInt(colonne[7]));
				noleggio.setServita(Boolean.parseBoolean(colonne[8]));
				noleggio.setDettagli(colonne[9]);

				listanoleggi.add(noleggio);
			}
			return listanoleggi;
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}


	@Override	// LISTA NOLEGGI BY ASSISTENZA
	public List<Noleggio> FindNoleggiByAssistenza(String assistenza) throws NoleggioNonTrovatoException, BusinessException
	{	
		if(assistenza.equalsIgnoreCase("tutte"))
		{	return FindAllNoleggi();	}
		try
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
				noleggio.setCliente(utenteservice.getUtenteById(Integer.parseInt(colonne[3])));
				noleggio.setVeicolo(veicoloservice.getVeicoloById(Integer.parseInt(colonne[4])));
				noleggio.setData_inizio_noleggio(LocalDateTime.parse(colonne[5], formatter));
				noleggio.setData_fine_noleggio(LocalDateTime.parse(colonne[6], formatter));
				noleggio.setKM_percorsi(Integer.parseInt(colonne[7]));
				noleggio.setServita(Boolean.parseBoolean(colonne[8]));
				noleggio.setDettagli(colonne[9]);
				
				if(assistenza.equalsIgnoreCase("si"))
				{
					if(noleggio.getAssistenza() != null)
					{	listanoleggi.add(noleggio); 	}
				}
				
				if(assistenza.equalsIgnoreCase("no"))
				{
					if(noleggio.getAssistenza() == null)
					{	listanoleggi.add(noleggio); 	}
				}
			}
			return listanoleggi;
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}



	@Override	// LISTA NOLEGGI IN CORSO
	public List<Noleggio> FindNoleggiInCorso() throws NoleggioNonTrovatoException, BusinessException
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
				noleggio.setCliente(utenteservice.getUtenteById(Integer.parseInt(colonne[3])));
				noleggio.setVeicolo(veicoloservice.getVeicoloById(Integer.parseInt(colonne[4])));
				noleggio.setData_inizio_noleggio(LocalDateTime.parse(colonne[5], formatter));
				noleggio.setData_fine_noleggio(LocalDateTime.parse(colonne[6], formatter));
				noleggio.setKM_percorsi(Integer.parseInt(colonne[7]));
				noleggio.setServita(Boolean.parseBoolean(colonne[8]));
				noleggio.setDettagli(colonne[9]);
				
				if(noleggio.getData_inizio_noleggio().isBefore(LocalDateTime.now())
				&& noleggio.getData_fine_noleggio().isAfter(LocalDateTime.now()))
				{	listanoleggi.add(noleggio); 	}
			}
			return listanoleggi;
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}



	@Override	// LISTA NOLEGGI UTENTE
	public List<Noleggio> FindNoleggiByUtente(Utente utente) throws NoleggioNonTrovatoException, BusinessException
	{	try
		{
			FileData filedata = Utility.readAllRows("noleggi");
			List<Noleggio> listanoleggi = new ArrayList<Noleggio>();
			
			for (String[] colonne : filedata.getRighe()) 
			{
				if(utente.getID() == Integer.parseInt(colonne[3]))
				{
					Noleggio noleggio = new Noleggio();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
					
					noleggio.setID(Integer.parseInt(colonne[0]));
					noleggio.setPrezzo_noleggio(Double.parseDouble(colonne[1]));
					noleggio.setAssistenza(assistenzaservice.getAssistenzaByID(Integer.parseInt(colonne[2])));
					noleggio.setCliente(utenteservice.getUtenteById(Integer.parseInt(colonne[3])));
					noleggio.setVeicolo(veicoloservice.getVeicoloById(Integer.parseInt(colonne[4])));
					noleggio.setData_inizio_noleggio(LocalDateTime.parse(colonne[5], formatter));
					noleggio.setData_fine_noleggio(LocalDateTime.parse(colonne[6], formatter));
					noleggio.setKM_percorsi(Integer.parseInt(colonne[7]));
					noleggio.setServita(Boolean.parseBoolean(colonne[8]));
					noleggio.setDettagli(colonne[9]);
					
					listanoleggi.add(noleggio);
				}
			}
			return listanoleggi;
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	
	
	@Override	// LISTA NOLEGGI DATA FINE
	public List<Noleggio> FindNoleggiByDataFine(LocalDate data_fine) throws BusinessException 
	{
		try
		{
			FileData filedata = Utility.readAllRows("noleggi");
			List<Noleggio> listanoleggi = new ArrayList<Noleggio>();
			
			for (String[] colonne : filedata.getRighe()) 
			{
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				
				if (data_fine.isEqual(LocalDate.parse(colonne[6], formatter)))
				{
					Noleggio noleggio = new Noleggio();
					
					noleggio.setID(Integer.parseInt(colonne[0]));
					noleggio.setPrezzo_noleggio(Double.parseDouble(colonne[1]));
					noleggio.setAssistenza(assistenzaservice.getAssistenzaByID(Integer.parseInt(colonne[2])));
					noleggio.setCliente(utenteservice.getUtenteById(Integer.parseInt(colonne[3])));
					noleggio.setVeicolo(veicoloservice.getVeicoloById(Integer.parseInt(colonne[4])));
					noleggio.setData_inizio_noleggio(LocalDateTime.parse(colonne[5], formatter));
					noleggio.setData_fine_noleggio(LocalDateTime.parse(colonne[6], formatter));
					noleggio.setKM_percorsi(Integer.parseInt(colonne[7]));
					noleggio.setServita(Boolean.parseBoolean(colonne[8]));
					noleggio.setDettagli(colonne[9]);
					listanoleggi.add(noleggio);
				}
			}
			return listanoleggi;
		}
		catch(IOException e)
		{	throw new NoleggioNonTrovatoException();	}
					
	}
	
	
	
	
	

	@Override	// CHECK STATO VEICOLI
	public void CheckStatoVeicoli() throws BusinessException
	{
		List<Noleggio> listanoleggi = FindAllNoleggi();
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
		List<Veicolo> listaveicoli = veicoloservice.GetListaVeicoliByStato("disponibile");
		List<Noleggio> listanoleggi = FindAllNoleggi();
		List<Veicolo> veicolidaeliminare = new ArrayList<Veicolo>();
		
		for(Noleggio tmp : listanoleggi)
		{
			if(datainizio.isAfter(tmp.getData_inizio_noleggio()) && datainizio.isBefore(tmp.getData_fine_noleggio()))
			{	veicolidaeliminare.add(tmp.getVeicolo());	}
			
			
		}
		
		
		
		
		return null;
	}
	
	
	

	
					
				
			
	



	/*@Override	// LISTA NOLEGGI DATE							id veicolo non ci vuole
	public List<Veicolo> FindNoleggiByData(LocalDateTime datainizio, LocalDateTime datafine) throws BusinessException
	{	try
		{
			FileData filedata = Utility.readAllRows("noleggi");
			List<Veicolo> listaveicolo = new ArrayList<Veicolo>();
			
			for (String[] colonne : filedata.getRighe()) 
			{
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
				if( !(datainizio.isAfter(LocalDateTime.parse(colonne[5], formatter)) && datainizio.isBefore(LocalDateTime.parse(colonne[6], formatter)))
				&&  !(datafine.isAfter(LocalDateTime.parse(colonne[5], formatter)) && datafine.isBefore(LocalDateTime.parse(colonne[6], formatter))))
				{
					Noleggio noleggio = new Noleggio();
					
					noleggio.setID(Integer.parseInt(colonne[0]));
					noleggio.setPrezzo_noleggio(Double.parseDouble(colonne[1]));
					noleggio.setAssistenza(assistenzaservice.getAssistenzaByID(Integer.parseInt(colonne[2])));
					noleggio.setCliente(utenteservice.getUtenteById(Integer.parseInt(colonne[3])));
					noleggio.setVeicolo(veicoloservice.getVeicoloById(Integer.parseInt(colonne[4])));
					noleggio.setData_inizio_noleggio(LocalDateTime.parse(colonne[5], formatter));
					noleggio.setData_fine_noleggio(LocalDateTime.parse(colonne[6], formatter));
					noleggio.setKM_percorsi(Integer.parseInt(colonne[7]));
					noleggio.setServita(Boolean.parseBoolean(colonne[8]));
					noleggio.setDettagli(colonne[9]);
					listaveicolo.add(noleggio.getVeicolo());
				}
			}
			return listaveicolo;
		}
		catch(IOException e)
		{	throw new NoleggioNonTrovatoException();	}
	}*/
	
	
}	
	
	
	
	
	
	
	
	
	
	
	
	
	


		
	

















