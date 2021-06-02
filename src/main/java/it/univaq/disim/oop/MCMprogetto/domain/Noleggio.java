package it.univaq.disim.oop.MCMprogetto.domain;

import java.time.LocalDateTime;

public class Noleggio
{
	private int ID;
	private int KM_percorsi;
	private double prezzo_noleggio;
	private Assistenza assistenza;
	private Utente cliente;
	private Veicolo veicolo;
	private LocalDateTime data_checkin_auto;
	private LocalDateTime data_inizio_noleggio;
	private LocalDateTime data_fine_noleggio;
	private int km_fatti;
	private boolean servita;
	private String dettagli;
	
	
	
	// GET E SET
	public int getID() {
		return ID;
	}
	public void setID(int id) {
		this.ID = id;
	}
	public double getPrezzo_noleggio() {
		return prezzo_noleggio;
	}
	public void setPrezzo_noleggio(double prezzo_noleggio) {
		this.prezzo_noleggio = prezzo_noleggio;
	}
	public Assistenza getAssistenza() {
		return assistenza;
	}
	public void setAssistenza(Assistenza assistenza) {
		this.assistenza = assistenza;
	}
	public Veicolo getVeicolo() {
		return veicolo;
	}
	public void setVeicolo(Veicolo veicolo) {
		this.veicolo = veicolo;
	}
	public Utente getCliente() {
		return cliente;
	}
	public void setCliente(Utente utente) {
		this.cliente = utente;
	}
	public LocalDateTime getData_inizio_noleggio() {
		return data_inizio_noleggio;
	}
	public void setData_inizio_noleggio(LocalDateTime data_inizio_noleggio) {
		this.data_inizio_noleggio = data_inizio_noleggio;
	}
	public LocalDateTime getData_fine_noleggio() {
		return data_fine_noleggio;
	}
	public void setData_fine_noleggio(LocalDateTime data_fine_noleggio) {
		this.data_fine_noleggio = data_fine_noleggio;
	}
	public int getKM_percorsi() {
		return KM_percorsi;
	}
	public void setKM_percorsi(int kM_percorsi) {
		KM_percorsi = kM_percorsi;
	}
	public LocalDateTime getData_checkin_auto() {
		return data_checkin_auto;
	}
	public void setData_checkin_auto(LocalDateTime data_checkin_auto) {
		this.data_checkin_auto = data_checkin_auto;
	}
	public int getKm_fatti() {
		return km_fatti;
	}
	public void setKm_fatti(int km_fatti) {
		this.km_fatti = km_fatti;
	}
	public boolean isServita() {
		return servita;
	}
	public void setServita(boolean servita) {
		this.servita = servita;
	}
	public String getDettagli() {
		return dettagli;
	}
	public void setDettagli(String dettagli) {
		this.dettagli = dettagli;
	}
	

	
	
	
	
}
