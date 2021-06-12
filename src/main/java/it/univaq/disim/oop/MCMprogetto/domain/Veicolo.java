package it.univaq.disim.oop.MCMprogetto.domain;

import java.util.ArrayList;
import java.util.List;

public class Veicolo
{
	private int ID;
	private String tipologia;
	private String modello;
	private String alimentazione; 
	private ArrayList<Feedback> feedback; 
	private String stato;
	private String targa;
	private boolean manutenzione;
	private boolean disponibilita;
	private int chilometri;
	private double tariffa_chilometraggio;
	private double tariffa_periodo;
	
	
	// GET E SET
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getAlimentazione() {
		return alimentazione;
	}
	public void setTarga(String targa) {
		this.targa = targa;
	}
	public String getTarga() {
		return targa;
	}
	public void setAlimentazione(String alimentazione) {
		this.alimentazione = alimentazione;
	}
	public ArrayList<Feedback> getFeedback() {
		return feedback;
	}
	public void addFeedback(List<Feedback> feedback) {
		this.feedback = (ArrayList<Feedback>) feedback;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}
	public boolean isManutenzione() {
		return manutenzione;
	}
	public void setManutenzione(boolean manutenzione) {
		this.manutenzione = manutenzione;
	}
	public boolean isDisponibilita() {
		return disponibilita;
	}
	public void setDisponibilita(boolean disponibilità) {
		this.disponibilita = disponibilità;
	}
	public int getChilometri() {
		return chilometri;
	}
	public void setChilometri(int chilometri) {
		this.chilometri = chilometri;
	}
	public double getTariffa_chilometraggio() {
		return tariffa_chilometraggio;
	}
	public void setTariffa_chilometraggio(double tariffa_chilometraggio) {
		this.tariffa_chilometraggio = tariffa_chilometraggio;
	}
	public double getTariffa_periodo() {
		return tariffa_periodo;
	}
	public void setTariffa_periodo(double tariffa_periodo) {
		this.tariffa_periodo = tariffa_periodo;
	}
		

	
	
	
	
	
	
	

}
