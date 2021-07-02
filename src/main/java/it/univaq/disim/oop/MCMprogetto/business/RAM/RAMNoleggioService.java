package it.univaq.disim.oop.MCMprogetto.business.RAM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;
import it.univaq.disim.oop.MCMprogetto.domain.Stato;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public class RAMNoleggioService implements NoleggioService {
	private final List<Noleggio> listaNoleggi;
	private final VeicoloService veicoloservice;
	private final AssistenzaService assistenzaservice;
	int contNoleggi = 1;

	public RAMNoleggioService(List<Noleggio> listaNoleggi, VeicoloService veicoloservice,
			AssistenzaService assistenzaservice) {
		this.listaNoleggi = listaNoleggi;
		this.veicoloservice = veicoloservice;
		this.assistenzaservice = assistenzaservice;
	}

	@Override // CREA
	public Noleggio createNoleggio(Noleggio noleggio) throws BusinessException {
		noleggio.setID(contNoleggi++);
		listaNoleggi.add(noleggio);
		return noleggio;
	}

	@Override // MODIFICA
	public void updateNoleggio(Noleggio noleggio) throws BusinessException {
		try {
			for (Noleggio tmp : listaNoleggi) {
				if (tmp.getID() == noleggio.getID()) {
					listaNoleggi.remove(tmp);
					listaNoleggi.add(noleggio);
				}
			}
		} catch (ConcurrentModificationException e) {
			System.err.println("ConcurrentModificationException");
		}
	}

	@Override // ALL NOLEGGI
	public List<Noleggio> findAllNoleggi() throws BusinessException {
		return listaNoleggi;
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

	@Override // LISTA NOLEGGI NON PAGATI
	public List<Noleggio> findNoleggiByPagamento() throws BusinessException {
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();

		for (Noleggio tmp : findAllNoleggi()) {
			if (tmp.getPrezzoNoleggio() == 0 && tmp.getDataFineNoleggio().isBefore(LocalDate.now())) {
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
			if (!tmp.getVeicolo().getStato().equals(Stato.NON_DISPONIBILE)) {
				if ((tmp.getDataInizioNoleggio().isBefore(LocalDate.now())
						|| tmp.getDataInizioNoleggio().isEqual(LocalDate.now()))
						&& tmp.getDataFineNoleggio().isAfter(LocalDate.now())) {
					tmp.getVeicolo().setStato(Stato.IN_NOLEGGIO);
				} else {
					tmp.getVeicolo().setStato(Stato.DISPONIBILE);
				}
			}
		}
	}

	@Override // VEICOLI DISPONIBILI PER DATA
	public List<Veicolo> findVeicoliDisponibiliForDate(LocalDate datainizio, LocalDate datafine)
			throws BusinessException {
		List<Veicolo> listaveicoli = veicoloservice.findAllVeicoli();
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
		try {
			for (Veicolo v1 : listaveicoli) {
				for (Veicolo v2 : veicolidaeliminare) {
					if (v1.getTarga().equalsIgnoreCase(v2.getTarga())) {
						listaveicoli.remove(v1);
					}
				}
			}
		} catch (ConcurrentModificationException e) {
			System.err.println("ConcurrentModificationException");
		}
		return listaveicoli;
	}

	@Override // DISPONIBILITA DATE
	public boolean disponibilitaDate(LocalDate inizio, LocalDate fine, Veicolo veicolo) throws BusinessException {

		for (Noleggio tmp : findAllNoleggi()) {
			if (tmp.getVeicolo().getTarga().equalsIgnoreCase(veicolo.getTarga())) {
				if (inizio.isAfter(tmp.getDataInizioNoleggio()) && inizio.isBefore(tmp.getDataFineNoleggio())) {
					return false;
				}
				if (fine.isAfter(tmp.getDataInizioNoleggio()) && fine.isBefore(tmp.getDataFineNoleggio())) {
					return false;
				}
				if (tmp.getDataInizioNoleggio().isAfter(inizio) && tmp.getDataInizioNoleggio().isBefore(fine)) {
					return false;
				}
				if (tmp.getDataFineNoleggio().isAfter(inizio) && tmp.getDataFineNoleggio().isBefore(fine)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override // RISPOSTA ASSISTENZA
	public void rispostaAssistenzaByNoleggio(Noleggio nuovoNoleggio, Assistenza assistenza) throws BusinessException {
		for (Assistenza a : assistenza.getNoleggio().getListaAssistenza()) {
			assistenzaservice.deleteAssistenza(a);
		}
		assistenza.getNoleggio().getVeicolo().setStato(Stato.NON_DISPONIBILE);
		assistenza.getNoleggio().getListaAssistenza().clear();
		updateNoleggio(assistenza.getNoleggio());
		createNoleggio(nuovoNoleggio);
	}

}
