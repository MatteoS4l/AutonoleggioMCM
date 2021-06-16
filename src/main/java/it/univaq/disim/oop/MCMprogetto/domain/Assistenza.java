package it.univaq.disim.oop.MCMprogetto.domain;

public class Assistenza
{
	private Integer ID;
	private Boolean servita;
	private String dettagli;
	private String risposta;
	private Veicolo veicolo;
	private Utente cliente;
	
	
	//	GET E SET
	public String getDettagli() {
		return dettagli;
	}
	public void setDettagli(String dettagli) {
		this.dettagli = dettagli;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Boolean isServita() {
		return servita;
	}
	public void setServita(Boolean servita) {
		this.servita = servita;
	}
	public String getRisposta() {
		return risposta;
	}
	public void setRisposta(String risposta) {
		this.risposta = risposta;
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
	public void setCliente(Utente cliente) {
		this.cliente = cliente;
	}
	
	
	
	
}
