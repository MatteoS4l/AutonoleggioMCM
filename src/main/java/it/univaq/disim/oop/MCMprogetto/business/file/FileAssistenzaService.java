package it.univaq.disim.oop.MCMprogetto.business.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import it.univaq.disim.oop.MCMprogetto.business.AssistenzaNonTrovataException;
import it.univaq.disim.oop.MCMprogetto.business.AssistenzaService;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;
import it.univaq.disim.oop.MCMprogetto.business.UtenteService;
import it.univaq.disim.oop.MCMprogetto.business.VeicoloService;
import it.univaq.disim.oop.MCMprogetto.domain.Assistenza;
import it.univaq.disim.oop.MCMprogetto.domain.Veicolo;

public class FileAssistenzaService implements AssistenzaService
{// id, dettagli, servita(ture,false), risposta, id veicolo, id cliente
	private VeicoloService veicoloservice = new FileVeicoloService();
	private UtenteService utenteservice = new FileUtenteService();
	
	@Override	// CREA
	public int createAssistenza(Assistenza assistenza) throws BusinessException
	{	try
		{
			FileData fileData = Utility.readAllRows("assistenza");
			try (PrintWriter writer = new PrintWriter(new File(Utility.PERCORSO + "assistenza" + Utility.FORMATO)))
			{
				int contatore = fileData.getCont();
				writer.println((contatore + 1));

				for (String[] righe : fileData.getRighe())
				{	writer.println(String.join(Utility.SEPARATORE, righe));		}

				StringBuilder row = new StringBuilder();
				row.append(fileData.getCont());
				row.append(Utility.SEPARATORE);
				row.append(assistenza.getDettagli().toString().replace(",", " "));
				row.append(Utility.SEPARATORE);
				row.append("false");
				row.append(Utility.SEPARATORE);
				row.append("null");
				row.append(Utility.SEPARATORE);
				row.append(assistenza.getVeicolo().getID());
				row.append(Utility.SEPARATORE);
				row.append(assistenza.getCliente().getID());

				writer.println(row.toString());
				
				return fileData.getCont();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	

	@Override	// GET ASSISTENZA ID
	public Assistenza getAssistenzaByID(int id) throws AssistenzaNonTrovataException, BusinessException
	{	
		if(id == 0)
		{	return null;	}
		
		try
		{
			FileData fileData = Utility.readAllRows("assistenza");	
			for(String[] righe : fileData.getRighe())
			{
				if(Integer.parseInt(righe[0]) == id)
				{	Assistenza assistenza = new Assistenza();
				
					assistenza.setID(Integer.parseInt(righe[0]));
					assistenza.setDettagli(righe[1]);
					assistenza.setServita(Boolean.parseBoolean(righe[2]));
					assistenza.setRisposta(righe[3]);
					assistenza.setVeicolo(veicoloservice.getVeicoloById(Integer.parseInt(righe[4])));
					assistenza.setCliente(utenteservice.getUtenteById(Integer.parseInt(righe[5])));

					return assistenza;
				}
			}
			throw new AssistenzaNonTrovataException();
		}
		catch (IOException e) {	
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
