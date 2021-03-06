CREATE TABLE cliente (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    sobrenome VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    nascimento date NOT NULL,
    tipo_pessoa VARCHAR(10) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    cep VARCHAR(15),
    logradouro VARCHAR(50),
    numero VARCHAR(15),
    bairro VARCHAR(50),
    complemento VARCHAR(20),
    id_cidade BIGINT(20),
    FOREIGN KEY (id_cidade) REFERENCES cidade(id)
);
