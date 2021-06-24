package it.univaq.disim.oop.MCMprogetto.domain;

public class Feedback {
	private Integer ID;
	private String feedback;
	private int stelle;
	private Noleggio noleggio;
	private Utente utente;

	// GET E SET
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
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

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}
