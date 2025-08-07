package br.com.hub.forum.alura.forumhub_api.infra.exception.curso;

public class CursoInativoException extends RuntimeException {
  public CursoInativoException(String message) {
    super(message);
  }
}
