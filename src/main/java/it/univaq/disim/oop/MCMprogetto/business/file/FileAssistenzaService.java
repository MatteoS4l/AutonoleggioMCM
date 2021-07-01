package it.univaq.disim.oop.MCMprogetto.business.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.MCMprogetto.business.AssistenzaNonTrovataException;
import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;

public class FileAssistenzaService implements AssistenzaService {
	// id, richiesta, risposta, idNoleggio
	

	@Override // CREA
	public Assistenza createAssistenza(Assistenza assistenza) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows("assistenza");
			try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "assistenza" + Utility.FORMATO))) {
				writer.println((fileData.getCont() + 1));

				for (String[] colonne : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE, colonne));
				}

				StringBuilder row = new StringBuilder();
				row.append(fileData.getCont());
				row.append(Utility.SEPARATORE);
				row.append(assistenza.getRichiesta().replace(",", " "));
				row.append(Utility.SEPARATORE);
				if (assistenza.getRisposta() != null) {
					row.append(assistenza.getRisposta().replace(",", " "));
				} else {
					row.append("null");
				}
				row.append(Utility.SEPARATORE);
				row.append(assistenza.getNoleggio().getID());
				assistenza.setID(fileData.getCont());
				writer.println(row.toString());
			}
			return assistenza;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Errore durante la creazione di Assistenza");
		}
	}

	@Override // UPDATE
	public void updateAssistenza(Assistenza assistenza) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows("assistenza");

			try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "assistenza" + Utility.FORMATO))) {
				writer.println(fileData.getCont());
				for (String[] righe : fileData.getRighe()) {
					if (Integer.parseInt(righe[0]) == assistenza.getID()) {
						StringBuilder row = new StringBuilder();
//								assistenza.getID().toString() + Utility.SEPARATORE + 
//								assistenza.getRichiesta().replace(",", " ") + Utility.SEPARATORE +
//								assistenza.getRisposta().replace(",", " ") + Utility.SEPARATORE + 
//								assistenza.getNoleggio().getID();
								
								
						
						row.append(assistenza.getID());
						row.append(Utility.SEPARATORE);
						row.append(assistenza.getRichiesta().replace(",", " "));
						row.append(Utility.SEPARATORE);
						if (assistenza.getRisposta() != null) {
							row.append(assistenza.getRisposta().replace(",", " "));
						} else {
							row.append("null");
						}
						row.append(Utility.SEPARATORE);
						row.append(assistenza.getNoleggio().getID());
						writer.println(row.toString());
					} else {
						writer.println(String.join(Utility.SEPARATORE, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Errore durante update Assistenza");
		}
	}

	@Override // FIND ALL ASSISTENZA
	public List<Assistenza> findAllAssistenza() throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows("assistenza");
			List<Assistenza> lista = new ArrayList<Assistenza>();

			for (String[] colonne : fileData.getRighe()) {
				Assistenza assistenza = new Assistenza();

				assistenza.setID(Integer.parseInt(colonne[0]));
				assistenza.setRichiesta(colonne[1]);
				assistenza.setRisposta(colonne[2]);
				assistenza.setIdNoleggio(Integer.parseInt(colonne[3]));

				lista.add(assistenza);
			}
			return lista;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Errore in findAllAssistenza");
		}
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
						if(a.getRisposta() != null) {
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
