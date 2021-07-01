package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.util.*;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Alimentazione;
import it.univaq.disim.oop.MCMprogetto.domain.Stato;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public class RAMVeicoloService implements VeicoloService {
	List<Veicolo> listaveicoli = new ArrayList<Veicolo>();
	int cont_veicoli = 1;
	public static boolean veico = false;

	@Override // CREA
	public Veicolo createVeicolo(Veicolo veicolo) throws BusinessException {
		if (!giaEsistente(veicolo.getTarga())) {
			listaveicoli.add(veicolo);
			return veicolo;
		} else {
			throw new BusinessException();
		}
	}

	@Override // MODIFICA
	public void updateVeicolo(Veicolo veicolo) throws VeicoloNonTrovatoException {
		try {
			for (Veicolo tmp : listaveicoli) {

				if (tmp.getTarga().equalsIgnoreCase(veicolo.getTarga())) {
					listaveicoli.add(veicolo);
					listaveicoli.remove(tmp);
				}
			}
		} catch (ConcurrentModificationException e) {
			/* errore checked */ }
	}

	@Override // ELIMINA
	public void deleteVeicolo(Veicolo veicolo) throws VeicoloNonTrovatoException {
		if (veicolo.getStato().equals(Stato.DISPONIBILE)) {
			listaveicoli.remove(veicolo);
		} else {
			throw new VeicoloNonTrovatoException("Impossibile eliminare questo veicolo");
		}
	}

	@Override // ALL VEICOLI
	public List<Veicolo> findAllVeicoli() throws BusinessException {
		return listaveicoli;
	}

	@Override // GET LISTA VEICOLI STATO
	public List<Veicolo> findListaVeicoliByStato(Stato stato) throws VeicoloNonTrovatoException, BusinessException {
		List<Veicolo> listacompleta = new ArrayList<Veicolo>();

		for (Veicolo tmp : findAllVeicoli()) {
			if (tmp.getStato().equals(stato)) {
				listacompleta.add(tmp);
			}
		}
		return listacompleta;
	}

	@Override // GET LISTA VEICOLI TIPOLOGIA
	public List<Veicolo> findListaVeicoliByTipologia(String tipologia)
			throws VeicoloNonTrovatoException, BusinessException {
		if (tipologia.equalsIgnoreCase("tutte")) {
			return findAllVeicoli();
		}
		List<Veicolo> listacompleta = new ArrayList<Veicolo>();

		for (Veicolo tmp : findAllVeicoli()) {
			if (tmp.getTipologia().getNome().equalsIgnoreCase(tipologia)) {
				listacompleta.add(tmp);
			}
		}
		return listacompleta;
	}

	@Override // GET LISTA BY ALIMENTAZIONE
	public List<Veicolo> findListaVeicoliByAlimentazione(Alimentazione alimentazione)
			throws VeicoloNonTrovatoException, BusinessException {
		List<Veicolo> listacompleta = new ArrayList<Veicolo>();
		for (Veicolo tmp : findAllVeicoli()) {
			if (tmp.getAlimentazione().equals(alimentazione)) {
				listacompleta.add(tmp);
			}
		}
		return listacompleta;
	}

	@Override // GET LISTA BY MODELLO
	public List<Veicolo> findListaVeicoliByModello(String modello)
			throws VeicoloNonTrovatoException, BusinessException {
		List<Veicolo> listacompleta = new ArrayList<Veicolo>();

		for (Veicolo tmp : findAllVeicoli()) {
			if (tmp.getModello().toLowerCase().contains(modello.toLowerCase())) {
				listacompleta.add(tmp);
			}
		}
		return listacompleta;
	}

	@Override // GET LISTA BY TARIFFA_KM
	public List<Veicolo> findListaVeicoliByTariffaKM(double min, double max)
			throws VeicoloNonTrovatoException, BusinessException {
		List<Veicolo> listacompleta = new ArrayList<Veicolo>();

		for (Veicolo tmp : findAllVeicoli()) {
			if (tmp.getTariffaChilometraggio() >= min && tmp.getTariffaChilometraggio() <= max) {
				listacompleta.add(tmp);
			}
		}
		return listacompleta;
	}

	@Override // GET LISTA BY TARIFFA_PERIODO
	public List<Veicolo> findListaVeicoliByTariffaPeriodo(double min, double max)
			throws VeicoloNonTrovatoException, BusinessException {
		List<Veicolo> listacompleta = new ArrayList<Veicolo>();
		for (Veicolo tmp : findAllVeicoli()) {
			if (tmp.getTariffaPeriodo() >= min && tmp.getTariffaPeriodo() <= max) {
				listacompleta.add(tmp);
			}
		}
		return listacompleta;
	}

	// GIA ESISTENTE
	private Boolean giaEsistente(String targa) throws BusinessException {
		for (Veicolo tmp : findAllVeicoli()) {
			if (targa.equalsIgnoreCase(tmp.getTarga())) {
				return true;
			}
		}
		return false;
	}

	@Override // FIND VEICOLO BY TARGA
	public Veicolo findVeicoloByTarga(String targa) throws VeicoloNonTrovatoException, BusinessException {
		for (Veicolo tmp : findAllVeicoli()) {
			if (tmp.getTarga().equalsIgnoreCase(targa)) {
				return tmp;
			}
		}
		throw new VeicoloNonTrovatoException();
	}

}
