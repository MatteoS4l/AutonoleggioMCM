package it.univaq.disim.oop.MCMprogetto.business;

import java.util.List;

import it.univaq.disim.oop.MCMprogetto.domain.Alimentazione;
import it.univaq.disim.oop.MCMprogetto.domain.Stato;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public interface VeicoloService {
	Veicolo createVeicolo(Veicolo veicolo) throws BusinessException;

	void updateVeicolo(Veicolo veicolo) throws BusinessException;

	void deleteVeicolo(Veicolo veicolo) throws BusinessException;

	List<Veicolo> findAllVeicoli() throws BusinessException;

	List<Veicolo> findListaVeicoliByStato(Stato stato) throws BusinessException;

	List<Veicolo> findListaVeicoliByTipologia(String tipologia) throws BusinessException;

	List<Veicolo> findListaVeicoliByAlimentazione(Alimentazione alimentazione) throws BusinessException;

	List<Veicolo> findListaVeicoliByModello(String modello) throws BusinessException;

	List<Veicolo> findListaVeicoliByTariffaKM(double min, double max) throws BusinessException;

	List<Veicolo> findListaVeicoliByTariffaPeriodo(double min, double max) throws BusinessException;

	Veicolo findVeicoloByTarga(String targa) throws VeicoloNonTrovatoException, BusinessException;

}
