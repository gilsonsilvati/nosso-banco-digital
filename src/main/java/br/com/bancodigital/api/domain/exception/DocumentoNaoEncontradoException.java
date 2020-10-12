package br.com.bancodigital.api.domain.exception;

public class DocumentoNaoEncontradoException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public DocumentoNaoEncontradoException(String message) {
		super(message);
	}

}
