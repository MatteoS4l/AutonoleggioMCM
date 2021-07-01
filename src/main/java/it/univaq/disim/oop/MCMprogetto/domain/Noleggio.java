package it.univaq.disim.oop.MCMprogetto.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Noleggio {
	private Integer ID;
	private LocalDate dataInizioNoleggio;
	private LocalDate dataFineNoleggio;
	private Double prezzoNoleggio;
	private int kmPercorsi;
	private Cliente cliente;
	private Veicolo veicolo;
	private String dettagli;
	private boolean feedLasciato;
	private List<Assistenza> listaAssistenza = new ArrayList<Assistenza>();

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

	public Double getPrezzoNoleggio() {
		return prezzoNoleggio;
	}

	public void setPrezzoNoleggio(Double prezzo_noleggio) {
		this.prezzoNoleggio = prezzo_noleggio;
	}

	public int getKmPercorsi() {
		return kmPercorsi;
	}

	public void setKmPercorsi(int kM_percorsi) {
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

	public List<Assistenza> getListaAssistenza() {
		return listaAssistenza;
	}

	public void setListaAssistenza(List<Assistenza> listaAssistenza) {
		this.listaAssistenza = listaAssistenza;
	}
	
	public void addListaAssistenza(Assistenza assistenza) {
		this.listaAssistenza.add(assistenza);
	}
	
	public boolean inCorso() {
		if (this.getDataInizioNoleggio().isBefore(LocalDate.now())
				&& this.getDataFineNoleggio().isAfter(LocalDate.now())) {
			return true;
		}
		return false;
	}

	public boolean isFeedLasciato() {
		return feedLasciato;
	}

	public void setFeedLasciato(boolean feedLasciato) {
		this.feedLasciato = feedLasciato;
	}

}
