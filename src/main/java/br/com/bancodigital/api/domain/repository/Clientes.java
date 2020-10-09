package br.com.bancodigital.api.domain.repository;

import br.com.bancodigital.api.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Clientes extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByCpf(String cpf);

}
