
package it.univaq.disim.oop.MCMprogetto.business.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.FeedbackService;
import it.univaq.disim.oop.MCMprogetto.business.UtenteNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloNonTrovatoException;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Feedback;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;


public class FileFeedbackService implements FeedbackService
{// ID, feedback, utente, veicolo, (n° stelle feedback)
	private UtenteService utenteservice = new FileUtenteService();
	private VeicoloService veicoloservice = new FileVeicoloService();

	
	@Override // CREA
	public void createFeedback(Feedback feedback) throws BusinessException
	{	try
		{
			FileData fileData = Utility.readAllRows("feedback");
			try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "feedback" + Utility.FORMATO)))
			{
				int contatore = fileData.getCont();
				writer.println((contatore + 1));

				for (String[] righe : fileData.getRighe())
				{	writer.println(String.join(Utility.SEPARATORE, righe));		}

				StringBuilder row = new StringBuilder();
				row.append(fileData.getCont());
				row.append(Utility.SEPARATORE);
				row.append(feedback.getFeedback().toString().replace(",", " "));
				row.append(Utility.SEPARATORE);
				row.append(feedback.getUtente().getID());
				row.append(Utility.SEPARATORE);
				row.append(feedback.getVeicolo().getID());
				row.append(Utility.SEPARATORE);
				row.append(feedback.getStelle());

				writer.println(row.toString());
			}
		} 
		catch (IOException e) {	
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	
	@Override // GET LISTA FEEDBACK
	public List<Feedback> FindAllFeedback(Veicolo veicolo) throws FeedbackNonTrovatoException, BusinessException
	{	try 
		{
			FileData filedata = Utility.readAllRows("feedback");
			List<Feedback> listacompleta = new ArrayList<Feedback>();

			for (String[] colonne : filedata.getRighe()) 
			{
				if (Integer.parseInt(colonne[3]) == veicolo.getID()) 
				{
					Feedback feedback = new Feedback();

					feedback.setID(Integer.parseInt(colonne[0]));
					feedback.setFeedback(colonne[1]);
					

					try 
					{	feedback.setUtente(utenteservice.FindUtenteById(Integer.parseInt(colonne[2])));	}
					catch (NumberFormatException e)
					{	e.printStackTrace();	} 
					catch (UtenteNonTrovatoException e)
					{	e.printStackTrace();	}

					try 
					{	feedback.setVeicolo(veicoloservice.FindVeicoloById(Integer.parseInt(colonne[3])));	}
					catch (NumberFormatException e) 
					{	e.printStackTrace();	} 
					catch (VeicoloNonTrovatoException e)	
					{	e.printStackTrace();	}
					
					feedback.setStelle(Integer.parseInt(colonne[4]));

					listacompleta.add(feedback);
				}
			}
			return listacompleta;
		} 
		catch (IOException e) {	
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

}
