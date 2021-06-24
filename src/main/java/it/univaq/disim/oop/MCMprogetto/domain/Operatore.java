package it.univaq.disim.oop.MCMprogetto.domain;

import java.time.LocalDate;

public class Operatore extends Utente {

	public Operatore() {
		super();
	}

	public Operatore(int iD, String nome, String cognome, String username, String password, char sesso, int eta,
			LocalDate data_nascita) {
		super(iD, nome, cognome, username, password, sesso, eta, data_nascita);
		// TODO Auto-generated constructor stub
	}

}
