package it.univaq.disim.oop.MCMprogetto.business;

import java.time.LocalDate;
import java.util.List;

import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public interface NoleggioService {
	Noleggio createNoleggio(Noleggio noleggio) throws BusinessException;

	void updateNoleggio(Noleggio noleggio) throws BusinessException;

	Noleggio findNoleggioByID(Integer ID) throws NoleggioNonTrovatoException, BusinessException;

	List<Noleggio> findAllNoleggi() throws BusinessException;

	List<Noleggio> findNoleggiInCorso() throws BusinessException;

	List<Noleggio> findNoleggiByUtente(Utente utente) throws BusinessException;

	List<Noleggio> findNoleggiByPagamento() throws BusinessException;

	List<Noleggio> findNoleggiByDettagli() throws BusinessException;
	
	List<Veicolo> findVeicoliDisponibiliForDate(LocalDate datainizio, LocalDate datafine) throws BusinessException;
	
	void rispostaAssistenzaByNoleggio(Noleggio noleggio, Assistenza assistenza) throws BusinessException;
	
	boolean disponibilitaDate(LocalDate inizio, LocalDate fine, Veicolo veicolo) throws BusinessException;
	
	void checkStatoVeicoli() throws BusinessException;
}
