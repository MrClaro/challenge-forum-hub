package br.com.hub.forum.alura.forumhub_api.infra.exception.curso;

public class CursoNotFoundException extends RuntimeException {
  public CursoNotFoundException(String message) {
    super(message);
  }

}
