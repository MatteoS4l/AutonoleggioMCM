package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.UtenteNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.domain.Amministratore;
import it.univaq.disim.oop.MCMprogetto.domain.Cliente;
import it.univaq.disim.oop.MCMprogetto.domain.Operatore;
import it.univaq.disim.oop.MCMprogetto.domain.Sesso;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;

public class RAMUtenteService implements UtenteService {
	private static int cont = 1;
	List<Utente> listaUtenti = new ArrayList<Utente>(); // vorse si deve mettere set

	@Override // metodo invocato al momento di login
	public Utente Autenticazione(String username, String password) throws BusinessException {
		if (username.equalsIgnoreCase("admin") && password.equals("admin")) {
			Utente admin = new Amministratore();
			admin.setNome("Carmine");
			admin.setCognome("Pittella");
			admin.setUsername(username);
			admin.setPassword(password);
			admin.setEta(21);
			admin.setSesso(Sesso.UOMO);
			return admin;
		}

		for (Utente utente : listaUtenti) {
			if (username.equalsIgnoreCase(utente.getUsername()) && password.equals(utente.getPassword())) {
				return utente;
			}
		}
		throw new UtenteNonTrovatoException("Username o password errati!");
	}
	
	@Override // CREA
	public void createUtente(Utente utente) throws BusinessException {
		utente.setID(cont++);
		listaUtenti.add(utente);
	}

	@Override // UPDATE
	public void updateUtente(Utente utente) throws BusinessException {
		try {
			for (Utente tmp : listaUtenti) {
				if (tmp.getID() == utente.getID()) {
					listaUtenti.remove(tmp);
					listaUtenti.add(utente);
				}
			}
		} catch (ConcurrentModificationException e) {
			/* errore checked */ }
	}

	@Override // ELIMINA
	public void deleteUtente(Utente utente) throws UtenteNonTrovatoException {
		listaUtenti.remove(utente);
	}

	@Override // GET LISTA UTENTI
	public List<Utente> findAllUtenti() throws BusinessException {
		return listaUtenti;
	}


	@Override // CALCOLA ETA
	public int CalcolaEta(LocalDate data) throws BusinessException {
		int eta = 0;
		try {
			eta = LocalDate.now().getYear() - data.getYear();
		} catch (NullPointerException e) {
			System.err.println("Inserire la data"); }

		if (data.getMonthValue() > LocalDate.now().getMonthValue()) {
			return --eta;
		}
		if (data.getMonthValue() == LocalDate.now().getMonthValue()
				&& data.getDayOfMonth() > LocalDate.now().getDayOfMonth()) {
			return --eta;
		}
		return eta;
	}

	@Override // GET UTENTE BY ID
	public Utente findUtenteById(int id) throws UtenteNonTrovatoException, BusinessException {
		for (Utente tmp : listaUtenti) {
			if (tmp.getID() == id) {
				return tmp;
			}
		}
		throw new UtenteNonTrovatoException();
	}

	@Override
	public void prima() throws BusinessException {
		Utente u = new Cliente();
		u.setNome("a");
		u.setCognome("a");
		u.setEta(0);
		u.setDataNascita(LocalDate.now());
		u.setSesso(Sesso.UOMO);
		u.setUsername("a");
		u.setPassword("a");
		u.setID(1);
		
		Utente o = new Operatore();
		o.setNome("o");
		o.setCognome("o");
		o.setEta(0);
		o.setDataNascita(LocalDate.now());
		o.setSesso(Sesso.UOMO);
		o.setUsername("o");
		o.setPassword("o");
		o.setID(2);
		
		createUtente(u);
		createUtente(o);
	}

}
