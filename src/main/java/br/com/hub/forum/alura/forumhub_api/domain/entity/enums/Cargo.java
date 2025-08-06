package br.com.hub.forum.alura.forumhub_api.domain.entity.enums;

public enum Cargo {
  ADMIN("admin"),
  USER("user");

  private String cargo;

  Cargo(String cargo) {
    this.cargo = cargo;
  }

  public String obterCargo() {
    return cargo;
  }
}
