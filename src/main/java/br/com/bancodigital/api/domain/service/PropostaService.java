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

    public AceiteModel verificarAceitePorId(Long id, Boolean aceite) {
        var proposta = buscarPorId(id);
        proposta.setStatus(aceite ? StatusProposta.APROVADA : StatusProposta.NEGADA);
        propostas.save(proposta);

        var mensagem = proposta.getStatus().equals(StatusProposta.APROVADA) ?
                "Sua conta será criada e um e-mail será enviado com seus dados" : "Que pena! Entraremos em contato em breve.";

        var aceiteModel = AceiteModel.builder()
                .mensagem(mensagem)
                .nomeCliente(proposta.getCliente().getNome())
                .emailCliente(proposta.getCliente().getEmail())
                .status(proposta.getStatus())
                .build();

        return aceiteModel;
    }

    private Proposta buscarPorId(Long id) {
        return propostas.findById(id)
                .orElseThrow(() -> new PropostaNaoEncontradaException("Proposta não localizada e/ou não foi iniciado um cadastro de cliente."));
    }

    private PropostaModel toPropostaModel(Proposta proposta) {
        var documentoModel = toDocumentoModel(proposta);
        var propostaModel = PropostaModel.builder()
                .id(proposta.getId())
                .tipo(proposta.getTipo())
                .status(proposta.getStatus())
                .cliente(proposta.getCliente())
                .data(proposta.getData())
                .documentoModel(documentoModel)
                .build();

        return propostaModel;
    }

    private DocumentoModel toDocumentoModel(Proposta proposta) {
        var documento = documentos.findByProposta(proposta)
                .orElseThrow(() -> new DocumentoNaoEncontradoException("Documento não localizado. Favor enviar CPF"));

        var documentoModel = DocumentoModel.builder()
                .nome(documento.getNome())
                .formato(documento.getFormato())
                .arquivoBase64(documento.getArquivoBase64())
                .build();

        return documentoModel;
    }

}
