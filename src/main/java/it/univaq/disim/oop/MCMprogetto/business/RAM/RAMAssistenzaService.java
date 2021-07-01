package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import it.univaq.disim.oop.MCMprogetto.business.AssistenzaNonTrovataException;
import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;

public class RAMAssistenzaService implements AssistenzaService {
	
	List<Assistenza> listassistenza = new ArrayList<Assistenza>();
	int cont_assistenza = 1;

	@Override // CREA
	public Assistenza createAssistenza(Assistenza assistenza) throws BusinessException {
		assistenza.setID(cont_assistenza++);
		listassistenza.add(assistenza);
		return assistenza;
	}

	@Override // MODIFICA
	public void updateAssistenza(Assistenza assistenza) throws BusinessException {
		try {
			for (Assistenza tmp : listassistenza) {
				if (tmp.getID() == assistenza.getID()) {
					listassistenza.remove(tmp);
					listassistenza.add(assistenza);
				}
			}
		} catch (ConcurrentModificationException e) {
			/* errore checked */ }

	}

	@Override // FIND ALL ASSISTENZA
	public List<Assistenza> findAllAssistenza() throws BusinessException {
		return listassistenza;
	}

	@Override // GET ASSISTENZA ID
	public Assistenza getAssistenzaByID(Integer id) throws BusinessException,AssistenzaNonTrovataException {
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

	@Override // GET ASSISTENZA SERVITA
	public List<Assistenza> findListaAssistenzaByServita(boolean servita) throws BusinessException {
		List<Assistenza> listacompleta = new ArrayList<Assistenza>();

		for (Assistenza tmp : findAllAssistenza()) {
			if (servita == true) {
				if (tmp.getRisposta().equalsIgnoreCase("null")) {
					listacompleta.add(tmp);
				}
			}
			if (servita == false) {
				if (tmp.getRisposta().equalsIgnoreCase("null")) {
					listacompleta.add(tmp);
				}
			}
		}
		return listacompleta;
	}

	@Override
	public void deleteAssistenza(Assistenza assistenza) throws BusinessException {
		for (Assistenza a:findAllAssistenza()) {
			if (a.getID()==assistenza.getID()) {
				findAllAssistenza().remove(a);
			}
		}
	}


	@Override
	public List<Assistenza> findAssistenzeByIds(List<Integer> ids) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
