package br.com.bancodigital.api.resource.model;

import br.com.bancodigital.api.domain.model.Endereco;
import br.com.bancodigital.api.domain.model.enums.TipoPessoa;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter @Setter
@JsonInclude(Include.NON_NULL)
public class ClienteModel {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Sobrenome é obrigatório")
    private String sobrenome;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotNull(message = "Data de nascimento é obrigatório")
    private LocalDate nascimento;

    private TipoPessoa tipoPessoa = TipoPessoa.FISICA;

    @NotBlank(message = "CPF é obrigatório")
    @CPF(message = "CPF inválido")
    private String cpf;

    private Endereco endereco;

}
