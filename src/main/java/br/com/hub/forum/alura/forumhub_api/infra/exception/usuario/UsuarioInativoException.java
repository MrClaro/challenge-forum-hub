package br.com.hub.forum.alura.forumhub_api.infra.exception.usuario;

public class UsuarioInativoException extends RuntimeException {
  public UsuarioInativoException(String message) {
    super(message);
  }
}
