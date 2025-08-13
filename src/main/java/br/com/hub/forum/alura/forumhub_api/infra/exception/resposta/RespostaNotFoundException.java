package br.com.hub.forum.alura.forumhub_api.infra.exception.resposta;

public class RespostaNotFoundException extends RuntimeException {
  public RespostaNotFoundException(String message) {
    super(message);
  }

}
