package it.univaq.disim.oop.MCMprogetto.business.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaNonTrovataException;
import it.univaq.disim.oop.MCMprogetto.business.TipologiaService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Tipologia;

public class FileTipologiaService implements TipologiaService { // ID, nome
	private VeicoloService veicoloservice = new FileVeicoloService();

	@Override // CREA
	public Tipologia createTipologia(Tipologia tipologia) throws BusinessException {
		if (!GiaEsistente(tipologia)) {
			try {
				FileData fileData = Utility.readAllRows("tipologie");
				try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "tipologie" + Utility.FORMATO))) {
					int contatore = fileData.getCont();
					writer.println((contatore + 1));

					for (String[] colonne : fileData.getRighe()) {
						writer.println(String.join(Utility.SEPARATORE, colonne));
					}

					StringBuilder row = new StringBuilder();
					row.append(fileData.getCont());
					row.append(Utility.SEPARATORE);
					row.append(tipologia.getNome());

					writer.println(row.toString());
					return tipologia;
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException(e);
			}
		} else {
			throw new BusinessException("Errore durante la creazione di una tipologia");
		}
	}

	@Override // ELIMINA
	public void deleteTipologia(Tipologia tipologia) throws VeicoloNonTrovatoException, BusinessException {
		if (veicoloservice.findListaVeicoliByTipologia(tipologia.getNome()).isEmpty()) {
			try {
				FileData fileData = Utility.readAllRows("tipologie");
				try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "tipologie" + Utility.FORMATO))) {
					writer.println(fileData.getCont());
					for (String[] righe : fileData.getRighe()) {
						if (Integer.parseInt(righe[0]) == tipologia.getID()) {
							System.err.println("TIPOLOGIA ELIMINATA");
						} else {
							writer.println(String.join(Utility.SEPARATORE, righe));
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException("Errore durante eliminazione tipologia");
			}
		} else {
			throw new TipologiaNonTrovataException();
		}
	}

	@Override // GET LISTA TIPOLOGIA
	public List<Tipologia> findAllTipologia() throws TipologiaNonTrovataException, BusinessException {
		try {
			List<Tipologia> listatipo = new ArrayList<Tipologia>();
			FileData filedata = Utility.readAllRows("tipologie");

			for (String[] colonne : filedata.getRighe()) {
				Tipologia tipologia = new Tipologia();

				tipologia.setID(Integer.parseInt(colonne[0]));
				tipologia.setNome(colonne[1]);
				listatipo.add(tipologia);
			}
			return listatipo;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Errore findAllTipologia");
		}
	}

	// GIA ESISTENTE
	private boolean GiaEsistente(Tipologia tipologia) throws TipologiaNonTrovataException, BusinessException {
		for (Tipologia tmp : findAllTipologia()) {
			if (tmp.getNome().equalsIgnoreCase(tipologia.getNome())) {
				return true;
			}
		}
		return false;
	}

}
