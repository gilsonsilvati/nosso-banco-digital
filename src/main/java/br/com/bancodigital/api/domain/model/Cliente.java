package br.com.bancodigital.api.domain.model;

import br.com.bancodigital.api.domain.model.base.EntidadeBase;
import br.com.bancodigital.api.domain.model.enums.TipoPessoa;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@DynamicUpdate
public class Cliente extends EntidadeBase {

	private String nome;
	private String sobrenome;
	private String email;
	private String cpf;
	private LocalDate nascimento;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pessoa")
	private TipoPessoa tipoPessoa;

	@Embedded
	private Endereco endereco;

	@PrePersist @PreUpdate
	private void prePersistPreUpdate() {
		cpf = TipoPessoa.removerFormatacao(cpf);
	}

	@PostPersist @PostUpdate
	private void postPersistPostUpdate() {
		cpf = tipoPessoa.formatar(cpf);
	}
	
	@PostLoad
	private void postLoad() {
		cpf = tipoPessoa.formatar(cpf);
	}

}
