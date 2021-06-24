package it.univaq.disim.oop.MCMprogetto.domain;

public class Tipologia {
	private String tipologia;
	private Integer ID;

	public Tipologia(String tipologia, int iD) {
		super();
		this.tipologia = tipologia;
		ID = iD;
	}

	public Tipologia() {
	}

	// GET E SET
	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

}
