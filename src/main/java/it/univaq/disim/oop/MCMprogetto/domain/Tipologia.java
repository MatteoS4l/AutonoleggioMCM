package it.univaq.disim.oop.MCMprogetto.domain;

public class Tipologia
{
	private String tipologia;
	private int ID;

	
	public Tipologia()
	{}
	
	public Tipologia(String tipologia)
	{
		this.tipologia = tipologia;
	}
	
	
	// GET E SET
	public String getTipologia()
	{	return tipologia;	}

	public void setTipologia(String tipologia)
	{	this.tipologia = tipologia; 	}

	public int getID()
	{	return ID;	}

	public void setID(int iD)
	{	ID = iD;	}
	
	
	
}
