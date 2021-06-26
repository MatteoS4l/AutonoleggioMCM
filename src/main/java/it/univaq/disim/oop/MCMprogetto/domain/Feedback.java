package it.univaq.disim.oop.MCMprogetto.domain;

public class Feedback {
	private Integer ID;
	private String testo;
	private int stelle;
	private Noleggio noleggio;

	// GET E SET
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public int getStelle() {
		return stelle;
	}

	public void setStelle(int stelle) {
		this.stelle = stelle;
	}

	public Noleggio getNoleggio() {
		return noleggio;
	}

	public void setNoleggio(Noleggio noleggio) {
		this.noleggio = noleggio;
	}

}
