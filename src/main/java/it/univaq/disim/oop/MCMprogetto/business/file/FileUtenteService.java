package it.univaq.disim.oop.MCMprogetto.business.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.UtenteNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.domain.Amministratore;
import it.univaq.disim.oop.MCMprogetto.domain.Cliente;
import it.univaq.disim.oop.MCMprogetto.domain.Operatore;
import it.univaq.disim.oop.MCMprogetto.domain.Sesso;
import it.univaq.disim.oop.MCMprogetto.domain.Utente;

public class FileUtenteService implements UtenteService { // nome, cognome, username, password, sesso, dataNascita, eta
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override // AUTENTICAZIONE
	public Utente Autenticazione(String username, String password) throws UtenteNonTrovatoException, BusinessException {
		if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
			Utente admin = new Amministratore();
			admin.setNome("Carmine");
			admin.setCognome("Pittella");
			admin.setUsername(username);
			admin.setPassword(password);
			admin.setSesso(Sesso.UOMO);
			return admin;
		}
		try {
			FileData filedata = Utility.readAllRows("utenti");

			for (String[] colonne : filedata.getRighe()) {
				if (colonne[4].equalsIgnoreCase(username) && colonne[5].equals(password)) {
					Utente utente = null;
					switch (colonne[1]) {
					case "cliente": {
						utente = new Cliente();
					}
						break;

					case "operatore": {
						utente = new Operatore();
					}
						break;

					default:
						throw new BusinessException("Tipo di utente non riconosciuto");
					}
					utente.setID(Integer.parseInt(colonne[0]));
					utente.setNome(colonne[2]);
					utente.setCognome(colonne[3]);
					utente.setUsername(colonne[4]);
					utente.setPassword(colonne[5]);
					utente.setSesso(Sesso.valueOf(colonne[6]));
					utente.setDataNascita(LocalDate.parse(colonne[7], formatter));
					utente.setEta(CalcolaEta(utente.getDataNascita()));

					return utente;
				}
			}
			throw new UtenteNonTrovatoException();
		}

		catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Errore in autenticazione");
		}
	}

	@Override // CREA
	public void createUtente(Utente utente) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows("utenti");
			try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "utenti" + Utility.FORMATO))) {
				int contatore = fileData.getCont();
				writer.println((contatore + 1));

				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE, righe));
				}

				StringBuilder row = new StringBuilder();
				row.append(fileData.getCont());
				row.append(Utility.SEPARATORE);

				if (utente instanceof Cliente) {
					row.append("cliente");
					row.append(Utility.SEPARATORE);
				}
				if (utente instanceof Operatore) {
					row.append("operatore");
					row.append(Utility.SEPARATORE);
				}
				row.append(utente.getNome());
				row.append(Utility.SEPARATORE);
				row.append(utente.getCognome());
				row.append(Utility.SEPARATORE);
				row.append(utente.getUsername());
				row.append(Utility.SEPARATORE);
				row.append(utente.getPassword());
				row.append(Utility.SEPARATORE);
				row.append(utente.getSesso());
				row.append(Utility.SEPARATORE);
				row.append(utente.getDataNascita().toString().replace("T", " "));
				row.append(Utility.SEPARATORE);
				row.append(utente.getEta());

				writer.println(row.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Errore in creazione utente");
		}
	}

	@Override // MODIFICA
	public void updateUtente(Utente utente) throws UtenteNonTrovatoException, BusinessException {
		try {
			FileData fileData = Utility.readAllRows("utenti");
			try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "utenti" + Utility.FORMATO))) {
				writer.println(fileData.getCont());
				for (String[] righe : fileData.getRighe()) {
					if (Integer.parseInt(righe[0]) == utente.getID()) {
						StringBuilder row = new StringBuilder();

						row.append(utente.getID());
						row.append(Utility.SEPARATORE);

						if (utente instanceof Cliente) {
							row.append("cliente");
							row.append(Utility.SEPARATORE);
						}
						if (utente instanceof Operatore) {
							row.append("operatore");
							row.append(Utility.SEPARATORE);
						}
						if (utente instanceof Amministratore) {
							row.append("amministratore");
							row.append(Utility.SEPARATORE);
						}
						row.append(utente.getNome());
						row.append(Utility.SEPARATORE);
						row.append(utente.getCognome());
						row.append(Utility.SEPARATORE);
						row.append(utente.getUsername());
						row.append(Utility.SEPARATORE);
						row.append(utente.getPassword());
						row.append(Utility.SEPARATORE);
						row.append(utente.getSesso());
						row.append(Utility.SEPARATORE);
						row.append(utente.getDataNascita().toString().replace("T", " "));
						row.append(Utility.SEPARATORE);
						row.append(utente.getEta());

						writer.println(row.toString());
					} else {
						writer.println(String.join(Utility.SEPARATORE, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Errore in update utente");
		}
	}

	@Override // ELIMINA
	public void deleteUtente(Utente utente) throws UtenteNonTrovatoException, BusinessException {
		try {
			FileData fileData = Utility.readAllRows("utenti");
			try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "utenti" + Utility.FORMATO))) {
				writer.println(fileData.getCont());
				for (String[] righe : fileData.getRighe()) {
					if (Integer.parseInt(righe[0]) == utente.getID()) {
						System.err.println("Utente eliminato");
					} else {
						writer.println(String.join(Utility.SEPARATORE, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Errore durannte l'eliminazione dell'utente");
		}
	}

	@Override // GET LISTA UTENTI
	public List<Utente> findAllUtenti() throws BusinessException {
		try {
			FileData filedata = Utility.readAllRows("utenti"); // elimina anche gli spazi
			List<Utente> listacompleta = new ArrayList<Utente>();

			for (String[] colonne : filedata.getRighe()) {
				Utente utente = null;
				switch (colonne[1]) {
				case "cliente": {
					utente = new Cliente();
				}
					break;
				case "operatore": {
					utente = new Operatore();
				}
					break;
				default:
					break;
				}
				utente.setID(Integer.parseInt(colonne[0]));
				utente.setNome(colonne[2]);
				utente.setCognome(colonne[3]);
				utente.setUsername(colonne[4]);
				utente.setPassword(colonne[5]);
				utente.setSesso(Sesso.valueOf(colonne[6]));
				utente.setDataNascita(LocalDate.parse(colonne[7], formatter));
				utente.setEta(CalcolaEta(utente.getDataNascita()));

				listacompleta.add(utente);
			}
			return listacompleta;
		}

		catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Errore in ricerca di tutti gli utenti");
		}
	}

	@Override // CALCOLA ETA
	public int CalcolaEta(LocalDate data) throws BusinessException, NullPointerException {
		int eta = 0;
		try {
			eta = LocalDate.now().getYear() - data.getYear();
		} catch (NullPointerException e) {
			throw new NullPointerException();
		}
		if (data.getMonthValue() > LocalDate.now().getMonthValue()) {
			return --eta;
		}
		if (data.getMonthValue() == LocalDate.now().getMonthValue()
				&& data.getDayOfMonth() > LocalDate.now().getDayOfMonth()) {
			return --eta;
		}
		return eta;
	}

	@Override // GET UTENTE BY ID
	public Utente findUtenteById(int id) throws UtenteNonTrovatoException, BusinessException {
		for (Utente tmp : findAllUtenti()) {
			if (tmp.getID() == id) {
				return tmp;
			}
		}
		throw new UtenteNonTrovatoException();
	}
}
