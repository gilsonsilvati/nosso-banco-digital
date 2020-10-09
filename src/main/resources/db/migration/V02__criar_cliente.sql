CREATE TABLE cliente (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    sobrenome VARCHAR(50) NOT NULL,
    tipo_pessoa VARCHAR(10) NOT NULL,
    cpf_cnpj VARCHAR(14) NOT NULL,
    email VARCHAR(50) NOT NULL,
    logradouro VARCHAR(50),
    numero VARCHAR(15),
    complemento VARCHAR(20),
    cep VARCHAR(15),
    id_cidade BIGINT(20),
    FOREIGN KEY (id_cidade) REFERENCES cidade(id)
);
