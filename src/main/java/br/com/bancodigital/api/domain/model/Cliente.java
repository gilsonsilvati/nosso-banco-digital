package br.com.bancodigital.api.domain.model;

import br.com.bancodigital.api.domain.model.base.EntidadeBase;
import br.com.bancodigital.api.domain.model.enums.TipoPessoa;
import br.com.bancodigital.api.domain.model.validation.ClienteGroupSequenceProvider;
import br.com.bancodigital.api.domain.model.validation.group.CnpjGroup;
import br.com.bancodigital.api.domain.model.validation.group.CpfGroup;
import br.com.brewer.model.Endereco;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "cliente")
@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
@Getter @Setter
public class Cliente extends EntidadeBase {

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	@NotBlank(message = "Sobrenome é obrigatório")
	private String sobrenome;
	
	@NotBlank(message = "E-mail é obrigatório")
	@Email(message = "E-mail inválido")
	private String email;

	@NotNull(message = "Data de nascimento é obrigatório")
	private LocalDate nascimento;

	@NotNull(message = "Tipo pessoa é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pessoa")
	private TipoPessoa tipoPessoa;

	@NotBlank(message = "CPF/CNPJ é obrigatório")
	@CPF(groups = CpfGroup.class)
	@CNPJ(groups = CnpjGroup.class)
	@Column(name = "cpf_cnpj")
	private String cpfOuCnpj;

	@Embedded
	private Endereco endereco;

	public String getCpfOuCnpjSemFormatacao() {
		return TipoPessoa.removerFormatacao(cpfOuCnpj);
	}
	
	@PrePersist @PreUpdate
	private void prePersistPreUpdate() {
		cpfOuCnpj = getCpfOuCnpjSemFormatacao();
	}
	
	@PostLoad
	private void postLoad() {
		cpfOuCnpj = tipoPessoa.formatar(cpfOuCnpj);
	}

}
