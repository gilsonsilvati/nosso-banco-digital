package br.com.bancodigital.api.domain.service;

import br.com.bancodigital.api.domain.exception.EntidadeNaoEncontradaException;
import br.com.bancodigital.api.domain.exception.NegocioException;
import br.com.bancodigital.api.domain.model.*;
import br.com.bancodigital.api.domain.model.enums.StatusProposta;
import br.com.bancodigital.api.domain.repository.Cidades;
import br.com.bancodigital.api.domain.repository.Clientes;
import br.com.bancodigital.api.domain.repository.Documentos;
import br.com.bancodigital.api.domain.repository.Propostas;
import br.com.bancodigital.api.model.ClienteModel;
import br.com.bancodigital.api.model.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private Clientes clientes;

    @Autowired
    private Cidades cidades;

    @Autowired
    private Propostas propostas;

    @Autowired
    private Documentos documentos;

    public List<ClienteModel> listar() {
        return ModelMapperUtil.toCollectionModel(clientes.findAll(Sort.by("nome")));
    }

    public ClienteModel salvar(ClienteModel clienteModel) {
        var cliente = ModelMapperUtil.toEntity(clienteModel);
        cliente = clientes.save(cliente);

        novaProposta(cliente);

        return ModelMapperUtil.toModel(cliente);
    }

    public ClienteModel salvarEndereco(Long id, Endereco endereco) {
        var cliente = buscarCliente(id);
        Optional<Cidade> cidadeOptional = cidades.findById(endereco.getCidade().getId());
        endereco.setCidade(cidadeOptional.get());
        cliente.setEndereco(endereco);

        return ModelMapperUtil.toModel(clientes.save(cliente));
    }

    public void salvarDocumento(Long id, Documento documento) {
        var cliente = buscarCliente(id);
        Optional<Proposta> propostaOptional = propostas.findByCliente(cliente);

        if (propostaOptional.get().getStatus().equals(StatusProposta.AGUARDANDO))
            throw new NegocioException("Já existe um documento cadastrado para esta proposta");

        documento.setProposta(propostaOptional.get());
        documentos.save(documento);

        atualizarProposta(propostaOptional.get(), StatusProposta.AGUARDANDO);
    }

    private void atualizarProposta(Proposta proposta, StatusProposta statusProposta) {
        proposta.setStatus(statusProposta);
        propostas.save(proposta);
    }

    private Cliente buscarCliente(Long id) {
        var cliente = clientes.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não localizado"));

        return cliente;
    }

    private Proposta novaProposta(Cliente cliente) {
        var proposta = new Proposta();
        proposta.setCliente(cliente);
        proposta = propostas.save(proposta);

        return proposta;
    }

}
