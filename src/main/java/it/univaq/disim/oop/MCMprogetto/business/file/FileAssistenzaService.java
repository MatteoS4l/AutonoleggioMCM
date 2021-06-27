package it.univaq.disim.oop.MCMprogetto.business.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.business.AssistenzaNonTrovataException;
import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;

public class FileAssistenzaService implements AssistenzaService {
	// id, richiesta, risposta, id noleggio
	//private NoleggioService noleggioservice = new FileNoleggioService();

	@Override // CREA
	public Assistenza createAssistenza(Assistenza assistenza) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows("assistenza");
			try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "assistenza" + Utility.FORMATO))) {
				int contatore = fileData.getCont();
				writer.println((contatore + 1));

				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE, righe));
				}

				StringBuilder row = new StringBuilder();
				row.append(fileData.getCont());
				row.append(Utility.SEPARATORE);
				row.append(assistenza.getRichiesta().replace(",", " "));
				row.append(Utility.SEPARATORE);
				row.append(assistenza.getRisposta().replace(",", " "));
//				row.append(Utility.SEPARATORE);
//				row.append(assistenza.getNoleggio().getID());

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
						row.append(fileData.getCont());
						row.append(Utility.SEPARATORE);
						row.append(assistenza.getRichiesta().replace(",", " "));
						row.append(Utility.SEPARATORE);
						row.append(assistenza.getRisposta().replace(",", " "));
//						row.append(Utility.SEPARATORE);
//						row.append(assistenza.getNoleggio().getID());

						writer.println(row.toString());
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

			for (String[] righe : fileData.getRighe()) {
				Assistenza assistenza = new Assistenza();

				assistenza.setID(Integer.parseInt(righe[0]));
				assistenza.setRichiesta(righe[1]);
				assistenza.setRisposta(righe[2]);
				//assistenza.setNoleggio(noleggioservice.findNoleggioByID(Integer.parseInt(righe[3])));

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
		List<Assistenza> lista = findAllAssistenza();
		List<Assistenza> listacompleta = new ArrayList<Assistenza>();

		for (Assistenza tmp : lista) {

			if (servita == true) {
				if (tmp.getRisposta() != null) {
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

	/*@Override // FIND ASSISTENZA BY NOLEGGIO
	public List<Assistenza> findAssistenzaByNoleggio(Noleggio noleggio) throws BusinessException {
		List<Assistenza> lista = new ArrayList<Assistenza>();
		for (Assistenza tmp : findAllAssistenza()) {
			if (tmp.getNoleggio().getID() == noleggio.getID()) {
				lista.add(tmp);
			}
		}
		return lista;
	}*/
	
	@Override
	public List<Assistenza> findAssistenzeByIds(List<Integer> ids) throws BusinessException {
		List<Assistenza> res = new ArrayList<>();
		try{
			FileData fileData = Utility.readAllRows("assistenza");
			for(Integer id: ids){
				for(String[] column : fileData.getRighe()){
					if(Integer.parseInt(column[0]) == id){
						Assistenza a = new Assistenza();
						createAssistenza(a);
						res.add(a);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("error in findCandidatesByIds");
		}
		return res;
	}

}
