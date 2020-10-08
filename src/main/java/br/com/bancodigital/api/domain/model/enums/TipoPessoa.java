package br.com.bancodigital.api.domain.model.enums;

import br.com.bancodigital.api.domain.model.validation.group.CnpjGroup;
import br.com.bancodigital.api.domain.model.validation.group.CpfGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoPessoa {
	
	FISICA("Física", "CPF", "000.000.000-00", CpfGroup.class) {
		@Override
		public String formatar(String cpfOuCnpj) {
			return cpfOuCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
		}
	},
	
	JURIDICA("Jurídica", "CNPJ", "00.000.000/0000-00", CnpjGroup.class) {
		@Override
		public String formatar(String cpfOuCnpj) {
			return cpfOuCnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})", "$1.$2.$3/$4-");
		}
	};
	
	private final String descricao;
	private final String documento;
	private final String mascara;
	private final Class<?> group;
	
	public static String removerFormatacao(String cpfOuCnpj) {
		return cpfOuCnpj.replaceAll("\\.|-|/", "");
	}
	
	public abstract String formatar(String cpfOuCnpj);
	
}
