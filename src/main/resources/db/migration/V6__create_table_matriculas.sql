CREATE TABLE matriculas (
    id VARCHAR(36) PRIMARY KEY,
    usuario_id VARCHAR(36) NOT NULL,
    curso_id VARCHAR(36) NOT NULL,
    data_matricula TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_conclusao TIMESTAMP NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    progresso DECIMAL(5,2) DEFAULT 0.00,
    avaliacao_aluno INT NULL CHECK (avaliacao_aluno BETWEEN 1 AND 5),
    comentario TEXT,
    CONSTRAINT fk_matricula_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_matricula_curso FOREIGN KEY (curso_id) REFERENCES cursos(id),
    CONSTRAINT uk_matricula_usuario_curso UNIQUE (usuario_id, curso_id)
);
