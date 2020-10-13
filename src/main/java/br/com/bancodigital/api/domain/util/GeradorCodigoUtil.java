package br.com.bancodigital.api.domain.util;

import java.util.UUID;

public class GeradorCodigoUtil {

    public static Integer gerar(int tamanho) {
        String codigo = String.valueOf(UUID.randomUUID().getMostSignificantBits());
        codigo = codigo.replace("-", "").substring(0, tamanho);

        return Integer.parseInt(codigo);
    }

}
