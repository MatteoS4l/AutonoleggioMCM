package it.univaq.disim.oop.MCMprogetto.domain;

import java.time.LocalDate;
import java.util.Set;

public class Cliente extends Utente {

	// private int eta;	bovino 25/6
	//private LocalDate dataNascita;  bovino 24/6
	private Set<Noleggio> listaNoleggi;

	// GET E SET
	

	/*public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}*/

	public Set<Noleggio> getListaNoleggi() {
		return listaNoleggi;
	}
	
	public void addNoleggio(Noleggio noleggio) {
		listaNoleggi.add(noleggio);
	}

	public void setListaNoleggi(Set<Noleggio> listaNoleggi) {
		this.listaNoleggi = listaNoleggi;
	}

	// METODI
	public void RichiediAssistenza() {
		
	}

}
