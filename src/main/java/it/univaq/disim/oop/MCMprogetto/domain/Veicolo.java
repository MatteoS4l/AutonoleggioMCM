package it.univaq.disim.oop.MCMprogetto.domain;

import java.util.List;

public class Veicolo {

	private String targa;
	private String modello;
	private int chilometri;
	private Tipologia tipologia;
	private Alimentazione alimentazione;
	private Stato stato;
	private Double tariffaChilometraggio; // da mettere nel domain model
	private Double tariffaPeriodo; // da mettere nel domain model
	private List<Feedback> listaFeedback;

	// GET E SET
	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public int getChilometri() {
		return chilometri;
	}

	public void setChilometri(int chilometri) {
		this.chilometri = chilometri;
	}

	public Tipologia getTipologia() {
		return tipologia;
	}

	public void setTipologia(Tipologia tipologia) {
		this.tipologia = tipologia;
	}

	public Alimentazione getAlimentazione() {
		return alimentazione;
	}

	public void setAlimentazione(Alimentazione alimentazione) {
		this.alimentazione = alimentazione;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public Double getTariffaChilometraggio() {
		return tariffaChilometraggio;
	}

	public void setTariffaChilometraggio(Double tariffaChilometraggio) {
		this.tariffaChilometraggio = tariffaChilometraggio;
	}

	public Double getTariffaPeriodo() {
		return tariffaPeriodo;
	}

	public void setTariffaPeriodo(Double tariffaPeriodo) {
		this.tariffaPeriodo = tariffaPeriodo;
	}

	public List<Feedback> getListaFeedback() {
		return listaFeedback;
	}

	public void setListaFeedback(List<Feedback> listaFeedback) {
		this.listaFeedback = listaFeedback;
	}
	
	public void addFeedbackToLista(Feedback feedback) {
		this.listaFeedback.add(feedback);
	}

}
