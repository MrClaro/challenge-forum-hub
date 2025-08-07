ALTER TABLE cursos 
ADD COLUMN duracao_horas INT NOT NULL DEFAULT 0,
ADD COLUMN avaliacao_media DECIMAL(3,2) DEFAULT 0.00,
ADD COLUMN total_avaliacoes INT DEFAULT 0,
ADD COLUMN instrutor_id VARCHAR(36),
MODIFY COLUMN categoria VARCHAR(50) NOT NULL, 
ADD CONSTRAINT fk_curso_instrutor FOREIGN KEY (instrutor_id) REFERENCES usuarios(id);
