package it.univaq.disim.oop.MCMprogetto.domain;

import java.time.LocalDate;

public class Cliente extends Utente
{

	public Cliente() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cliente(int iD, String nome, String cognome, String username, String password, char sesso, int eta,
			LocalDate data_nascita) {
		super(iD, nome, cognome, username, password, sesso, eta, data_nascita);
		// TODO Auto-generated constructor stub
	}
	
}
