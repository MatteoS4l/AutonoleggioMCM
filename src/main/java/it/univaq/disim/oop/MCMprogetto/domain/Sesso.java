package it.univaq.disim.oop.MCMprogetto.domain;

public enum Sesso {
	UOMO('M'), DONNA('F');

	private char sesso;

	Sesso(char desc) {
		this.sesso = desc;
	}

	public char getSesso() {
		return sesso;
	}

}
