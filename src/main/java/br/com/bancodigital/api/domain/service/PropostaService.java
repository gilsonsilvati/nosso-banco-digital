package br.com.bancodigital.api.domain.service;

import br.com.bancodigital.api.domain.repository.Propostas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropostaService {

    @Autowired
    private Propostas propostas;

    

}
