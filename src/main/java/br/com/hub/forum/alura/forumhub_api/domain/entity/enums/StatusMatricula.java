package br.com.hub.forum.alura.forumhub_api.domain.entity.enums;

public enum StatusMatricula {
  ATIVO("Ativo"),
  CONCLUIDO("Concluído"),
  CANCELADO("Cancelado"),
  SUSPENSO("Suspenso");

  private final String descricao;

  StatusMatricula(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return descricao;
  }

  @Override
  public String toString() {
    return descricao;
  }
}
