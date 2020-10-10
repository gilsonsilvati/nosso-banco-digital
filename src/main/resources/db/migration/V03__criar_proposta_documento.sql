CREATE TABLE proposta (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    tipo_proposta VARCHAR(15) NOT NULL,
    status_proposta VARCHAR(15) NOT NULL,
    data date,
    id_cliente BIGINT(20),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);

CREATE TABLE documento (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    arquivo_base64 TEXT NOT NULL,
    formato VARCHAR(10) NOT NULL,
    data date,
    id_proposta BIGINT(20),
    FOREIGN KEY (id_proposta) REFERENCES proposta(id)
);
