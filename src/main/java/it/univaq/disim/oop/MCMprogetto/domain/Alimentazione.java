package it.univaq.disim.oop.MCMprogetto.domain;

public enum Alimentazione {
	BENZINA("benzina"), DIESEL("diesel"), GPL("gpl"), ELETTRICO("elettrico");

	private String alim;

	Alimentazione(String desc) {
		this.alim = desc;
	}

	public String getAlim() {
		return alim;
	}

}
