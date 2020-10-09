package br.com.bancodigital.api.domain.repository;

import br.com.bancodigital.api.domain.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long> { }
