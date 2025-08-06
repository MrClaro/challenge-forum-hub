package br.com.hub.forum.alura.forumhub_api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.hub.forum.alura.forumhub_api.repositories.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var usuario = usuarioRepository.findByEmail(username);
    if (usuario == null) {
      throw new UsernameNotFoundException("Usuário não encontrado: " + username);
    }
    return usuario;

  }

}
