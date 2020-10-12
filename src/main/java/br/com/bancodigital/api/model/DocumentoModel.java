package br.com.bancodigital.api.model;

import br.com.bancodigital.api.domain.model.enums.FormatoDocumento;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class DocumentoModel {

    private String nome;
    private FormatoDocumento formato;
    private String arquivoBase64;

}
