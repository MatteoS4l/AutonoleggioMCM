package it.univaq.disim.oop.MCMprogetto.domain;

public enum Sesso {
	UOMO("M"), DONNA("F");

	private String sesso;

	Sesso(String desc) {
		this.sesso = desc;
	}

	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

}
