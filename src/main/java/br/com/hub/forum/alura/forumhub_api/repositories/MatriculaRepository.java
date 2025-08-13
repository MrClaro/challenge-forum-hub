package br.com.hub.forum.alura.forumhub_api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hub.forum.alura.forumhub_api.domain.entity.Curso;
import br.com.hub.forum.alura.forumhub_api.domain.entity.Matricula;
import br.com.hub.forum.alura.forumhub_api.domain.entity.Usuario;

public interface MatriculaRepository extends JpaRepository<Matricula, String> {

  Page<Matricula> findAll(Pageable pageable);

  boolean existsByUsuarioAndCurso(Usuario usuario, Curso curso);

}
