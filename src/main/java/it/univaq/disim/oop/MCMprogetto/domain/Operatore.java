package it.univaq.disim.oop.MCMprogetto.domain;

import java.time.LocalDate;

public class Operatore extends Utente {

	private int eta;
	private LocalDate dataNascita;

	// GET E SET
	public int getEta() {
		return eta;
	}

	public void setEta(int eta) {
		this.eta = eta;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

}
