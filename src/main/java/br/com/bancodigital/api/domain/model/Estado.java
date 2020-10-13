package br.com.bancodigital.api.domain.model;

import br.com.bancodigital.api.domain.model.base.EntidadeBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Getter @Setter
public class Estado extends EntidadeBase {

	@NotBlank
	private String nome;

	@NotBlank
	private String sigla;

}
