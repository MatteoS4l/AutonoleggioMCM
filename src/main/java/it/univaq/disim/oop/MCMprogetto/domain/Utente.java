package it.univaq.disim.oop.MCMprogetto.domain;

import java.time.LocalDate;

public class Utente {

	public Utente() {
	}

	public Utente(int iD, String nome, String cognome, String username, String password, char sesso, int eta,
			LocalDate data_nascita) {
		ID = iD;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.password = password;
		this.sesso = sesso;
		this.eta = eta;
		this.dataNascita = data_nascita;
	}

	private Integer ID;
	private String nome;
	private String cognome;
	private String username;
	private String password;
	private Character sesso;
	private Integer eta;
	private LocalDate dataNascita;

	// GET E SET
	public Integer getID() {
		return ID;
	}
	public void setID(Integer id) {
		this.ID = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Character getSesso() {
		return sesso;
	}
	public void setSesso(Character sesso) {
		this.sesso = sesso;
	}
	public Integer getEta() {
		return eta;
	}
	public void setEta(Integer eta) {
		this.eta = eta;
	}
	public LocalDate getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(LocalDate data_nascita) {
		this.dataNascita = data_nascita;
	}

}
