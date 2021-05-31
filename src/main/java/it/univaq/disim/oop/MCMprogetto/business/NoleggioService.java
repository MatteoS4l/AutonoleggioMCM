package it.univaq.disim.oop.MCMprogetto.business;

import java.util.List;

import it.univaq.disim.oop.MCMprogetto.domain.Noleggio;

public interface NoleggioService 
{
	void createNoleggio(Noleggio noleggio) throws BusinessException;	
	
	void updateNoleggio(Noleggio noleggio) throws NoleggioNonTrovatoException;
	
	void deleteNoleggio(Noleggio noleggio) throws NoleggioNonTrovatoException;
	
	Noleggio FindNoleggio(int ID) throws NoleggioNonTrovatoException;
	
	List<Noleggio> FindAllNoleggi() throws BusinessException;

}
