package it.univaq.disim.oop.MCMprogetto.business;

import java.util.List;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public interface VeicoloService
{
	void createVeicolo(Veicolo veicolo) throws BusinessException;	
	
	void updateVeicolo(Veicolo veicolo) throws BusinessException;
	
	void deleteVeicolo(Veicolo veicolo) throws BusinessException;
	
	List<Veicolo> FindAllVeicoli() throws BusinessException;
	
	List<Veicolo> FindListaVeicoliByStato(String stato) throws BusinessException;
	
	List<Veicolo> FindListaVeicoliByTipologia(String tipologia) throws BusinessException;
	
	List<Veicolo> FindListaVeicoliByAlimentazione(String alimentazione) throws BusinessException;
	
	List<Veicolo> FindListaVeicoliByModello(String modello) throws BusinessException;
	
	List<Veicolo> FindListaVeicoliByTariffaKM(double min, double max) throws BusinessException;
	
	List<Veicolo> FindListaVeicoliByTariffaPeriodo(double min, double max) throws BusinessException;
	
	String[] FindListStatoVeicolo() throws BusinessException;
	
	String[] FindListAlimentazioneVeicolo() throws BusinessException;
	
	Veicolo FindVeicoloById(int id) throws VeicoloNonTrovatoException, BusinessException;
	

	
	
	
	

}
