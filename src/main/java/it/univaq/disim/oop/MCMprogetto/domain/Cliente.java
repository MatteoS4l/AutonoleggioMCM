package it.univaq.disim.oop.MCMprogetto.domain;

public class Cliente extends Utente
{
	private Noleggio noleggio;


	// GET E SET
	public Noleggio getNoleggio() {
		return noleggio;
	}
	public void setNoleggio(Noleggio noleggio) {
		this.noleggio = noleggio;
	}
	
	public String toString()
	{	
		return ""+getNome()+""+getCognome();
		
	}
	
	
	
	
}
