package br.com.hub.forum.alura.forumhub_api.infra.exception.usuario;

public class EmailJaCadastradoException extends RuntimeException {
  public EmailJaCadastradoException(String message) {
    super(message);
  }
}
