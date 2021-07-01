package it.univaq.disim.oop.MCMprogetto.business.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackService;
import it.univaq.disim.oop.MCMprogetto.business.NoleggioService;
import it.univaq.disim.oop.MCMprogetto.domain.Feedback;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public class FileFeedbackService implements FeedbackService {// ID, feedback, stelle, id noleggio
	NoleggioService noleggioservice = new FileNoleggioService();

	@Override // CREA
	public Feedback createFeedback(Feedback feedback) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows("feedback");
			try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "feedback" + Utility.FORMATO))) {
				int contatore = fileData.getCont();
				writer.println((contatore + 1));

				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE, righe));
				}

				StringBuilder row = new StringBuilder();
				row.append(fileData.getCont());
				row.append(Utility.SEPARATORE);
				row.append(feedback.getTesto().toString().replace(",", " "));
				row.append(Utility.SEPARATORE);
				row.append(feedback.getStelle());
				row.append(Utility.SEPARATORE);
				row.append(feedback.getNoleggio().getID());

				writer.println(row.toString());
				return feedback;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	
	@Override
	public void rilasciaFeedback(Feedback feedback) throws BusinessException {
		createFeedback(feedback);
		feedback.getNoleggio().setFeedLasciato(true);
		noleggioservice.updateNoleggio(feedback.getNoleggio());
	}

	@Override // LISTA DI TUTTI I FEEDBACK
	public List<Feedback> findAllFeedback() throws BusinessException {
		try {
			FileData filedata = Utility.readAllRows("feedback");
			List<Feedback> listacompleta = new ArrayList<Feedback>();

			for (String[] colonne : filedata.getRighe()) {
				Feedback feedback = new Feedback();

				feedback.setID(Integer.parseInt(colonne[0]));
				feedback.setTesto(colonne[1]);
				feedback.setStelle(Integer.parseInt(colonne[2]));
				feedback.setNoleggio(noleggioservice.findNoleggioByID(Integer.parseInt(colonne[3])));

				listacompleta.add(feedback);
			}
			return listacompleta;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override // GET LISTA FEEDBACK DEL VEICOLO PASSATO
	public List<Feedback> findAllFeedbackByVeicolo(Veicolo veicolo)
			throws FeedbackNonTrovatoException, BusinessException {
		try {
			List<Feedback> listacompleta = new ArrayList<Feedback>();

			for (Feedback f : findAllFeedback()) {
				if (f.getNoleggio().getVeicolo().getTarga().equalsIgnoreCase(veicolo.getTarga())) {
					listacompleta.add(f);
				}
			}

			return listacompleta;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

}