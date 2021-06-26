package it.univaq.disim.oop.MCMprogetto.domain;

import java.time.LocalDate;

public class Noleggio {
	private Integer ID;
	private LocalDate dataInizioNoleggio;
	private LocalDate dataFineNoleggio;
	private Double prezzoNoleggio;
	private Integer kmPercorsi;
	private Cliente cliente;
	private Veicolo veicolo;
	private String dettagli;

	// GET E SET
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public LocalDate getDataInizioNoleggio() {
		return dataInizioNoleggio;
	}

	public void setDataInizioNoleggio(LocalDate data_inizio_noleggio) {
		this.dataInizioNoleggio = data_inizio_noleggio;
	}

	public LocalDate getDataFineNoleggio() {
		return dataFineNoleggio;
	}

	public void setDataFineNoleggio(LocalDate data_fine_noleggio) {
		this.dataFineNoleggio = data_fine_noleggio;
	}

	public Double getPrezzo_noleggio() {
		return prezzoNoleggio;
	}

	public void setPrezzo_noleggio(Double prezzo_noleggio) {
		this.prezzoNoleggio = prezzo_noleggio;
	}

	public Integer getKmPercorsi() {
		return kmPercorsi;
	}

	public void setKmPercorsi(Integer kM_percorsi) {
		kmPercorsi = kM_percorsi;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Veicolo getVeicolo() {
		return veicolo;
	}

	public void setVeicolo(Veicolo veicolo) {
		this.veicolo = veicolo;
	}

	public String getDettagli() {
		return dettagli;
	}

	public void setDettagli(String dettagli) {
		this.dettagli = dettagli;
	}

}
