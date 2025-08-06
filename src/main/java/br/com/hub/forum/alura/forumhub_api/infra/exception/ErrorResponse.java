package br.com.hub.forum.alura.forumhub_api.infra.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
    String erro,
    String mensagem,
    LocalDateTime timestamp) {
  public ErrorResponse(String erro, String mensagem) {
    this(erro, mensagem, LocalDateTime.now());
  }
}
