package br.com.hub.forum.alura.forumhub_api.infra.exception.topico;

public class TopicoInativoException extends RuntimeException {
  public TopicoInativoException(String message) {
    super(message);
  }
}
