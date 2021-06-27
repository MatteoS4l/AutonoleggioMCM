package it.univaq.disim.oop.MCMprogetto.business.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import it.univaq.disim.oop.MCMprogetto.business.BusinessException;

public class Utility {
	public static final String FORMATO = ".txt";
	public static final String PERCORSO = "src/main/resources/dati/";
	public static final String SEPARATORE = ",";

	// metodo per eliminare gli spazi all'inizio e alla fine di una stringa
	// ES. (ciao, come , stai --> ciao,come,stai)
	public static final String[] Elimina_Spazi(String[] s) {
		for (int i = 0; i < s.length; i++) {
			s[i] = s[i].trim();
		}

		return s;
	}

	public static FileData readAllRows(String nomefile) throws IOException {
		FileData result = new FileData();

		try (BufferedReader in = new BufferedReader(new FileReader(PERCORSO + nomefile + FORMATO))) {
			List<String[]> righe = new ArrayList<>();
			int contatore = Integer.parseInt(in.readLine());
			result.setCont(contatore);
			String linea = null;

			while ((linea = in.readLine()) != null) {
				righe.add(Elimina_Spazi(linea.split(SEPARATORE)));
			}

			result.setRighe(righe);
		}

		return result;
	}

	public static List<Integer> readList(String list) throws BusinessException {
		// Iniziamo da 1 perchè il primo elemento della lista sarà "" in quanto
		// nel file utilizziamo un - all'inizio
		List<Integer> res = new ArrayList<>();
		String[] separatedList = list.split("-");
		for (int i = 1; i < separatedList.length; i++) {
			res.add(Integer.parseInt(separatedList[i]));
		}

		return res;
	}

}
