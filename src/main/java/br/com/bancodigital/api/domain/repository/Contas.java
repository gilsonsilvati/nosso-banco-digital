package br.com.bancodigital.api.domain.repository;

import br.com.bancodigital.api.domain.model.Conta;
import br.com.bancodigital.api.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Contas extends JpaRepository<Conta, Long> {

    Optional<Conta> findByCliente(Cliente cliente);

    Optional<Conta> findByConta(Integer conta);

}
