package br.com.hub.forum.alura.forumhub_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import br.com.hub.forum.alura.forumhub_api.domain.dto.autenticacao.DadosAutenticacao;
import br.com.hub.forum.alura.forumhub_api.domain.entity.Usuario;

@Service
public class AutenticacaoService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  public String autenticar(DadosAutenticacao dados) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(
        dados.email(),
        dados.senha());

    var authentication = authenticationManager.authenticate(authenticationToken);

    var usuario = (Usuario) authentication.getPrincipal();
    return tokenService.gerarToken(usuario);
  }
}
