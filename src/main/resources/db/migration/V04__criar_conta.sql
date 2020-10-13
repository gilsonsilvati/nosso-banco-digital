CREATE TABLE conta (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    agencia INT NOT NULL,
    conta INT NOT NULL,
    codigo_banco INT NOT NULL,
    tipo_conta VARCHAR(10) NOT NULL,
    saldo DECIMAL(10, 2) NOT NULL,
    id_cliente BIGINT(20),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);
