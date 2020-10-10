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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private ModelMapper modelMapper;

    public List<ClienteModel> listar() {
        return toCollectionModel(clientes.findAll(Sort.by("nome")));
    }

    public ClienteModel salvar(ClienteModel clienteInputModel) {
        Cliente cliente = toEntity(clienteInputModel);
        Optional<Cliente> clienteExistente = clientes.findByCpf(cliente.getCpf());

        validarCliente(cliente, clienteExistente);

        cliente = clientes.save(cliente);
        novaProposta(cliente);

        return toModel(cliente);
    }

    public ClienteModel salvarEndereco(Long id, Endereco endereco) {
        Optional<Cliente> clienteOptional = buscarCliente(id);
        Optional<Cidade> cidadeOptional = cidades.findById(endereco.getCidade().getId());
        endereco.setCidade(cidadeOptional.get());
        clienteOptional.get().setEndereco(endereco);

        return toModel(clientes.save(clienteOptional.get()));
    }

    public void salvarDocumento(Long id, Documento documento) {
        Optional<Cliente> clienteOptional = buscarCliente(id);
        Optional<Proposta> propostaOptional = propostas.findByCliente(clienteOptional.get());

        documento.setProposta(propostaOptional.get());
        documentos.save(documento);

        atualizarProposta(propostaOptional.get(), StatusProposta.AGUARDANDO);
    }

    private void atualizarProposta(Proposta proposta, StatusProposta statusProposta) {
        proposta.setStatus(statusProposta);
        propostas.save(proposta);
    }

    private Optional<Cliente> buscarCliente(Long id) {
        Optional<Cliente> clienteOptional = clientes.findById(id);

        if (clienteOptional.isEmpty())
            throw new EntidadeNaoEncontradaException("Cliente de id " + id + " não localizado.");

        return clienteOptional;
    }

    private void validarCliente(Cliente cliente, Optional<Cliente> clienteExistente) {
        if (clienteExistente.isPresent() && !clienteExistente.get().equals(cliente))
            throw new NegocioException("Já existe um cliente cadastrado com este cpf.");

        clienteExistente = clientes.findByEmail(cliente.getEmail());

        if (clienteExistente.isPresent() && !clienteExistente.get().equals(cliente))
            throw new NegocioException("Já existe um cliente cadastrado com este email.");

        if (!isNascimentoValido(cliente.getNascimento()))
            throw new NegocioException("Data de nascimento inválida.");
    }

    private boolean isNascimentoValido(LocalDate nascimento) {
        LocalDate hoje = LocalDate.now();
        long idade = ChronoUnit.YEARS.between(nascimento, hoje);

        return nascimento.isBefore(hoje) && idade > 18;
    }

    private Proposta novaProposta(Cliente cliente) {
        Proposta proposta = new Proposta();
        proposta.setCliente(cliente);
        proposta = propostas.save(proposta);

        return proposta;
    }

    private List<ClienteModel> toCollectionModel(List<Cliente> clientes) {
        return clientes.stream()
                .map(cliente -> toModel(cliente))
                .collect(Collectors.toList());
    }

    private ClienteModel toModel(Cliente cliente) {
        return modelMapper.map(cliente, ClienteModel.class);
    }

    private Cliente toEntity(ClienteModel clienteInputModel) {
        return modelMapper.map(clienteInputModel, Cliente.class);
    }

}
