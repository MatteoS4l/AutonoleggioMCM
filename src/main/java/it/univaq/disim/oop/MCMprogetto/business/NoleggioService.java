package it.univaq.disim.oop.MCMprogetto.business;

import java.time.LocalDateTime;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public interface NoleggioService 
{
	void createNoleggio(Noleggio noleggio) throws BusinessException;	
	
	Noleggio FindNoleggioByID(int ID) throws NoleggioNonTrovatoException, NumberFormatException, BusinessException;
	
	List<Noleggio> FindAllNoleggi() throws BusinessException;
	
	List<Noleggio> FindNoleggiByAssistenza(String assistenza) throws BusinessException;
	
	List<Noleggio> FindNoleggiInCorso() throws BusinessException;
	
	List<Noleggio> FindNoleggiByUtente(Utente utente) throws BusinessException;
	
	
	

}
