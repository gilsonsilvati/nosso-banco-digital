package br.com.bancodigital.api.domain.exception;

public class NegocioException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NegocioException(String message) {
		super(message);
	}
	
	public NegocioException(String message, Exception exception) {
		super(message, exception);
	}

}