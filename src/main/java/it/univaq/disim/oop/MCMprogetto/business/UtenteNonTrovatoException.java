package it.univaq.disim.oop.MCMprogetto.business;

public class UtenteNonTrovatoException extends BusinessException
{

	public UtenteNonTrovatoException() {
		super();
	}

	public UtenteNonTrovatoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UtenteNonTrovatoException(String message, Throwable cause) {
		super(message, cause);
	}

	public UtenteNonTrovatoException(String message) {
		super(message);
	}

	public UtenteNonTrovatoException(Throwable cause) {
		super(cause);
	}

}
