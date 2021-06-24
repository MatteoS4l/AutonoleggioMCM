package it.univaq.disim.oop.MCMprogetto.business.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;


public class FileVeicoloService implements VeicoloService
{// id, targa, tipologia, modello, alimentazione, manutenzione, disponibilita, stato, chilometri, tarifKM, tarifperiodoalgiorno
	
	
	
	@Override	// CREA
	public void createVeicolo(Veicolo veicolo) throws BusinessException
	{
		if(!giaEsistente(veicolo.getTarga()))
		{
			try {
				FileData fileData = Utility.readAllRows("veicoli");
				try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "veicoli" + Utility.FORMATO)))
				{		
					int contatore = fileData.getCont();
					writer.println((contatore + 1));
					
					for (String[] righe : fileData.getRighe())
					{	writer.println(String.join(Utility.SEPARATORE, righe));		}
					
					StringBuilder row = new StringBuilder();
					row.append(fileData.getCont());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getTarga());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getTipologia());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getModello());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getAlimentazione());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.isManutenzione());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.isDisponibilita());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getStato());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getChilometri());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getTariffa_chilometraggio());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getTariffa_periodo());
					row.append(Utility.SEPARATORE);
					row.append(0);
					
					writer.println(row.toString());
				}
			}
			catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException(e);
			}
		}
		else
		{	throw new BusinessException();	}
	}

	
	
	@Override	// MODIFICA
	public void updateVeicolo(Veicolo veicolo) throws VeicoloNonTrovatoException, BusinessException
	{	try
		{
			FileData fileData = Utility.readAllRows("veicoli");
			try(PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "veicoli" + Utility.FORMATO)))
			{
				writer.println(fileData.getCont());
				for(String[] righe : fileData.getRighe())
				{
					if(Integer.parseInt(righe[0]) == veicolo.getID())
					{
						StringBuilder row = new StringBuilder();
						
						row.append(veicolo.getID());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getTarga());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getTipologia());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getModello());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getAlimentazione());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.isManutenzione());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.isDisponibilita());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getStato());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getChilometri());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getTariffa_chilometraggio());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getTariffa_periodo());
						row.append(Utility.SEPARATORE);
						row.append(0);
						
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
	public void deleteVeicolo(Veicolo veicolo) throws VeicoloNonTrovatoException, BusinessException
	{	try
		{
			FileData fileData = Utility.readAllRows("veicoli");
			try(PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "veicoli" + Utility.FORMATO)))
			{	
				writer.println(fileData.getCont());
				for(String[] righe : fileData.getRighe())
				{
					if(Integer.parseInt(righe[0]) == veicolo.getID() && veicolo.getStato().equalsIgnoreCase("disponibile"))
					{	return;	}
					else
					{	writer.println(String.join(Utility.SEPARATORE, righe));	}
				}
				throw new VeicoloNonTrovatoException();
			}
		}
		catch (IOException e) {	
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	
	
	
	
	
	
	
	
	@Override	// ALL VEICOLI
	public List<Veicolo> findAllVeicoli() throws BusinessException
	{	try
		{
			List<Veicolo> listacompleta = new ArrayList<Veicolo>();
			FileData filedata = Utility.readAllRows("veicoli");
			
			for (String[] colonne : filedata.getRighe())
			{
				Veicolo veicolo = new Veicolo();
				veicolo.setID(Integer.parseInt(colonne[0]));
				veicolo.setTarga(colonne[1]);
				veicolo.setTipologia(colonne[2]);
				veicolo.setModello(colonne[3]);
				veicolo.setAlimentazione(colonne[4]);
				veicolo.setManutenzione(Boolean.parseBoolean(colonne[5]));
				veicolo.setDisponibilita(Boolean.parseBoolean(colonne[6]));
				veicolo.setStato(colonne[7]);
				veicolo.setChilometri(Integer.parseInt(colonne[8]));
				veicolo.setTariffa_chilometraggio(Double.parseDouble(colonne[9]));
				veicolo.setTariffa_periodo(Double.parseDouble(colonne[10]));
				
				listacompleta.add(veicolo);
			}
			
			return listacompleta;
		}
		catch (IOException e) {	
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	
	

	@Override	// GET LISTA VEICOLI STATO
	public List<Veicolo> findListaVeicoliByStato(String stato) throws VeicoloNonTrovatoException, BusinessException
	{	
		if(stato.equalsIgnoreCase("tutte"))
		{	return findAllVeicoli();	}
		
		List<Veicolo> lista = findAllVeicoli();
		List<Veicolo> listacompleta = new ArrayList<Veicolo>();
		
		for(Veicolo tmp : lista)
		{
			if(tmp.getStato().equalsIgnoreCase(stato))
			{	listacompleta.add(tmp);	}
		}
		return listacompleta;
	}
		
		
	
	@Override	//	GET LISTA VEICOLI TIPOLOGIA
	public List<Veicolo> findListaVeicoliByTipologia(String tipologia) throws VeicoloNonTrovatoException, BusinessException
	{	
		if(tipologia.equalsIgnoreCase("tutte"))
		{	return findAllVeicoli();	}
		
		List<Veicolo> lista = findAllVeicoli();
		List<Veicolo> listacompleta = new ArrayList<Veicolo>();
		
		for(Veicolo tmp : lista)
		{
			if(tmp.getTipologia().equalsIgnoreCase(tipologia))
			{	listacompleta.add(tmp);	}
		}
		return listacompleta;
	}
		
	
	
	@Override   //GET LISTA BY ALIMENTAZIONE
	public List<Veicolo> findListaVeicoliByAlimentazione(String alimentazione) throws VeicoloNonTrovatoException, BusinessException
	{	
		if(alimentazione.equalsIgnoreCase("tutte"))
		{	return findAllVeicoli();	}
		
		List<Veicolo> lista = findAllVeicoli();
		List<Veicolo> listacompleta = new ArrayList<Veicolo>();
		
		for(Veicolo tmp : lista)
		{
			if(tmp.getAlimentazione().equalsIgnoreCase(alimentazione))
			{	listacompleta.add(tmp);	}
		}
		return listacompleta;
	}

	
	
	@Override	// GET LISTA BY MODELLO
	public List<Veicolo> findListaVeicoliByModello(String modello) throws VeicoloNonTrovatoException, BusinessException
	{	
		List<Veicolo> lista = findAllVeicoli();
		List<Veicolo> listacompleta = new ArrayList<Veicolo>();
		
		for(Veicolo tmp : lista)
		{
			if(tmp.getModello().toLowerCase().contains(modello.toLowerCase()))
			{	listacompleta.add(tmp);	  }
		}
		return listacompleta;
	}
	
	
	
	@Override	// GET LISTA BY TARIFFA_KM
	public List<Veicolo> findListaVeicoliByTariffaKM(double min, double max) throws VeicoloNonTrovatoException, BusinessException
	{	
		List<Veicolo> lista = findAllVeicoli();
		List<Veicolo> listacompleta = new ArrayList<Veicolo>();
		
		for(Veicolo tmp : lista)
		{
			if(tmp.getTariffa_chilometraggio() >= min && tmp.getTariffa_chilometraggio() <= max)
			{	listacompleta.add(tmp);	}
		}
		return listacompleta;
	}
		
		
		
	@Override	// GET LISTA BY TARIFFA_PERIODO
	public List<Veicolo> findListaVeicoliByTariffaPeriodo(double min, double max) throws VeicoloNonTrovatoException, BusinessException
	{	
		List<Veicolo> lista = findAllVeicoli();
		List<Veicolo> listacompleta = new ArrayList<Veicolo>();
	
		for(Veicolo tmp : lista)
		{
			if(tmp.getTariffa_periodo() >= min && tmp.getTariffa_periodo() <= max)
			{	listacompleta.add(tmp);	}
		}
		return listacompleta;
	}
	
	
	@Override	// GET VEICOLO BY ID
	public Veicolo findVeicoloById(int id) throws VeicoloNonTrovatoException , BusinessException
	{	
		List<Veicolo> lista = findAllVeicoli();
		
		for(Veicolo tmp : lista)
		{
			if(tmp.getID() == id)
			{	return tmp; 	}
		}
		throw new VeicoloNonTrovatoException();
	}

	
	
	
	
	
	@Override	// GET STATO VEICOLO
	public String[] findListStatoVeicolo()
	{	
		String[] s = {"disponibile","non disponibile","in noleggio"};
		return s;
	}
	
	
	@Override	// GET ALIMENTAZIONE VEICOLO
	public String[] findListAlimentazioneVeicolo()
	{	
		String[] s = {"benzina","diesel","gpl"};
		return s;
	}
	
	

	//	GIA ESISTENTE
	private Boolean giaEsistente(String targa) throws BusinessException
	{
		List<Veicolo> lista = findAllVeicoli();
		for(Veicolo tmp : lista)
		{
			if(targa.equalsIgnoreCase(tmp.getTarga()))
			{	return true;	}
		}
		return false;
	}



	@Override
	public void primaAggiunta() {
		// TODO Auto-generated method stub
		
	}

	

	
	


	


	
	
	
}
