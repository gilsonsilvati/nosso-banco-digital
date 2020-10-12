package br.com.bancodigital.api.model;

import br.com.bancodigital.api.domain.model.Endereco;
import br.com.bancodigital.api.domain.model.enums.TipoPessoa;
import br.com.bancodigital.api.domain.model.validation.annotation.MaiorIdade;
import br.com.bancodigital.api.domain.model.validation.annotation.UnicoCPF;
import br.com.bancodigital.api.domain.model.validation.annotation.UnicoEmail;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteModel {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Sobrenome é obrigatório")
    private String sobrenome;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @UnicoEmail
    private String email;

    @NotBlank(message = "CPF é obrigatório")
    @CPF(message = "CPF inválido")
    @UnicoCPF
    private String cpf;

    @NotNull(message = "Data de nascimento é obrigatório")
    @Past(message = "Data de nascimento inválida")
    @MaiorIdade
    private LocalDate nascimento;

    private TipoPessoa tipoPessoa = TipoPessoa.FISICA;
    private Endereco endereco;

}
