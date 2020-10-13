package br.com.bancodigital.api.async;

import br.com.bancodigital.api.domain.model.enums.StatusProposta;
import br.com.bancodigital.api.model.AceiteModel;
import lombok.AllArgsConstructor;
import org.springframework.web.context.request.async.DeferredResult;

@AllArgsConstructor
public class ContaRunnable implements Runnable {

    private Long id;
    private Boolean aceite;
    private DeferredResult<AceiteModel> resultado;
    private ContaAsync contaAsync;

    /* Aqui processa a operação demorada */
    @Override
    public void run() {
        AceiteModel aceiteModel = contaAsync.verificarAceite(id, aceite);

        if (!aceiteModel.getStatus().equals(StatusProposta.FINALIZADA)) {
            for (int i = 0; i < 2; i++)
                contaAsync.criarConta(id);
        }

        resultado.setResult(aceiteModel);
    }

}
