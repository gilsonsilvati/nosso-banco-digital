package br.com.bancodigital.api.domain.repository;

import br.com.bancodigital.api.domain.model.Cliente;
import br.com.bancodigital.api.domain.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Propostas extends JpaRepository<Proposta, Long> {

    Optional<Proposta> findByCliente(Cliente cliente);

    Optional<Proposta> findByClienteId(Long id);

}
