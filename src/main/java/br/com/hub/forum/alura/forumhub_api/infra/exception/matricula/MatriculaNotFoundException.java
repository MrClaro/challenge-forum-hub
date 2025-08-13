package br.com.hub.forum.alura.forumhub_api.infra.exception.matricula;

public class MatriculaNotFoundException extends RuntimeException {
  public MatriculaNotFoundException(String message) {
    super(message);
  }
}
