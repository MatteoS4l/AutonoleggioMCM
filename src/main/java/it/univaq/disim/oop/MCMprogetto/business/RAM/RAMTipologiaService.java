package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaNonTrovataException;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Tipologia;

public class RAMTipologiaService implements TipologiaService {
	List<Tipologia> listatipologie = new ArrayList<Tipologia>();
	VeicoloService veicoloservice = null;
	boolean tipo = false; // da togliere

	@Override // CREA
	public void createTipologia(Tipologia tipologia) throws BusinessException {
		for (Tipologia tmp : listatipologie) {
			if (tmp.getTipologia().equalsIgnoreCase(tipologia.getTipologia())) {
				throw new BusinessException();
			}
		}
		listatipologie.add(tipologia);
	}

	@Override // ELIMINA
	public void deleteTipologia(Tipologia tipologia) throws BusinessException {
		if (veicoloservice.findListaVeicoliByTipologia(tipologia.getTipologia()).isEmpty()) {
			listatipologie.remove(tipologia);
		}

		else {
			throw new TipologiaNonTrovataException();
		}
	}

	@Override // GET LISTA TIPOLOGIA
	public List<String> findAllTipologia() throws TipologiaNonTrovataException {
		List<String> listaT = new ArrayList<String>();
		for (Tipologia tmp : listatipologie) {
			listaT.add(tmp.getTipologia());
		}

		return listaT;
	}

	@Override // GET LISTA TIPOLOGIA T
	public List<Tipologia> findAllTipologiaT() throws TipologiaNonTrovataException, BusinessException {
		return listatipologie;
	}

}
