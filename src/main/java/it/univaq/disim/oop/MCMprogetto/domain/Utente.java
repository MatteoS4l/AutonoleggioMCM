package it.univaq.disim.oop.MCMprogetto.domain;

import java.time.LocalDate;

public class Utente
{
	private int ID;
	private String nome;
	private String cognome;
	private String username;
	private String password;
	private char sesso;
	private int eta;
	private LocalDate data_nascita;
	
	
	
	
	public int getID()		// Get e Set
	{	return ID;	}
	
	public void setID(int id)
	{	this.ID = id;	}
	
	public String getNome()
	{	return nome;	}
	
	public void setNome(String nome)
	{	this.nome = nome;	}
	
	public String getCognome()
	{	return cognome; 	}
	
	public void setCognome(String cognome)
	{	this.cognome = cognome; 	}
	
	public String getUsername()
	{	return username;	}
	
	public void setUsername(String username)
	{	this.username = username;	}
	
	public String getPassword()
	{	return password;	}
	
	public void setPassword(String password)
	{	this.password = password;	}

	public char getSesso()
	{	return sesso;	}

	public void setSesso(char sesso)
	{	this.sesso = sesso;	}

	public int getEta()
	{	return eta;	}

	public void setEta(int eta)
	{	this.eta = eta;	}

	public LocalDate getData_nascita()
	{	return data_nascita;	}

	public void setData_nascita(LocalDate data_nascita)
	{	this.data_nascita = data_nascita;	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
