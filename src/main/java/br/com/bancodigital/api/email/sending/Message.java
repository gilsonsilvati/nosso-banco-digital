package br.com.bancodigital.api.email.sending;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Getter @Setter
public class Message {
	
	private String remetente;
	private String assunto;
	private String corpo;

	private List<String> destinatarios;

}
