package br.com.hub.forum.alura.forumhub_api.domain.dto.autenticacao;

public record DadosTokenJWT(String token, String tipo) {

  public DadosTokenJWT(String token) {
    this(token, "Bearer");
  }
}
