package br.com.bancodigital.api.model.util;

import br.com.bancodigital.api.domain.model.Cliente;
import br.com.bancodigital.api.model.ClienteModel;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapperUtil {

    public static ModelMapper modelMapper = new ModelMapper();

    public static List<ClienteModel> toCollectionModel(List<Cliente> clientes) {
        return clientes.stream()
                .map(cliente -> toModel(cliente))
                .collect(Collectors.toList());
    }

    public static ClienteModel toModel(Cliente cliente) {
        return modelMapper.map(cliente, ClienteModel.class);
    }

    public static Cliente toEntity(ClienteModel clienteInputModel) {
        return modelMapper.map(clienteInputModel, Cliente.class);
    }

}
