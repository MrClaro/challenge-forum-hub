package br.com.hub.forum.alura.forumhub_api.domain.entity.enums;

public enum Categoria {
  BACKEND("backend"),
  FRONTEND("frontend"),
  FULLSTACK("fullstack"),
  DEVOPS("devops"),
  CIENCIA_DADOS("ciencia_dados"),
  MOBILE("mobile"),
  UX_UI("ux_ui"),
  OUTRO("outro");

  private final String valor;

  Categoria(String valor) {
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
