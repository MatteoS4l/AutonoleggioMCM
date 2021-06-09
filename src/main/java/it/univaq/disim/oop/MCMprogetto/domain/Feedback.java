package it.univaq.disim.oop.MCMprogetto.domain;


public class Feedback
{
	private String feedback;
	private Veicolo veicolo;
	private Utente utente;
	private int ID;
	private int stelle;
	
	
	//	GET E SET
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public Veicolo getVeicolo() {
		return veicolo;
	}
	public void setVeicolo(Veicolo veicolo) {
		this.veicolo = veicolo;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getUtenteId(Utente utente) 
	{ return utente.getUsername(); }
	
	public int getStelle() {
		return stelle;
	}
	public void setStelle(int stelle) {
		this.stelle = stelle;
	}

}
