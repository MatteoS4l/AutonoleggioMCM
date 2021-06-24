package it.univaq.disim.oop.MCMprogetto.domain;

import java.util.ArrayList;
import java.util.List;

public class Veicolo {

	private Integer ID;
	private String tipologia;
	private String modello;
	private String alimentazione;
	private ArrayList<Feedback> feedback;
	private String stato;
	private String targa;
	private Boolean manutenzione;
	private Boolean disponibilita;
	private Integer chilometri;
	private Double tariffa_chilometraggio;
	private Double tariffa_periodo;

	public Veicolo(Integer iD, String tipologia, String modello, String alimentazione, ArrayList<Feedback> feedback,
			String stato, String targa, Boolean manutenzione, Boolean disponibilita, Integer chilometri,
			Double tariffa_chilometraggio, Double tariffa_periodo) {
		super();
		ID = iD;
		this.tipologia = tipologia;
		this.modello = modello;
		this.alimentazione = alimentazione;
		this.feedback = feedback;
		this.stato = stato;
		this.targa = targa;
		this.manutenzione = manutenzione;
		this.disponibilita = disponibilita;
		this.chilometri = chilometri;
		this.tariffa_chilometraggio = tariffa_chilometraggio;
		this.tariffa_periodo = tariffa_periodo;
	}

	public Veicolo() {
	}

	// GET E SET
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
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

	public Boolean isManutenzione() {
		return manutenzione;
	}

	public void setManutenzione(Boolean manutenzione) {
		this.manutenzione = manutenzione;
	}

	public Boolean isDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(Boolean disponibilità) {
		this.disponibilita = disponibilità;
	}

	public Integer getChilometri() {
		return chilometri;
	}

	public void setChilometri(Integer chilometri) {
		this.chilometri = chilometri;
	}

	public Double getTariffa_chilometraggio() {
		return tariffa_chilometraggio;
	}

	public void setTariffa_chilometraggio(Double tariffa_chilometraggio) {
		this.tariffa_chilometraggio = tariffa_chilometraggio;
	}

	public Double getTariffa_periodo() {
		return tariffa_periodo;
	}

	public void setTariffa_periodo(Double tariffa_periodo) {
		this.tariffa_periodo = tariffa_periodo;
	}

}
