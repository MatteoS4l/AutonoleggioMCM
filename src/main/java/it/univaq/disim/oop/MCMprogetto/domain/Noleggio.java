package it.univaq.disim.oop.MCMprogetto.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Noleggio {
	private Integer ID;
	private LocalDate data_inizio_noleggio;
	private LocalDate data_fine_noleggio;
	private Double prezzo_noleggio;
	private Integer KM_percorsi;
	private ArrayList<Assistenza> listaAssistenza;
	private Cliente cliente;
	private Veicolo veicolo;

	// GET E SET
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public LocalDate getData_inizio_noleggio() {
		return data_inizio_noleggio;
	}

	public void setData_inizio_noleggio(LocalDate data_inizio_noleggio) {
		this.data_inizio_noleggio = data_inizio_noleggio;
	}

	public LocalDate getData_fine_noleggio() {
		return data_fine_noleggio;
	}

	public void setData_fine_noleggio(LocalDate data_fine_noleggio) {
		this.data_fine_noleggio = data_fine_noleggio;
	}

	public Double getPrezzo_noleggio() {
		return prezzo_noleggio;
	}

	public void setPrezzo_noleggio(Double prezzo_noleggio) {
		this.prezzo_noleggio = prezzo_noleggio;
	}

	public Integer getKM_percorsi() {
		return KM_percorsi;
	}

	public void setKM_percorsi(Integer kM_percorsi) {
		KM_percorsi = kM_percorsi;
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

}
