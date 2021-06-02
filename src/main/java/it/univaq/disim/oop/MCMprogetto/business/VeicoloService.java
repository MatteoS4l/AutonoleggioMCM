package it.univaq.disim.oop.MCMprogetto.business;

import java.util.List;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public interface VeicoloService
{
	void createVeicolo(Veicolo veicolo) throws BusinessException;	
	
	void updateVeicolo(Veicolo veicolo) throws VeicoloNonTrovatoException;
	
	void deleteVeicolo(Veicolo veicolo) throws VeicoloNonTrovatoException;
	
	List<Veicolo> GetListaVeicoliByStato(String stato) throws BusinessException;
	
	List<Veicolo> GetListaVeicoliByTipologia(String tipologia) throws BusinessException;
	
	List<Veicolo> GetListaVeicoliByAlimentazione(String alimentazione) throws BusinessException;
	
	List<Veicolo> GetListaVeicoliByModello(String modello) throws BusinessException;
	
	List<Veicolo> GetListaVeicoliByTariffaKM(double min, double max) throws BusinessException;
	
	List<Veicolo> GetListaVeicoliByTariffaPeriodo(double min, double max) throws BusinessException;
	
	String[] GetListStatoVeicolo();
	
	String[] GetListAlimentazioneVeicolo();
	
	Veicolo getVeicoloById(int id) throws VeicoloNonTrovatoException, NumberFormatException, BusinessException;
	

	
	
	
	

}
