package br.com.bancodigital.api.resource;

import br.com.bancodigital.api.domain.model.Cliente;
import br.com.bancodigital.api.domain.model.Documento;
import br.com.bancodigital.api.domain.model.Endereco;
import br.com.bancodigital.api.domain.repository.Clientes;
import br.com.bancodigital.api.domain.service.ClienteService;
import br.com.bancodigital.api.event.RecursoCriadoEvent;
import br.com.bancodigital.api.model.ClienteModel;
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
    private Clientes clientes;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<ClienteModel> listar() {
        return clienteService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return clientes.findById(id)
                .map(cliente -> ResponseEntity.ok(cliente))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteModel> adicionar(@Valid @RequestBody ClienteModel clienteInputModel, HttpServletResponse response) {
        ClienteModel clienteModel = clienteService.salvar(clienteInputModel);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteModel.getId(), "/{id}/endereco"));

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteModel);
    }

    @PutMapping("/{id}/endereco")
    public ResponseEntity<ClienteModel> adicionarEndereco(@PathVariable Long id, @Valid @RequestBody Endereco endereco) {
        ClienteModel clienteModel = clienteService.salvarEndereco(id, endereco);
        // TODO: Location -> /{id}/documento

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteModel);
    }

    @PostMapping("/{id}/documento")
    public ResponseEntity<Void> adicionarDocumento(@PathVariable Long id, @Valid @RequestBody Documento documento) {
        clienteService.salvarDocumento(id, documento);
        // TODO: Location -> /api/v1/propostas/{id}

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
