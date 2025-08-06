package br.com.hub.forum.alura.forumhub_api.domain.dto.usuario;

import java.time.LocalDateTime;

import br.com.hub.forum.alura.forumhub_api.domain.entity.Usuario;
import br.com.hub.forum.alura.forumhub_api.domain.entity.enums.Cargo;

public record DadosDetalhamentoUsuario(
    String id,
    String nome,
    String email,
    Boolean ativo,
    Cargo cargo,
    LocalDateTime dataCriacao) {
  public DadosDetalhamentoUsuario(Usuario usuario) {
    this(
        usuario.getId(),
        usuario.getNome(),
        usuario.getEmail(),
        usuario.getAtivo(),
        usuario.getCargo(),
        usuario.getDataCriacao());
  }
}
