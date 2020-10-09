package br.com.bancodigital.api.resource;

import br.com.bancodigital.api.domain.service.ClienteService;
import br.com.bancodigital.api.resource.event.RecursoCriadoEvent;
import br.com.bancodigital.api.resource.model.ClienteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<ClienteModel> listar() {
        return clienteService.listar();
    }

    @PostMapping
    public ResponseEntity<ClienteModel> adicionar(@Valid @RequestBody ClienteModel clienteInputModel, HttpServletResponse response) {
        ClienteModel clienteModel = clienteService.salvar(clienteInputModel);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteModel.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteModel);
    }

}
