package it.univaq.disim.oop.MCMprogetto.business;

public class VeicoloNonTrovatoException extends BusinessException
{
	public VeicoloNonTrovatoException() {
		super();
	}

	public VeicoloNonTrovatoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public VeicoloNonTrovatoException(String message, Throwable cause) {
		super(message, cause);
	}

	public VeicoloNonTrovatoException(String message) {
		super(message);
	}

	public VeicoloNonTrovatoException(Throwable cause) {
		super(cause);
	}
}
