package br.com.bancodigital.api.model;

import br.com.bancodigital.api.domain.model.Cliente;
import br.com.bancodigital.api.domain.model.enums.StatusProposta;
import br.com.bancodigital.api.domain.model.enums.TipoProposta;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropostaModel {

    private Long id;
    private TipoProposta tipo;
    private StatusProposta status;
    private Cliente cliente;
    private LocalDate data;
    private DocumentoModel documentoModel;

}
