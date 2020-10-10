package br.com.bancodigital.api.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FormatoDocumento {

    JPG(".jpg"),
    JPEG(".jpeg"),
    PNG(".png");

    private final String extensao;

}
