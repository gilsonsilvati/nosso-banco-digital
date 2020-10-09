package br.com.bancodigital.api.domain.model;

import br.com.bancodigital.api.domain.model.base.EntidadeBase;
import br.com.bancodigital.api.domain.model.enums.TipoPessoa;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cliente")
@Getter @Setter
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

	public String getCpfOuCnpjSemFormatacao() {
		return TipoPessoa.removerFormatacao(cpf);
	}
	
	@PrePersist @PreUpdate
	private void prePersistPreUpdate() {
		cpf = getCpfOuCnpjSemFormatacao();
	}
	
	@PostLoad
	private void postLoad() {
		cpf = tipoPessoa.formatar(cpf);
	}

}
