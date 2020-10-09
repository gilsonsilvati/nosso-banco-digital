package br.com.bancodigital.api.domain.model;

import br.com.bancodigital.api.domain.model.validation.CEP;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Embeddable
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Endereco {

	@NotBlank(message = "CEP é obrigatório")
	@CEP
	private String cep;

	@NotBlank(message = "Logradouro é obrigatório")
	private String logradouro;

	@NotBlank(message = "Número é obrigatório")
	private String numero;

	@NotBlank(message = "Bairro é obrigatório")
	private String bairro;

	private String complemento;

	@NotNull(message = "Cidade é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_cidade")
	private Cidade cidade;

}
