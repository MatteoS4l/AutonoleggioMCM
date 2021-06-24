package it.univaq.disim.oop.MCMprogetto.domain;

public enum Stato {
	DISPONIBILE("disponibile"), NON_DISPONIBILE("non disponibile"), IN_NOLEGGIO("in noleggio");

	private String stato;

	Stato(String desc) {
		this.stato = desc;
	}

	public String getDesc() {
		return stato;
	}

}

