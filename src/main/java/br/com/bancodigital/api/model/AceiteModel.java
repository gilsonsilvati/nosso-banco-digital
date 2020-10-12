package br.com.bancodigital.api.model;

import br.com.bancodigital.api.domain.model.enums.StatusProposta;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class AceiteModel {

    private String mensagem;
    private String nomeCliente;
    private String emailCliente;
    private StatusProposta status;

}
