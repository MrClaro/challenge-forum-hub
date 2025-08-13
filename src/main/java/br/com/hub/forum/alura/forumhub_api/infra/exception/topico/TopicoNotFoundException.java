package br.com.hub.forum.alura.forumhub_api.infra.exception.topico;

public class TopicoNotFoundException extends RuntimeException {
  public TopicoNotFoundException(String message) {
    super(message);
  }
}
