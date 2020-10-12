package br.com.bancodigital.api.domain.repository;

import br.com.bancodigital.api.domain.model.Documento;
import br.com.bancodigital.api.domain.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Documentos extends JpaRepository<Documento, Long> {

    Optional<Documento> findByProposta(Proposta proposta);

}
