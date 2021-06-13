package it.univaq.disim.oop.MCMprogetto.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public interface NoleggioService 
{
	void createNoleggio(Noleggio noleggio) throws BusinessException;
	
	void updateNoleggio(Noleggio noleggio) throws BusinessException;
	
	Noleggio FindNoleggioByID(int ID) throws NoleggioNonTrovatoException, BusinessException;
	
	Noleggio FindNoleggioByIdAssistenza(int ID) throws BusinessException;
	
	List<Noleggio> FindAllNoleggi() throws BusinessException;
	
	List<Noleggio> FindNoleggiByAssistenza(String assistenza) throws BusinessException;
	
	List<Noleggio> FindNoleggiInCorso() throws BusinessException;
	
	List<Noleggio> FindNoleggiByUtente(Utente utente) throws BusinessException;
	
	List<Noleggio> FindNoleggiByPagamento() throws BusinessException;
	
	List<Noleggio> FindNoleggiByDataFine(LocalDate data_fine) throws BusinessException;
	
	List<Veicolo> FindVeicoliDisponibiliForDate(LocalDateTime datainizio, LocalDateTime datafine) throws BusinessException;
	
	List<Noleggio> FindNoleggiByDettagli() throws BusinessException;
	
	boolean DisponibilitaDate(LocalDateTime inizio, LocalDateTime fine, Veicolo veicolo) throws BusinessException;
	
	void CheckStatoVeicoli()throws BusinessException;
	
	
	

}
