CREATE TABLE respostas (
    id CHAR(36) NOT NULL DEFAULT(UUID()),
    mensagem TEXT NOT NULL,
    topico_id CHAR(36) NOT NULL,
    autor_id CHAR(36) NOT NULL,
    solucao BOOLEAN DEFAULT FALSE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    data_exclusao TIMESTAMP NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (topico_id) REFERENCES topicos(id),
    FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);
