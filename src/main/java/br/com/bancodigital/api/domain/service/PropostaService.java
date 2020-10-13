package br.com.bancodigital.api.domain.service;

import br.com.bancodigital.api.domain.exception.DocumentoNaoEncontradoException;
import br.com.bancodigital.api.domain.exception.PropostaNaoEncontradaException;
import br.com.bancodigital.api.domain.model.Documento;
import br.com.bancodigital.api.domain.model.Proposta;
import br.com.bancodigital.api.domain.model.enums.StatusProposta;
import br.com.bancodigital.api.domain.repository.Documentos;
import br.com.bancodigital.api.domain.repository.Propostas;
import br.com.bancodigital.api.model.AceiteModel;
import br.com.bancodigital.api.model.DocumentoModel;
import br.com.bancodigital.api.model.PropostaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropostaService {

    @Autowired
    private Propostas propostas;

    @Autowired
    private Documentos documentos;

    public PropostaModel porId(Long id) {
        var proposta = buscarPorId(id);
        var propostaModel = toPropostaModel(proposta);

        return propostaModel;
    }

    private Proposta buscarPorId(Long id) {
        return propostas.findById(id)
                .orElseThrow(() -> new PropostaNaoEncontradaException("Proposta não localizada e/ou não foi iniciado um cadastro de cliente."));
    }

    private PropostaModel toPropostaModel(Proposta proposta) {
        var documentoModel = toDocumentoModel(proposta);
        return PropostaModel.builder()
                .id(proposta.getId())
                .tipo(proposta.getTipo())
                .status(proposta.getStatus())
                .cliente(proposta.getCliente())
                .data(proposta.getData())
                .documentoModel(documentoModel)
                .build();
    }

    private DocumentoModel toDocumentoModel(Proposta proposta) {
        var documento = documentos.findByProposta(proposta)
                .orElseThrow(() -> new DocumentoNaoEncontradoException("Documento não localizado. Favor enviar CPF"));

        return DocumentoModel.builder()
                .nome(documento.getNome())
                .formato(documento.getFormato())
                .arquivoBase64(documento.getArquivoBase64())
                .build();
    }

}
