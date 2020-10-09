package br.com.brewer.model;

import br.com.bancodigital.api.domain.model.Cidade;
import br.com.bancodigital.api.domain.model.Estado;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Embeddable
@Getter @Setter
public class Endereco {

	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;

	@ManyToOne
	@JoinColumn(name = "id_cidade")
	private Cidade cidade;

	@Transient
	private Estado estado;

}
