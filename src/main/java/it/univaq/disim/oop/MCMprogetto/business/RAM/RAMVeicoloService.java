package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.util.*;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;


public class RAMVeicoloService implements VeicoloService
{
	List<Veicolo> listaveicoli = new ArrayList<Veicolo>();


	@Override	// GET LISTA VEICOLI
	public List<Veicolo> GetListaVeicoliByStato(String stato) throws BusinessException
	{	
		List<Veicolo> listafiltrata = new ArrayList<Veicolo>();
		for(Veicolo tmp : listaveicoli)
		{
			if(tmp.getStato().equalsIgnoreCase(stato) || stato.equalsIgnoreCase("tutte"))
			{	listafiltrata.add(tmp); 	}
		}
		
		return listafiltrata;
	}
	
	
	@Override	//	GET LISTA VEICOLI TIPOLOGIA
	public List<Veicolo> GetListaVeicoliByTipologia(String tipologia) throws BusinessException
	{
		List<Veicolo> listafiltrata = new ArrayList<Veicolo>();
		for(Veicolo tmp : listaveicoli)
		{
			if(tmp.getTipologia().equalsIgnoreCase(tipologia) || tipologia.equalsIgnoreCase("tutte"))
			{	listafiltrata.add(tmp); 	}
		}
		return listafiltrata;
	}
	
	@Override //  GET LISTA VEICOLI ALIMENTAZIONE
	public List<Veicolo> GetListaVeicoliByAlimentazione(String alimentazione) throws BusinessException 
	{
		List<Veicolo> listafiltrata = new ArrayList<Veicolo>();
		for(Veicolo tmp : listaveicoli)
		{
			if(tmp.getAlimentazione().equalsIgnoreCase(alimentazione) || alimentazione.equalsIgnoreCase("tutte"))
			{	listafiltrata.add(tmp); 	}
		}
		return listafiltrata;	
	}
	
	
	@Override	// GET LISTA BY TARIFFA_KM
	public List<Veicolo> GetListaVeicoliByTariffaKM(double min, double max) throws BusinessException
	{
		List<Veicolo> listafiltrata = new ArrayList<Veicolo>();
		for(Veicolo tmp : listaveicoli)
		{
			if(tmp.getTariffa_chilometraggio() >= min && tmp.getTariffa_chilometraggio() <= max)
			{	listafiltrata.add(tmp); 	}
		}
		return listafiltrata;
	}
	
	
	@Override	// GET LISTA BY TARIFFA_PERIODO
	public List<Veicolo> GetListaVeicoliByTariffaPeriodo(double min, double max) throws BusinessException
	{
		List<Veicolo> listafiltrata = new ArrayList<Veicolo>();
		for(Veicolo tmp : listaveicoli)
		{
			if(tmp.getTariffa_periodo() >= min && tmp.getTariffa_periodo() <= max)
			{	listafiltrata.add(tmp); 	}
		}
		return listafiltrata;
	}	
	
	
	@Override	// GET LISTA BY MODELLO
	public List<Veicolo> GetListaVeicoliByModello(String modello) throws BusinessException 
	{
		List<Veicolo> listafiltrata = new ArrayList<Veicolo>();
		for(Veicolo tmp : listaveicoli)
		{
			if(tmp.getModello().equalsIgnoreCase(modello))
			{	listafiltrata.add(tmp); 	}
		}
		return listafiltrata;
	}
	
	
	@Override	// CREA
	public void createVeicolo(Veicolo veicolo) throws BusinessException
	{	listaveicoli.add(veicolo);  	}
	
	
	
	@Override	// MODIFICA
	public void updateVeicolo(Veicolo veicolo) throws VeicoloNonTrovatoException
	{
		for(Veicolo v : listaveicoli)
		{
			if(v.getTarga().equalsIgnoreCase(veicolo.getTarga()))
			{
				v.setTipologia(veicolo.getTipologia());	
				v.setModello(veicolo.getModello());
				v.setAlimentazione(veicolo.getAlimentazione());
				v.setTarga(veicolo.getTarga());
				v.setManutenzione(veicolo.isManutenzione());
				v.setDisponibilita(veicolo.isDisponibilita());
				v.setChilometri(veicolo.getChilometri());
				v.setTariffa_chilometraggio(veicolo.getTariffa_chilometraggio());
				v.setTariffa_periodo(veicolo.getTariffa_periodo());
			}
		}
	}
	

	@Override	// ELIMINA
	public void deleteVeicolo(Veicolo veicolo) throws VeicoloNonTrovatoException
	{	
		if(veicolo.getStato().equalsIgnoreCase("disponibile"))
		{	listaveicoli.remove(veicolo);	}
		else
		{	throw new VeicoloNonTrovatoException();		}
	}

	
	@Override	// GET STATO VEICOLO
	public String[] GetListStatoVeicolo()
	{	
		String[] s = {"disponibile","non disponibile","in noleggio"};
		return s;
	}
	
	
	@Override	// GET ALIMENTAZIONE VEICOLO
	public String[] GetListAlimentazioneVeicolo()
	{	
		String[] s = {"benzina","diesel","gpl"};
		return s;
	}


	@Override	// GET VEICOLO
	public Veicolo getVeicoloById(int id) throws VeicoloNonTrovatoException 
	{
		for (Veicolo v: listaveicoli)
		{
			if (v.getID() == id)
			{ return v;	}	
		}
		return null;
	}


	


	


	


	
	
	
	
}
