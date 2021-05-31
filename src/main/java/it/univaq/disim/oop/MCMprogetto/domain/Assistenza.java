package it.univaq.disim.oop.MCMprogetto.domain;

public class Assistenza
{
	private int ID;
	private int cellulare; 
	private boolean servita;
	private String dettagli;
	
	
	//	GET E SET
	public int getCellulare() {
		return cellulare;
	}
	public void setCellulare(int cellulare) {
		this.cellulare = cellulare;
	}
	public String getDettagli() {
		return dettagli;
	}
	public void setDettagli(String dettagli) {
		this.dettagli = dettagli;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public boolean isServita() {
		return servita;
	}
	public void setServita(boolean servita) {
		this.servita = servita;
	}
	
	
	
	
}
