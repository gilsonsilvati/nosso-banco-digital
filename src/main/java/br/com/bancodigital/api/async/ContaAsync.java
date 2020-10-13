package br.com.bancodigital.api.async;

import br.com.bancodigital.api.model.AceiteModel;

public interface ContaAsync {

    AceiteModel verificarAceite(Long id, Boolean aceite);

    void criarConta(Long id);

}
