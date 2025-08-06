package br.com.hub.forum.alura.forumhub_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.hub.forum.alura.forumhub_api.domain.dto.usuario.DadosCadastroUsuario;
import br.com.hub.forum.alura.forumhub_api.domain.dto.usuario.DadosDetalhamentoUsuario;
import br.com.hub.forum.alura.forumhub_api.domain.entity.Usuario;
import br.com.hub.forum.alura.forumhub_api.repositories.UsuarioRepository;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public DadosDetalhamentoUsuario cadastrarUsuario(DadosCadastroUsuario dados) {
    var existeUsuario = usuarioRepository.findByEmail(dados.email());
    if (existeUsuario != null) {
      throw new IllegalArgumentException("Usuário já cadastrado com este e-mail: " + dados.email());
    }

    var senhaHasheada = passwordEncoder.encode(dados.senha());

    var usuario = new Usuario(dados, senhaHasheada);
    var usuarioSalvo = usuarioRepository.save(usuario);
    return new DadosDetalhamentoUsuario(usuarioSalvo);
  }

  public boolean validarSenha(String senhaRaw, String senhaHasheada) {
    return passwordEncoder.matches(senhaRaw, senhaHasheada);
  }

}
