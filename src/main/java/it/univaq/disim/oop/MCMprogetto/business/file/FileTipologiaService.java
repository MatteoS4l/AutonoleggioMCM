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
	public void createTipologia(Tipologia tipologia) throws BusinessException {
		if (!findAllTipologia().contains(tipologia.getNome())) {
			try {
				FileData fileData = Utility.readAllRows("tipologie");
				try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "tipologie" + Utility.FORMATO))) {
					int contatore = fileData.getCont();
					writer.println((contatore + 1));

					for (String[] righe : fileData.getRighe()) {
						writer.println(String.join(Utility.SEPARATORE, righe));
					}

					StringBuilder row = new StringBuilder();
					row.append(fileData.getCont());
					row.append(Utility.SEPARATORE);
					row.append(tipologia.getNome());

					writer.println(row.toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException(e);
			}
		} else {
			throw new BusinessException();
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
							/* eliminato */ } else {
							writer.println(String.join(Utility.SEPARATORE, righe));
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException(e);
			}
		} else {
			throw new TipologiaNonTrovataException();
		}

	}

	@Override // GET LISTA TIPOLOGIA
	public List<String> findAllTipologia() throws TipologiaNonTrovataException, BusinessException {
		try {
			List<String> listatipo = new ArrayList<String>();
			FileData filedata = Utility.readAllRows("tipologie");

			for (String[] colonne : filedata.getRighe()) {
				Tipologia tipologia = new Tipologia();

				tipologia.setID(Integer.parseInt(colonne[0]));
				tipologia.setNome(colonne[1]);
				listatipo.add(tipologia.getNome());
			}
			return listatipo;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override // GET LISTA TIPOLOGIA T
	public List<Tipologia> findAllTipologiaT() throws TipologiaNonTrovataException, BusinessException {
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
			throw new BusinessException(e);
		}
	}

}
