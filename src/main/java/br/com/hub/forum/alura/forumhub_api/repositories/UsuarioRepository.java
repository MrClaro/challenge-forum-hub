package br.com.hub.forum.alura.forumhub_api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.hub.forum.alura.forumhub_api.domain.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

  UserDetails findByEmail(String email);

  Page<Usuario> findAllByAtivoTrue(Pageable paginacao);

  boolean existsByEmailAndIdNot(String email, String id);

  boolean existsByEmail(String email);

}
