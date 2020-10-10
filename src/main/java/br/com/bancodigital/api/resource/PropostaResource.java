package br.com.bancodigital.api.resource;

import br.com.bancodigital.api.domain.model.Cliente;
import br.com.bancodigital.api.domain.model.Proposta;
import br.com.bancodigital.api.domain.repository.Propostas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/propostas")
public class PropostaResource {

    @Autowired
    private Propostas propostas;

    @GetMapping
    public List<Proposta> listar() {
        return propostas.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proposta> buscarPorId(@PathVariable Long id) {
        return propostas.findById(id)
                .map(proposta -> ResponseEntity.ok(proposta))
                .orElse(ResponseEntity.notFound().build());
    }

}
