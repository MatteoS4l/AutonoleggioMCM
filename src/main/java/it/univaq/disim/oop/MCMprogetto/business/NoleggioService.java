package it.univaq.disim.oop.MCMprogetto.business;

<<<<<<< HEAD
import java.time.LocalDate;
import java.time.LocalDateTime;
=======
>>>>>>> 43b28e8baa8f066f4b276c94f47d8f9feccb036f
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;

public interface NoleggioService 
{
	void createNoleggio(Noleggio noleggio) throws BusinessException;	
	
	Noleggio FindNoleggioByID(int ID) throws NoleggioNonTrovatoException, BusinessException;
	
	List<Noleggio> FindAllNoleggi() throws NoleggioNonTrovatoException, BusinessException;
	
	List<Noleggio> FindNoleggiByAssistenza(String assistenza) throws NoleggioNonTrovatoException, BusinessException;
	
	List<Noleggio> FindNoleggiInCorso() throws NoleggioNonTrovatoException, BusinessException;
	
	List<Noleggio> FindNoleggiByUtente(Utente utente) throws NoleggioNonTrovatoException, BusinessException;
	
	List<Noleggio> FindNoleggiByDataFine(LocalDate data_fine) throws BusinessException;
	
	
	

}
