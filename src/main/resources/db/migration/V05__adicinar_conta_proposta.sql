ALTER TABLE proposta
  ADD id_conta BIGINT(20);

ALTER TABLE proposta
  ADD CONSTRAINT `FK4cm1kg523jlopyexjbmi6y54j` FOREIGN KEY (id_conta) REFERENCES conta(id);
