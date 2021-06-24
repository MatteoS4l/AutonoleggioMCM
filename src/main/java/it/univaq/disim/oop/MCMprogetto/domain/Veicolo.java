package it.univaq.disim.oop.MCMprogetto.domain;

import java.util.ArrayList;
import java.util.Set;

public class Veicolo {

	private String targa;
	private String modello;
	private int chilometri;
	private Tipologia tipologia;
	private Alimentazione alimentazione;
	private Stato stato;
	private Double tariffaChilometraggio;   // da mettere nel domain model
	private Double tariffaPeriodo;			// da mettere nel domain model
	private ArrayList<Feedback> feedback;
	private Set<Noleggio> listaNoleggi;
	
	
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
	public ArrayList<Feedback> getFeedback() {
		return feedback;
	}
	public void setFeedback(ArrayList<Feedback> feedback) {
		this.feedback = feedback;
	}
	public Set<Noleggio> getListaNoleggi() {
		return listaNoleggi;
	}
	public void setListaNoleggi(Set<Noleggio> listaNoleggi) {
		this.listaNoleggi = listaNoleggi;
	}
	
	

}
