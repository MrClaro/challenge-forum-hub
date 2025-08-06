package br.com.hub.forum.alura.forumhub_api.domain.entity.enums;

public enum Status {
  ABERTO,
  RESOLVIDO,
  FECHADO;

  public boolean isFechado() {
    return this == FECHADO;
  }

  public boolean isResolvido() {
    return this == RESOLVIDO;
  }

  public boolean isAberto() {
    return this == ABERTO;
  }

}
