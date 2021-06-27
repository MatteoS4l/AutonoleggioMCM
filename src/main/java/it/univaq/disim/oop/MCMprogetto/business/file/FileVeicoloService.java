package it.univaq.disim.oop.MCMprogetto.business.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Alimentazione;
import it.univaq.disim.oop.MCMprogetto.domain.Stato;
import it.univaq.disim.oop.MCMprogetto.domain.Tipologia;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public class FileVeicoloService implements VeicoloService { // targa, modello, KM, tipologia, alimentazione,
															// stato, tariffaKM, tariffaPeriodo
	@Override // CREA
	public Veicolo createVeicolo(Veicolo veicolo) throws BusinessException {
		if (!giaEsistente(veicolo.getTarga())) {
			try {
				FileData fileData = Utility.readAllRows("veicoli");
				try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "veicoli" + Utility.FORMATO))) {
					int contatore = fileData.getCont();
					writer.println((contatore + 1));

					for (String[] righe : fileData.getRighe()) {
						writer.println(String.join(Utility.SEPARATORE, righe));
					}
					
					StringBuilder row = new StringBuilder();
					row.append(fileData.getCont());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getTarga());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getModello());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getChilometri());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getTipologia());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getAlimentazione());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getStato());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getTariffaChilometraggio());
					row.append(Utility.SEPARATORE);
					row.append(veicolo.getTariffaPeriodo());

					writer.println(row.toString());
					return veicolo;
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException("Errore durante la creazione di Veicolo");
			}
		} else {
			throw new BusinessException("ERRORE: Veicolo già esistente");
		}
	}

	@Override // MODIFICA
	public void updateVeicolo(Veicolo veicolo) throws VeicoloNonTrovatoException, BusinessException {
		try {
			FileData fileData = Utility.readAllRows("veicoli");
			try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "veicoli" + Utility.FORMATO))) {
				writer.println(fileData.getCont());
				for (String[] righe : fileData.getRighe()) {
					if ((righe[1]).equalsIgnoreCase(veicolo.getTarga())) {
						StringBuilder row = new StringBuilder();

						row.append(fileData.getCont());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getTarga());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getModello());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getChilometri());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getTipologia());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getAlimentazione());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getStato());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getTariffaChilometraggio());
						row.append(Utility.SEPARATORE);
						row.append(veicolo.getTariffaPeriodo());

						writer.println(row.toString());
					} else {
						writer.println(String.join(Utility.SEPARATORE, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("errore durante la modifica di Veicolo");
		}
	}

	@Override // ELIMINA
	public void deleteVeicolo(Veicolo veicolo) throws VeicoloNonTrovatoException, BusinessException {
		try {
			FileData fileData = Utility.readAllRows("veicoli");
			try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "veicoli" + Utility.FORMATO))) {
				writer.println(fileData.getCont());
				for (String[] righe : fileData.getRighe()) {
					if ((righe[0]).equalsIgnoreCase(veicolo.getTarga())
							&& veicolo.getStato().getDesc().equalsIgnoreCase("disponibile")) {
						return;
					} else {
						writer.println(String.join(Utility.SEPARATORE, righe));
					}
				}
				throw new VeicoloNonTrovatoException();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Errore durante eliminazione Veicolo");
		}
	}

	@Override // ALL VEICOLI
	public List<Veicolo> findAllVeicoli() throws BusinessException {
		try {
			List<Veicolo> listacompleta = new ArrayList<Veicolo>();
			FileData filedata = Utility.readAllRows("veicoli");

			for (String[] colonne : filedata.getRighe()) {
				Veicolo veicolo = new Veicolo();
				veicolo.setTarga(colonne[1]);
				veicolo.setModello(colonne[2]);
				veicolo.setChilometri(Integer.parseInt(colonne[3]));
				Tipologia t = new Tipologia();
				t.setNome(colonne[4]);
				veicolo.setTipologia(t);
				veicolo.setAlimentazione(Alimentazione.valueOf(colonne[5]));
				veicolo.setStato(Stato.valueOf(colonne[6]));
				veicolo.setTariffaChilometraggio(Double.parseDouble(colonne[7]));
				veicolo.setTariffaPeriodo(Double.parseDouble(colonne[8]));

				listacompleta.add(veicolo);
			}

			return listacompleta;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Errore in findAllVeicoli");
		}
	}

	@Override // GET LISTA VEICOLI STATO
	public List<Veicolo> findListaVeicoliByStato(String stato) throws VeicoloNonTrovatoException, BusinessException {
		if (stato.equalsIgnoreCase("tutte")) {
			return findAllVeicoli();
		}
		List<Veicolo> listacompleta = new ArrayList<Veicolo>();

		for (Veicolo tmp : findAllVeicoli()) {
			if (tmp.getStato().getDesc().equalsIgnoreCase(stato)) {
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
	public List<Veicolo> findListaVeicoliByAlimentazione(Alimentazione alimentazione) throws VeicoloNonTrovatoException, BusinessException {
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
		throw new VeicoloNonTrovatoException("TARGA NON TROVATA");
	}

}
