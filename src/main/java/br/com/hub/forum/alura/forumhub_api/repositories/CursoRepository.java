package br.com.hub.forum.alura.forumhub_api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hub.forum.alura.forumhub_api.domain.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, String> {

  boolean existsByNomeIgnoreCase(String nome);

  Page<Curso> findAllByAtivoTrue(Pageable paginacao);

}
