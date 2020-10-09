package br.com.bancodigital.api.domain.service;

import br.com.bancodigital.api.domain.exception.NegocioException;
import br.com.bancodigital.api.domain.model.Cliente;
import br.com.bancodigital.api.domain.repository.Clientes;
import br.com.bancodigital.api.resource.model.ClienteModel;
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
    private ModelMapper modelMapper;

    public List<ClienteModel> listar() {
        return toCollectionModel(clientes.findAll(Sort.by("nome")));
    }

    public ClienteModel salvar(ClienteModel clienteInputModel) {
        Cliente cliente = toEntity(clienteInputModel);
        Optional<Cliente> clienteExistente = clientes.findByCpf(cliente.getCpfOuCnpjSemFormatacao());

        validarCliente(cliente, clienteExistente);

        return toModel(clientes.save(cliente));
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

}
