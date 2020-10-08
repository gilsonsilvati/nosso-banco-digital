package br.com.bancodigital.api.domain.model;

import br.com.bancodigital.api.domain.model.base.EntidadeBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cidade")
@Getter @Setter
public class Cidade extends EntidadeBase {

	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@NotNull(message = "Estado é obrigatório")
	@ManyToOne(fetch = FetchType.LAZY) // Ignora no JPA 1°
	@JoinColumn(name = "codigo_estado")
	@JsonIgnore // Ignora na View 2°
	private Estado estado;
	
	public boolean temEstado() {
		return this.estado != null;
	}

}
