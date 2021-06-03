package it.univaq.disim.oop.MCMprogetto.business;

import java.time.LocalDate;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.domain.Amministratore;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;

public interface UtenteService 
{
	/*tutte queste variabili è come se fossero (public static final) */
	Utente Autenticazione(String username, String password) throws UtenteNonTrovatoException, BusinessException;
	
	List<Utente> GetListaAllUtenti(Amministratore admin) throws UtenteNonTrovatoException, BusinessException;
	
	void createUtente(Utente utente) throws BusinessException;	
	
	void updateUtente(Utente utente) throws UtenteNonTrovatoException, BusinessException;
	
	int CalcolaEta(LocalDate data) throws BusinessException;
	
	void deleteUtente(Utente utente) throws UtenteNonTrovatoException, BusinessException;
	
	Utente getUtenteById(int id) throws UtenteNonTrovatoException, BusinessException;
	
}
