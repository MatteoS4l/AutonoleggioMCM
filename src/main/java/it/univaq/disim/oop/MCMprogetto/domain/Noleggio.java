package it.univaq.disim.oop.MCMprogetto.domain;

import java.time.LocalDateTime;

public class Noleggio {
	private Integer ID;
	private Integer KM_percorsi;
	private Double prezzo_noleggio;
	private Assistenza assistenza;
	private Utente cliente;
	private Veicolo veicolo;
	private LocalDateTime data_inizio_noleggio;
	private LocalDateTime data_fine_noleggio;
	private Boolean servita;
	private Boolean feed_lasciato;
	private String dettagli;

	// GET E SET
	public Integer getID() {
		return ID;
	}

	public void setID(Integer id) {
		this.ID = id;
	}

	public Double getPrezzo_noleggio() {
		return prezzo_noleggio;
	}

	public void setPrezzo_noleggio(Double prezzo_noleggio) {
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

	public Integer getKM_percorsi() {
		return KM_percorsi;
	}

	public void setKM_percorsi(Integer kM_percorsi) {
		KM_percorsi = kM_percorsi;
	}

	public Boolean isServita() {
		return servita;
	}

	public void setServita(Boolean servita) {
		this.servita = servita;
	}

	public String getDettagli() {
		return dettagli;
	}

	public void setDettagli(String dettagli) {
		this.dettagli = dettagli;
	}

	public Boolean isFeed_lasciato() {
		return feed_lasciato;
	}

	public void setFeed_lasciato(Boolean feed_lasciato) {
		this.feed_lasciato = feed_lasciato;
	}

}
