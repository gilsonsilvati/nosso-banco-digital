package br.com.bancodigital.api.domain.repository;

import br.com.bancodigital.api.domain.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Documentos extends JpaRepository<Documento, Long> { }
