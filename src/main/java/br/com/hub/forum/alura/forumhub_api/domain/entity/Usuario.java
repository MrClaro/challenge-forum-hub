package br.com.hub.forum.alura.forumhub_api.domain.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.hub.forum.alura.forumhub_api.domain.dto.usuario.DadosAtualizacaoUsuario;
import br.com.hub.forum.alura.forumhub_api.domain.dto.usuario.DadosCadastroUsuario;
import br.com.hub.forum.alura.forumhub_api.domain.entity.enums.Cargo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "usuario")
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String nome;
  private String email;
  private String senha;
  private Boolean ativo;

  @Enumerated(EnumType.STRING)
  private Cargo cargo;

  @Column(name = "data_criacao")
  private LocalDateTime dataCriacao;

  @Column(name = "data_atualizacao")
  private LocalDateTime dataAtualizacao;

  @Column(name = "data_exclusao")
  private LocalDateTime dataExclusao;

  @PrePersist
  protected void onCreate() {
    dataCriacao = LocalDateTime.now();
    dataAtualizacao = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    dataAtualizacao = LocalDateTime.now();
  }

  public Usuario(DadosCadastroUsuario dados, String senhaHasheada) {
    this.nome = dados.nome();
    this.email = dados.email();
    this.senha = senhaHasheada;
    this.ativo = true;
    this.cargo = dados.cargo() != null ? dados.cargo() : Cargo.USER;
  }

  public void atualizarDados(DadosAtualizacaoUsuario dados, String senhaHasheada) {
    if (dados.nome() != null && !dados.nome().isBlank()) {
      this.nome = dados.nome();
    }

    if (dados.email() != null && !dados.email().isBlank()) {
      this.email = dados.email();
    }

    if (senhaHasheada != null) {
      this.senha = senhaHasheada;
    }

    if (dados.cargo() != null) {
      this.cargo = dados.cargo();
    }

    if (dados.ativo() != null) {
      this.ativo = dados.ativo();
    }
  }

  public void desativar() {
    this.ativo = false;
    this.dataExclusao = LocalDateTime.now();
  }

  public void reativar() {
    this.ativo = true;
    this.dataExclusao = null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (this.cargo == Cargo.ADMIN)
      return List.of(
          new SimpleGrantedAuthority("ROLE_ADMIN"),
          new SimpleGrantedAuthority("ROLE_USER"));
    else
      return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return senha;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.ativo;
  }

}
