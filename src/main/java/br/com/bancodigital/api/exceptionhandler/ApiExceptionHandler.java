package br.com.bancodigital.api.exceptionhandler;

import br.com.bancodigital.api.domain.exception.DocumentoNaoEncontradoException;
import br.com.bancodigital.api.domain.exception.EntidadeNaoEncontradaException;
import br.com.bancodigital.api.domain.exception.NegocioException;
import br.com.bancodigital.api.domain.exception.PropostaNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var problema = new Problema(status.value(), OffsetDateTime.now(), ex.getMessage(), new ArrayList<Problema.Campo>());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		var problema = new Problema(status.value(), OffsetDateTime.now(), ex.getMessage(), new ArrayList<Problema.Campo>());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(PropostaNaoEncontradaException.class)
	public ResponseEntity<Object> handlePropostaNaoEncontrada(PropostaNaoEncontradaException ex, WebRequest request) {
		var status = HttpStatus.UNPROCESSABLE_ENTITY;
		var problema = new Problema(status.value(), OffsetDateTime.now(), ex.getMessage(), new ArrayList<Problema.Campo>());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(DocumentoNaoEncontradoException.class)
	public ResponseEntity<Object> handleDocumentoNaoEncontrado(DocumentoNaoEncontradoException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var problema = new Problema(status.value(), OffsetDateTime.now(), ex.getMessage(), new ArrayList<Problema.Campo>());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		var campos = new ArrayList<Problema.Campo>();
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			campos.add(new Problema.Campo(nome, mensagem));
		}
		
		var problema = new Problema(status.value(), OffsetDateTime.now(), "Um ou mais campos estão inválidos. "
				+ "Faça o preenchimento correto e tente novamente.", campos);
		
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}

}
