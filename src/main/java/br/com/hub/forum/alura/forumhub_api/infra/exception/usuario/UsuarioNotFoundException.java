package br.com.hub.forum.alura.forumhub_api.infra.exception.usuario;

public class UsuarioNotFoundException extends RuntimeException {
  public UsuarioNotFoundException(String message) {
    super(message);
  }
}
