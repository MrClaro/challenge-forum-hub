package br.com.hub.forum.alura.forumhub_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hub.forum.alura.forumhub_api.domain.dto.usuario.DadosAtualizacaoUsuario;
import br.com.hub.forum.alura.forumhub_api.domain.dto.usuario.DadosCadastroUsuario;
import br.com.hub.forum.alura.forumhub_api.domain.dto.usuario.DadosDetalhamentoUsuario;
import br.com.hub.forum.alura.forumhub_api.domain.entity.Usuario;
import br.com.hub.forum.alura.forumhub_api.infra.exception.usuario.EmailJaCadastradoException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.usuario.UsuarioInativoException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.usuario.UsuarioNotFoundException;
import br.com.hub.forum.alura.forumhub_api.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UsuarioService {
  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public DadosDetalhamentoUsuario cadastrarUsuario(DadosCadastroUsuario dados) {
    if (usuarioRepository.existsByEmail(dados.email())) {
      throw new EmailJaCadastradoException("Usuário já cadastrado com este e-mail: " + dados.email());
    }
    var senhaHasheada = passwordEncoder.encode(dados.senha());
    var usuario = new Usuario(dados, senhaHasheada);
    var usuarioSalvo = usuarioRepository.save(usuario);
    return new DadosDetalhamentoUsuario(usuarioSalvo);
  }

  public Page<DadosDetalhamentoUsuario> listarUsuarios(Pageable paginacao) {
    var usuarios = usuarioRepository.findAllByAtivoTrue(paginacao).map(DadosDetalhamentoUsuario::new);
    return usuarios;
  }

  public DadosDetalhamentoUsuario obterPorId(String id) {
    var usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado: " + id));
    if (!usuario.getAtivo()) {
      throw new UsuarioInativoException("Usuário inativo: " + id);
    }
    return new DadosDetalhamentoUsuario(usuario);
  }

  @Transactional
  public DadosDetalhamentoUsuario atualizarUsuario(String id, @Valid DadosAtualizacaoUsuario dados) {
    var usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado: " + id));
    if (!usuario.getAtivo()) {
      throw new UsuarioInativoException("Usuário inativo: " + id);
    }
    if (dados.email() != null && !dados.email().equals(usuario.getEmail())) {
      if (usuarioRepository.existsByEmailAndIdNot(dados.email(), id)) {
        throw new EmailJaCadastradoException("E-mail já cadastrado: " + dados.email());
      }
    }
    String senhaHasheada = null;
    if (dados.senha() != null && !dados.senha().isBlank()) {
      senhaHasheada = passwordEncoder.encode(dados.senha());
    }
    usuario.atualizarDados(dados, senhaHasheada);
    var usuarioAtualizado = usuarioRepository.save(usuario);
    return new DadosDetalhamentoUsuario(usuarioAtualizado);
  }

  @Transactional
  public void deletarUsuario(String id) {
    var usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado: " + id));
    if (!usuario.getAtivo()) {
      throw new UsuarioInativoException("Usuário já está inativo: " + id);
    }
    usuario.desativar();
    usuarioRepository.save(usuario);
  }

  public boolean validarSenha(String senhaRaw, String senhaHasheada) {
    return passwordEncoder.matches(senhaRaw, senhaHasheada);
  }
}
