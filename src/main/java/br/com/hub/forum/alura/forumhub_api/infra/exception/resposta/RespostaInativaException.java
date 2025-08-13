package br.com.hub.forum.alura.forumhub_api.infra.exception.resposta;

public class RespostaInativaException extends RuntimeException {
  public RespostaInativaException(String message) {
    super(message);
  }
}
