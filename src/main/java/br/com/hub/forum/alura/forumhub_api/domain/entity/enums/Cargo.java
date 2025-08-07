package br.com.hub.forum.alura.forumhub_api.domain.entity.enums;

public enum Cargo {
  ADMIN("admin"),
  USER("user");

  private final String valor;

  Cargo(String valor) {
    this.valor = valor;
  }

  public String getValor() {
    return valor;
  }

  @Override
  public String toString() {
    return valor;
  }
}
