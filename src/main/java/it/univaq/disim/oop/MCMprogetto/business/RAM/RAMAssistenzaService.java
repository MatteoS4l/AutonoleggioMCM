package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import it.univaq.disim.oop.MCMprogetto.business.AssistenzaNonTrovataException;
import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.file.FileData;
import it.univaq.disim.oop.MCMprogetto.business.file.Utility;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;

public class RAMAssistenzaService implements AssistenzaService {

	private final List<Assistenza> listAssistenza;
	int contAssistenza = 1;

	public RAMAssistenzaService(List<Assistenza> listAssistenza) {
		this.listAssistenza = listAssistenza;
	}

	@Override // CREA
	public Assistenza createAssistenza(Assistenza assistenza) throws BusinessException {
		assistenza.setID(contAssistenza++);
		listAssistenza.add(assistenza);
		return assistenza;
	}

	@Override // MODIFICA
	public void updateAssistenza(Assistenza assistenza) throws BusinessException {
		try {
			for (Assistenza tmp : listAssistenza) {
				if (tmp.getID() == assistenza.getID()) {
					listAssistenza.add(assistenza);
					listAssistenza.remove(tmp);
				}
			}
		} catch (ConcurrentModificationException e) {
			/* errore checked */ }
	}

	@Override // ELIMINA
	public void deleteAssistenza(Assistenza assistenza) throws BusinessException {
		try {
			for (Assistenza tmp : listAssistenza) {
				if (tmp.getID() == assistenza.getID()) {
					listAssistenza.remove(tmp);
				}
			}
		} catch (ConcurrentModificationException e) {
			/* errore checked */
		}
	}

	@Override // FIND ALL ASSISTENZA
	public List<Assistenza> findAllAssistenza() throws BusinessException {
		return listAssistenza;
	}

	@Override // GET ASSISTENZA ID
	public Assistenza getAssistenzaByID(Integer id) throws AssistenzaNonTrovataException, BusinessException {
		if (id == null) {
			throw new AssistenzaNonTrovataException();
		}

		List<Assistenza> lista = findAllAssistenza();

		for (Assistenza tmp : lista) {
			if (tmp.getID() == id) {
				return tmp;
			}
		}
		throw new AssistenzaNonTrovataException();
	}

	@Override // FIND ASSISTENZA SERVITA
	public List<Assistenza> findListaAssistenzaByServita(boolean servita) throws BusinessException {
		List<Assistenza> listacompleta = new ArrayList<Assistenza>();

		for (Assistenza tmp : findAllAssistenza()) {
			if (servita == true) {
				if (tmp.getRisposta() == null) {
					listacompleta.add(tmp);
				}
			}
			if (servita == false) {
				if (tmp.getRisposta() == null) {
					listacompleta.add(tmp);
				}
			}
		}
		return listacompleta;
	}

	@Override
	public List<Assistenza> findAssistenzeByIds(List<Integer> ids) throws BusinessException {
		List<Assistenza> res = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows("assistenza");
			for (Integer id : ids) {
				for (String[] colonne : fileData.getRighe()) {
					if (Integer.parseInt(colonne[0]) == id) {
						Assistenza a = new Assistenza();
						a.setID(Integer.parseInt(colonne[0]));
						a.setRichiesta(colonne[1]);
						if (a.getRisposta() != null) {
							a.setRisposta(colonne[2]);
						}
						a.setIdNoleggio(Integer.parseInt(colonne[3]));
						res.add(a);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Errore in findAssistenzeByIds");
		}
		return res;
	}

}
