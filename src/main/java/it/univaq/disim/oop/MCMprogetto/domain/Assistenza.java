package it.univaq.disim.oop.MCMprogetto.domain;

public class Assistenza {
	private Integer ID;
	private String richiesta;
	private String risposta;
	private Integer idNoleggio;
	private Noleggio noleggio;

	// GET E SET
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getRichiesta() {
		return richiesta;
	}

	public void setRichiesta(String richiesta) {
		this.richiesta = richiesta;
	}

	public String getRisposta() {
		return risposta;
	}

	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}

	public Noleggio getNoleggio() {
		return noleggio;
	}

	public void setNoleggio(Noleggio noleggio) {
		this.noleggio = noleggio;
	}

	public Integer getIdNoleggio() {
		return idNoleggio;
	}

	public void setIdNoleggio(Integer idNoleggio) {
		this.idNoleggio = idNoleggio;
	}
}