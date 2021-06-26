package it.univaq.disim.oop.MCMprogetto.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Noleggio {
	private Integer ID;
	private LocalDate dataInizioNoleggio;
	private LocalDate dataFineNoleggio;
	private Double prezzo_noleggio;
	private Integer KmPercorsi;
	private ArrayList<Assistenza> listaAssistenza;
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
		return prezzo_noleggio;
	}

	public void setPrezzo_noleggio(Double prezzo_noleggio) {
		this.prezzo_noleggio = prezzo_noleggio;
	}

	public Integer getKmPercorsi() {
		return KmPercorsi;
	}

	public void setKmPercorsi(Integer kM_percorsi) {
		KmPercorsi = kM_percorsi;
	}

	public ArrayList<Assistenza> getListaAssistenza() {
		return listaAssistenza;
	}

	public void setListaAssistenza(ArrayList<Assistenza> listaAssistenza) {
		this.listaAssistenza = listaAssistenza;
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
