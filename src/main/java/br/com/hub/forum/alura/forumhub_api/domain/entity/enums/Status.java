package br.com.hub.forum.alura.forumhub_api.domain.entity.enums;

public enum Status {
  ABERTO("aberto"),
  RESOLVIDO("resolvido"),
  FECHADO("fechado");

  private final String valor;

  Status(String valor) {
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
