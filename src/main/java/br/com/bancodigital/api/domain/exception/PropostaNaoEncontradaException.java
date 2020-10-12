package br.com.bancodigital.api.domain.exception;

public class PropostaNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public PropostaNaoEncontradaException(String message) {
		super(message);
	}

}
