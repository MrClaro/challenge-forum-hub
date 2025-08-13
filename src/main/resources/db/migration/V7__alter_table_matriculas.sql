ALTER TABLE matriculas DROP FOREIGN KEY fk_matricula_usuario;
ALTER TABLE matriculas DROP FOREIGN KEY fk_matricula_curso;

ALTER TABLE matriculas 
ADD CONSTRAINT fk_matricula_usuario 
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE;

ALTER TABLE matriculas 
ADD CONSTRAINT fk_matricula_curso 
    FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE;
