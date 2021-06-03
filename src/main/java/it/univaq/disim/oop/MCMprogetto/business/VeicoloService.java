package it.univaq.disim.oop.MCMprogetto.business;

import java.util.List;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public interface VeicoloService
{
	void createVeicolo(Veicolo veicolo) throws BusinessException;	
	
	void updateVeicolo(Veicolo veicolo) throws VeicoloNonTrovatoException, BusinessException;
	
	void deleteVeicolo(Veicolo veicolo) throws VeicoloNonTrovatoException, BusinessException;
	
	List<Veicolo> GetListaVeicoliByStato(String stato) throws VeicoloNonTrovatoException, BusinessException;
	
	List<Veicolo> GetListaVeicoliByTipologia(String tipologia) throws VeicoloNonTrovatoException, BusinessException;
	
	List<Veicolo> GetListaVeicoliByAlimentazione(String alimentazione) throws VeicoloNonTrovatoException, BusinessException;
	
	List<Veicolo> GetListaVeicoliByModello(String modello) throws VeicoloNonTrovatoException, BusinessException;
	
	List<Veicolo> GetListaVeicoliByTariffaKM(double min, double max) throws VeicoloNonTrovatoException, BusinessException;
	
	List<Veicolo> GetListaVeicoliByTariffaPeriodo(double min, double max) throws VeicoloNonTrovatoException, BusinessException;
	
	String[] GetListStatoVeicolo()throws BusinessException;
	
	String[] GetListAlimentazioneVeicolo()throws BusinessException;
	
	Veicolo getVeicoloById(int id) throws VeicoloNonTrovatoException, BusinessException;
	

	
	
	
	

}
