package br.com.hub.forum.alura.forumhub_api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hub.forum.alura.forumhub_api.domain.entity.Topico;

public interface TopicoRepository extends JpaRepository<Topico, String> {

  Page<Topico> findAllByAtivoTrue(Pageable paginacao);

}
