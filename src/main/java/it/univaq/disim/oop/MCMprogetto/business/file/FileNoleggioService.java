package it.univaq.disim.oop.MCMprogetto.business.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
import it.univaq.disim.oop.MCMprogetto.domain.Cliente;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Stato;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public class FileNoleggioService implements NoleggioService {
	private UtenteService utenteservice = new FileUtenteService();
	private VeicoloService veicoloservice = new FileVeicoloService();
	private AssistenzaService assistenzaservice = new FileAssistenzaService();

	@Override // CREA
	public Noleggio createNoleggio(Noleggio noleggio) throws BusinessException {
		// id, dataInizio, dataFine, prezzo, kmPercorsi, idCliente, targaVeicolo,
		// dettagli
		try {
			FileData fileData = Utility.readAllRows("noleggi");
			try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "noleggi" + Utility.FORMATO))) {
				int contatore = fileData.getCont();
				writer.println((contatore + 1));

				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE, righe));
				}

				StringBuilder row = new StringBuilder();
				row.append(fileData.getCont());
				row.append(Utility.SEPARATORE);
				row.append(noleggio.getDataInizioNoleggio().toString().replace("T", " "));
				row.append(Utility.SEPARATORE);
				row.append(noleggio.getDataFineNoleggio().toString().replace("T", " "));
				row.append(Utility.SEPARATORE);
				row.append(noleggio.getPrezzo_noleggio());
				row.append(Utility.SEPARATORE);
				row.append(noleggio.getKmPercorsi());
				row.append(Utility.SEPARATORE);
				row.append(noleggio.getCliente().getID());
				row.append(Utility.SEPARATORE);
				row.append(noleggio.getVeicolo().getTarga());
				row.append(Utility.SEPARATORE);
				row.append(noleggio.getDettagli());
				row.append(Utility.SEPARATORE);
				row.append("-");

				writer.println(row.toString());
			}
			return noleggio;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("noleggio non creato");
		}
	}

	@Override // MODIFICA
	public void updateNoleggio(Noleggio noleggio) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows("noleggi");
			try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "noleggi" + Utility.FORMATO))) {
				writer.println(fileData.getCont());
				for (String[] righe : fileData.getRighe()) {
					if (Integer.parseInt(righe[0]) == noleggio.getID()) {
						StringBuilder row = new StringBuilder();

						row.append(noleggio.getID());
						row.append(Utility.SEPARATORE);
						row.append(noleggio.getDataInizioNoleggio().toString().replace("T", " "));
						row.append(Utility.SEPARATORE);
						row.append(noleggio.getDataFineNoleggio().toString().replace("T", " "));
						row.append(Utility.SEPARATORE);
						row.append(noleggio.getPrezzo_noleggio());
						row.append(Utility.SEPARATORE);
						row.append(noleggio.getKmPercorsi());
						row.append(Utility.SEPARATORE);
						row.append(noleggio.getCliente().getID());
						row.append(Utility.SEPARATORE);
						row.append(noleggio.getVeicolo().getTarga());
						row.append(Utility.SEPARATORE);
						row.append(noleggio.getDettagli());
						row.append(Utility.SEPARATORE);
<<<<<<< HEAD
						if(!noleggio.getListaAssistenza().isEmpty()) {
							for(Assistenza tmp : noleggio.getListaAssistenza()) {
								row.append("-");
								row.append(tmp.getID());
							}
=======
						row.append("-");
						int i;
						for (i=0;i<noleggio.getListaAssistenza().size();i++) {
							 row.append(noleggio.getListaAssistenza().get(i).getID());
							 System.out.println(noleggio.getListaAssistenza().get(i).getID());
>>>>>>> 466df313274d883c4db0765b7b787029b72720bf
						}
						writer.println(row.toString());
					} else {
						writer.println(String.join(Utility.SEPARATORE, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("errore durante la modifica del noleggio");
		}

	}

	@Override // ALL NOLEGGI
	public List<Noleggio> findAllNoleggi() throws BusinessException {
		try {
			FileData filedata = Utility.readAllRows("noleggi");
			List<Noleggio> listanoleggi = new ArrayList<Noleggio>();

			for (String[] colonne : filedata.getRighe()) {
				Noleggio noleggio = new Noleggio();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

				noleggio.setID(Integer.parseInt(colonne[0]));
				noleggio.setDataInizioNoleggio(LocalDate.parse(colonne[1], formatter));
				noleggio.setDataFineNoleggio(LocalDate.parse(colonne[2], formatter));
				noleggio.setPrezzo_noleggio(Double.parseDouble(colonne[3]));
				noleggio.setKmPercorsi(Integer.parseInt(colonne[4]));
				noleggio.setCliente((Cliente) utenteservice.findUtenteById(Integer.parseInt(colonne[5])));
				noleggio.setVeicolo(veicoloservice.findVeicoloByTarga(colonne[6]));
				noleggio.setDettagli(colonne[7]);
				if(colonne[8]!="-") {
					System.out.println(colonne[8]);
					noleggio.setListaAssistenza(assistenzaservice.findAssistenzeByIds(Utility.readList(colonne[8])));
				}

				listanoleggi.add(noleggio);
			}
			return listanoleggi;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Errore in findAllNoleggi");
		}
	}

	@Override // TROVA NOLEGGIO BY ID
	public Noleggio findNoleggioByID(Integer ID) throws BusinessException {

		for (Noleggio tmp : findAllNoleggi()) {
			if (tmp.getID() == ID) {
				return tmp;
			}
		}
		throw new NoleggioNonTrovatoException();
	}

	@Override // LISTA NOLEGGI IN CORSO
	public List<Noleggio> findNoleggiInCorso() throws BusinessException {
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();

		for (Noleggio tmp : findAllNoleggi()) {
			if (tmp.getDataInizioNoleggio().isBefore(LocalDate.now())
					&& tmp.getDataFineNoleggio().isAfter(LocalDate.now())) {
				listacompleta.add(tmp);
			}
		}
		return listacompleta;
	}

	@Override // LISTA NOLEGGI UTENTE
	public List<Noleggio> findNoleggiByUtente(Utente utente) throws BusinessException {
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();

		for (Noleggio tmp : findAllNoleggi()) {
			try {
				if (tmp.getCliente().getID() == utente.getID()) {
					listacompleta.add(tmp);
				}
			} catch (NullPointerException e) {
				continue;
			}
		}
		return listacompleta;
	}

	@Override // LISTA NOLEGGI DATA FINE
	public List<Noleggio> findNoleggiByDataFine(LocalDate data_fine) throws BusinessException {
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();

		for (Noleggio tmp : findAllNoleggi()) {
			if (tmp.getDataFineNoleggio().isEqual(data_fine)) {
				listacompleta.add(tmp);
			}
		}
		return listacompleta;
	}

	@Override // LISTA NOLEGGI NON PAGATI
	public List<Noleggio> findNoleggiByPagamento() throws BusinessException {
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();

		for (Noleggio tmp : findAllNoleggi()) {
			if (tmp.getPrezzo_noleggio() == 0 && tmp.getDataFineNoleggio().isBefore(LocalDate.now())) {
				listacompleta.add(tmp);
			}
		}
		return listacompleta;
	}

	@Override // NOLEGGI BY DETTAGLI
	public List<Noleggio> findNoleggiByDettagli() throws BusinessException {
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();

		for (Noleggio tmp : findAllNoleggi()) {
			if (tmp.getDettagli() == null) {
				listacompleta.add(tmp);
			}
		}
		return listacompleta;
	}

	@Override // CHECK STATO VEICOLI
	public void checkStatoVeicoli() throws BusinessException {
		for (Noleggio tmp : findAllNoleggi()) {
			Veicolo veicolo = new Veicolo();

			veicolo.setTarga(tmp.getVeicolo().getTarga());
			veicolo.setModello(tmp.getVeicolo().getModello());
			veicolo.setChilometri(tmp.getVeicolo().getChilometri());
			veicolo.setTipologia(tmp.getVeicolo().getTipologia());
			veicolo.setAlimentazione(tmp.getVeicolo().getAlimentazione());
			veicolo.setStato(tmp.getVeicolo().getStato());
			veicolo.setTariffaChilometraggio(tmp.getVeicolo().getTariffaChilometraggio());
			veicolo.setTariffaPeriodo(tmp.getVeicolo().getTariffaPeriodo());

			if (tmp.getDataInizioNoleggio().isBefore(LocalDate.now())
					&& tmp.getDataFineNoleggio().isAfter(LocalDate.now())) {
				veicolo.setStato(Stato.IN_NOLEGGIO);
			} else {
				veicolo.setStato(Stato.DISPONIBILE);
			}
			veicoloservice.updateVeicolo(veicolo);
		}
	}

	@Override // VEICOLI DISPONIBILI PER DATA
	public List<Veicolo> findVeicoliDisponibiliForDate(LocalDate datainizio, LocalDate datafine)
			throws BusinessException {
		List<Veicolo> listaveicoli = veicoloservice.findListaVeicoliByStato("tutte");
		List<Veicolo> veicolidaeliminare = new ArrayList<Veicolo>();

		for (Noleggio tmp : findAllNoleggi()) {
			if (datainizio.isAfter(tmp.getDataInizioNoleggio()) && datainizio.isBefore(tmp.getDataFineNoleggio())) {
				veicolidaeliminare.add(tmp.getVeicolo());
				continue;
			}
			if (datafine.isAfter(tmp.getDataInizioNoleggio()) && datafine.isBefore(tmp.getDataFineNoleggio())) {
				veicolidaeliminare.add(tmp.getVeicolo());
				continue;
			}
			if (tmp.getDataInizioNoleggio().isAfter(datainizio) && tmp.getDataInizioNoleggio().isBefore(datafine)) {
				veicolidaeliminare.add(tmp.getVeicolo());
				continue;
			}
			if (tmp.getDataFineNoleggio().isAfter(datainizio) && tmp.getDataFineNoleggio().isBefore(datafine)) {
				veicolidaeliminare.add(tmp.getVeicolo());
				continue;
			}
		}

		for (int i = 0; i < listaveicoli.size(); i++) {
			for (int j = 0; j < veicolidaeliminare.size(); j++) {
				if (veicolidaeliminare.get(j).getTarga() == listaveicoli.get(i).getTarga()) {
					listaveicoli.remove(i);
				}
			}
		}
		return listaveicoli;
	}

	@Override // DISPONIBILITA DATE
	public boolean disponibilitaDate(LocalDate inizio, LocalDate fine, Veicolo veicolo) throws BusinessException {

		for (Noleggio tmp : findAllNoleggi()) {
			if (tmp.getVeicolo().getTarga() == veicolo.getTarga()) {
				if (inizio.isAfter(tmp.getDataInizioNoleggio()) && inizio.isBefore(tmp.getDataFineNoleggio())) {
					return false;
				}

				if (fine.isAfter(tmp.getDataInizioNoleggio()) && fine.isBefore(tmp.getDataFineNoleggio())) {
					return false;
				}

				if (tmp.getDataInizioNoleggio().isAfter(inizio) && tmp.getDataInizioNoleggio().isBefore(fine)) {
					return false;
				}

				if (tmp.getDataFineNoleggio().isAfter(inizio) && tmp.getDataFineNoleggio().isBefore(inizio)) {
					return false;
				}
			}
		}
		return true;
	}
}
