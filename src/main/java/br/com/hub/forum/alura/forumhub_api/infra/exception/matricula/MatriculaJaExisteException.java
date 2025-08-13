package br.com.hub.forum.alura.forumhub_api.infra.exception.matricula;

public class MatriculaJaExisteException extends RuntimeException {
  public MatriculaJaExisteException(String message) {
    super(message);
  }
}
