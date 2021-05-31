package it.univaq.disim.oop.MCMprogetto.business.file;

import java.util.List;

public class FileData
{
	private int contatore;
	private List<String[]> righe;
	
	
	// GET E SET
	public int getCont()
	{ 	return contatore;	}
	
	public void setCont(int cont)
	{ 	this.contatore = cont;	}
	
	public List<String[]> getRighe()
	{ 	return righe;	}
	
	public void setRighe(List<String[]> righe)
	{	this.righe = righe;	}

}
