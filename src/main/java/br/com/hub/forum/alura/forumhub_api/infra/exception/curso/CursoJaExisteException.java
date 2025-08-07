package br.com.hub.forum.alura.forumhub_api.infra.exception.curso;

public class CursoJaExisteException extends RuntimeException {
  public CursoJaExisteException(String message) {
    super(message);
  }
}
