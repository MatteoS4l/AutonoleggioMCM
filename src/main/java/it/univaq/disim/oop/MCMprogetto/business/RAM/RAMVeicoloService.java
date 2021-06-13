package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.util.*;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;


public class RAMVeicoloService implements VeicoloService
{
	List<Veicolo> listaveicoli = new ArrayList<Veicolo>();
	int cont_veicoli = 1;
	private boolean veico = false;
	
	
	
	@Override	// CREA
	public void createVeicolo(Veicolo veicolo) throws BusinessException
	{	
		veicolo.setID(cont_veicoli++);
		if(!giaEsistente(veicolo.getTarga()))
		{	listaveicoli.add(veicolo);	}
		else
		{	throw new BusinessException();	}
	}
	
	
	
	@Override	// MODIFICA
	public void updateVeicolo(Veicolo veicolo) throws VeicoloNonTrovatoException
	{	try
		{	for(Veicolo tmp : listaveicoli)
			{
				if(tmp.getTarga().equalsIgnoreCase(veicolo.getTarga()))
				{	
					listaveicoli.remove(tmp);
					listaveicoli.add(veicolo);
				}
			}
		}
		catch(ConcurrentModificationException e)
		{	/* errore checked */	}
	}
	

	@Override	// ELIMINA
	public void deleteVeicolo(Veicolo veicolo) throws VeicoloNonTrovatoException
	{	
		if(veicolo.getStato().equalsIgnoreCase("disponibile"))
		{	listaveicoli.remove(veicolo);	}
		else
		{	throw new VeicoloNonTrovatoException();		}
	}

	
	
	
	
	@Override	// ALL VEICOLI
	public List<Veicolo> FindAllVeicoli() throws BusinessException
	{	return listaveicoli;	}
	
	

	@Override	// GET LISTA VEICOLI STATO
	public List<Veicolo> FindListaVeicoliByStato(String stato) throws VeicoloNonTrovatoException, BusinessException
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
	public List<Veicolo> FindListaVeicoliByTipologia(String tipologia) throws VeicoloNonTrovatoException, BusinessException
	{	
		List<Veicolo> listafiltrata = new ArrayList<Veicolo>();
		for(Veicolo tmp : listaveicoli)
		{
			if(tmp.getTipologia().equalsIgnoreCase(tipologia) || tipologia.equalsIgnoreCase("tutte"))
			{	listafiltrata.add(tmp); 	}
		}
		if(!listafiltrata.isEmpty())
		{	return listafiltrata;	}
		throw new BusinessException();
		
	}
	
	
	@Override   //GET LISTA BY ALIMENTAZIONE
	public List<Veicolo> FindListaVeicoliByAlimentazione(String alimentazione) throws VeicoloNonTrovatoException, BusinessException
	{
		List<Veicolo> listafiltrata = new ArrayList<Veicolo>();
		for(Veicolo tmp : listaveicoli)
		{
			if(tmp.getAlimentazione().equalsIgnoreCase(alimentazione) || alimentazione.equalsIgnoreCase("tutte"))
			{	listafiltrata.add(tmp); 	}
		}
		return listafiltrata;	
	}
	
	
	@Override	// GET LISTA BY MODELLO
	public List<Veicolo> FindListaVeicoliByModello(String modello) throws VeicoloNonTrovatoException, BusinessException
	{	
		List<Veicolo> listafiltrata = new ArrayList<Veicolo>();
		for(Veicolo tmp : listaveicoli)
		{
			if(tmp.getModello().toLowerCase().contains(modello.toLowerCase()))
			{	listafiltrata.add(tmp);	  }
		}
		return listafiltrata;
	}
	
	
	
	@Override	// GET LISTA BY TARIFFA_KM
	public List<Veicolo> FindListaVeicoliByTariffaKM(double min, double max) throws VeicoloNonTrovatoException, BusinessException
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
	public List<Veicolo> FindListaVeicoliByTariffaPeriodo(double min, double max) throws VeicoloNonTrovatoException, BusinessException
	{
		List<Veicolo> listafiltrata = new ArrayList<Veicolo>();
		for(Veicolo tmp : listaveicoli)
		{
			if(tmp.getTariffa_periodo() >= min && tmp.getTariffa_periodo() <= max)
			{	listafiltrata.add(tmp); 	}
		}
		return listafiltrata;
	}	
	
	
	@Override	// GET VEICOLO BY ID
	public Veicolo FindVeicoloById(int id) throws VeicoloNonTrovatoException , BusinessException
	{
		for (Veicolo tmp: listaveicoli)
		{
			if (tmp.getID() == id)
			{	return tmp;	 }	
		}
		throw new VeicoloNonTrovatoException();
	}
	

	
	@Override	// GET STATO VEICOLO
	public String[] FindListStatoVeicolo()
	{	
		String[] s = {"disponibile","non disponibile","in noleggio"};
		return s;
	}
	
	
	@Override	// GET ALIMENTAZIONE VEICOLO
	public String[] FindListAlimentazioneVeicolo()
	{	
		String[] s = {"benzina","diesel","gpl"};
		return s;
	}


	
	//	GIA ESISTENTE
	private Boolean giaEsistente(String targa) throws BusinessException
	{
		List<Veicolo> lista = FindAllVeicoli();
		for(Veicolo tmp : lista)
		{
			if(targa.equalsIgnoreCase(tmp.getTarga()))
			{	return true;	}
		}
		return false;
	}



	@Override
	public void primaAggiunta() 
	{
		if(!veico)
		{
			Veicolo v1 = new Veicolo(1,"lusso","ferrari","benzina",null,"disponibile","AA001AA",false,true,0,100,100);
			Veicolo v2 = new Veicolo(1,"lusso","lamborghini","benzina",null,"disponibile","AA002AA",false,true,0,200,200);
			Veicolo v3 = new Veicolo(1,"berlina","panda","benzina",null,"disponibile","AA003AA",false,true,0,50,50);
			listaveicoli.add(v1);
			listaveicoli.add(v2);
			listaveicoli.add(v3);
		}
		
	}

	


	


	


	
	
	
	
}
