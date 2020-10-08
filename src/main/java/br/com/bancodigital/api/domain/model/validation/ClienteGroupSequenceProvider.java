package br.com.bancodigital.api.domain.model.validation;

import br.com.bancodigital.api.domain.model.Cliente;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<Cliente> {

	@Override
	public List<Class<?>> getValidationGroups(Cliente cliente) {
		List<Class<?>> groups = new ArrayList<>();
		groups.add(Cliente.class);
		
		if (isPessoaSelecionada(cliente)) {
			groups.add(cliente.getTipoPessoa().getGroup());
		}
		
		return groups;
	}

	protected boolean isPessoaSelecionada(Cliente cliente) {
		return cliente != null && cliente.getTipoPessoa() != null;
	}

}
