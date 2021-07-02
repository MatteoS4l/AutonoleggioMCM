package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.util.List;

import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaNonTrovataException;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Tipologia;

public class RAMTipologiaService implements TipologiaService {
	private final List<Tipologia> listaTipologie;
	private final VeicoloService veicoloservice;

	public RAMTipologiaService(List<Tipologia> listaTipologie, VeicoloService veicoloservice) {
		this.listaTipologie = listaTipologie;
		this.veicoloservice = veicoloservice;
	}

	@Override // CREA
	public Tipologia createTipologia(Tipologia tipologia) throws BusinessException {
		if (!GiaEsistente(tipologia)) {
			listaTipologie.add(tipologia);
			return tipologia;
		} else {
			throw new BusinessException("Errore durante la creazione di una tipologia");
		}
	}

	@Override // ELIMINA
	public void deleteTipologia(Tipologia tipologia) throws BusinessException {
		if (veicoloservice.findListaVeicoliByTipologia(tipologia.getNome()).isEmpty()) {
			listaTipologie.remove(tipologia);
		} else {
			throw new TipologiaNonTrovataException();
		}
	}

	@Override // GET LISTA TIPOLOGIA
	public List<Tipologia> findAllTipologia() throws TipologiaNonTrovataException {
		return listaTipologie;
	}

	// GIA ESISTENTE
	private boolean GiaEsistente(Tipologia tipologia) throws TipologiaNonTrovataException, BusinessException {
		for (Tipologia tmp : findAllTipologia()) {
			if (tmp.getNome().equalsIgnoreCase(tipologia.getNome())) {
				return true;
			}
		}
		return false;
	}

	public void prima() throws BusinessException {
		Tipologia t1 = new Tipologia();
		t1.setID(0);
		t1.setNome("lusso");
		Tipologia t2 = new Tipologia();
		t2.setID(1);
		t2.setNome("berlina");
		Tipologia t3 = new Tipologia();
		t3.setID(2);
		t3.setNome("sostitutivo");

		listaTipologie.add(t1);
		listaTipologie.add(t2);
		listaTipologie.add(t3);
	}

}
