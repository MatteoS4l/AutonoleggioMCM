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
	List<Noleggio> listanoleggi = new ArrayList<Noleggio>();
	private VeicoloService veicoloservice = new RAMVeicoloService();
	private AssistenzaService assistenzaservice = new RAMAssistenzaService();
	
	int cont_noleggi;

	@Override // CREA
	public Noleggio createNoleggio(Noleggio noleggio) throws BusinessException {
		noleggio.setID(cont_noleggi++);
		listanoleggi.add(noleggio);
		return noleggio;
	}

	@Override // MODIFICA
	public void updateNoleggio(Noleggio noleggio) throws BusinessException {
		try {
			for (Noleggio tmp : listanoleggi) {
				if (tmp.getID() == noleggio.getID()) {
					listanoleggi.remove(tmp);
					listanoleggi.add(noleggio);
				}
			}
		} catch (ConcurrentModificationException e) {
			/* errore checked */ }

	}

	@Override // ALL NOLEGGI
	public List<Noleggio> findAllNoleggi() throws BusinessException {
		return listanoleggi;
	}

	@Override	// TROVA NOLEGGIO BY ID
	public Noleggio findNoleggioByID(Integer ID) throws BusinessException
	{
		for(Noleggio tmp : listanoleggi)
		{
			if(tmp.getID() == ID){
				return tmp;
			}
		}
		throw new NoleggioNonTrovatoException();
	}

	@Override // LISTA NOLEGGI IN CORSO
	public List<Noleggio> findNoleggiInCorso() throws BusinessException {
		List<Noleggio> lista = new ArrayList<Noleggio>();
		for (Noleggio tmp : listanoleggi) {
			if (tmp.getDataInizioNoleggio().isBefore(LocalDate.now())
					&& tmp.getDataFineNoleggio().isAfter(LocalDate.now())) {
				lista.add(tmp);
			}
		}
		return lista;
	}

	@Override // LISTA NOLEGGI UTENTE
	public List<Noleggio> findNoleggiByUtente(Utente utente) throws BusinessException {
		List<Noleggio> lista = new ArrayList<Noleggio>();
		for (Noleggio tmp : listanoleggi) {
			if (tmp.getCliente().getID() == utente.getID()) {
				lista.add(tmp);
			}
		}
		return lista;
	}

	@Override // LISTA NOLEGGI NON PAGATI
	public List<Noleggio> findNoleggiByPagamento() throws BusinessException {
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();

		for (Noleggio tmp : listanoleggi) {
			if (tmp.getPrezzoNoleggio() == 0 && tmp.getDataFineNoleggio().isBefore(LocalDate.now())) {
				listacompleta.add(tmp);
			}
		}
		return listacompleta;
	}

	@Override // NOLEGGI BY DETTAGLI
	public List<Noleggio> findNoleggiByDettagli() throws BusinessException {
		List<Noleggio> listacompleta = new ArrayList<Noleggio>();

		for (Noleggio tmp : listanoleggi) {
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

			if ((tmp.getDataInizioNoleggio().isBefore(LocalDate.now())
					|| tmp.getDataInizioNoleggio().isEqual(LocalDate.now()))
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
			/* errore checked */
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
		Noleggio vecchioNoleggio = assistenza.getNoleggio();
		Veicolo veicoloRotto = vecchioNoleggio.getVeicolo();
		for(Assistenza a : vecchioNoleggio.getListaAssistenza()) {
			assistenzaservice.deleteAssistenza(a);
		}
		assistenzaservice.updateAssistenza(assistenza);
		vecchioNoleggio.getListaAssistenza().removeAll(vecchioNoleggio.getListaAssistenza());
		veicoloRotto.setStato(Stato.NON_DISPONIBILE);
		veicoloservice.updateVeicolo(veicoloRotto);
		updateNoleggio(vecchioNoleggio);
		createNoleggio(nuovoNoleggio);

	}

}
