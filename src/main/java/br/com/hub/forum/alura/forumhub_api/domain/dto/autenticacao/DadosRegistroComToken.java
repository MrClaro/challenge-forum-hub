package br.com.hub.forum.alura.forumhub_api.domain.dto.autenticacao;

import br.com.hub.forum.alura.forumhub_api.domain.dto.usuario.DadosDetalhamentoUsuario;

public record DadosRegistroComToken(
    DadosDetalhamentoUsuario usuario,
    String token,
    String tipo) {
  public DadosRegistroComToken(DadosDetalhamentoUsuario usuario, String token) {
    this(usuario, token, "Bearer");
  }
}
