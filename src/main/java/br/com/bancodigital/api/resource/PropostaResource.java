package br.com.bancodigital.api.resource;

import br.com.bancodigital.api.async.ContaAsync;
import br.com.bancodigital.api.async.ContaRunnable;
import br.com.bancodigital.api.domain.model.Proposta;
import br.com.bancodigital.api.domain.repository.Propostas;
import br.com.bancodigital.api.domain.service.PropostaService;
import br.com.bancodigital.api.event.RecursoCriadoEvent;
import br.com.bancodigital.api.model.AceiteModel;
import br.com.bancodigital.api.model.PropostaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/propostas")
public class PropostaResource {

    @Autowired
    private Propostas propostas;

    @Autowired
    private PropostaService propostaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private ContaAsync contaAsync;

    @GetMapping
    public List<Proposta> listar() {
        return propostas.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaModel> buscarPorId(@PathVariable Long id, HttpServletResponse response) {
        PropostaModel propostaModel = propostaService.porId(id);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, propostaModel.getId(), "/aceite"));

        return ResponseEntity.ok(propostaModel);
    }

    /* Melhorando a disponibilidade da aplicação - retorno assíncrono (DeferredResult<?>) */
    @PutMapping("/{id}/aceite")
    public DeferredResult<AceiteModel> teste(@PathVariable Long id, @RequestBody Boolean aceite) {
        DeferredResult<AceiteModel> resultado = new DeferredResult<>();

        Thread thread = new Thread(new ContaRunnable(id, aceite, resultado, contaAsync));
        thread.start();

        return resultado;
    }

}
