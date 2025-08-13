package br.com.hub.forum.alura.forumhub_api.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import br.com.hub.forum.alura.forumhub_api.domain.dto.topico.DadosAtualizacaoTopico;
import br.com.hub.forum.alura.forumhub_api.domain.dto.topico.DadosCadastroTopico;
import br.com.hub.forum.alura.forumhub_api.domain.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "topicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String titulo;

  private String mensagem;

  @Enumerated(EnumType.STRING)
  private Status status;

  private Boolean ativo;

  @ManyToOne
  @JoinColumn(name = "autor_id")
  private Usuario autor;

  @ManyToOne
  @JoinColumn(name = "curso_id")
  private Curso curso;

  @OneToMany(mappedBy = "topico", fetch = FetchType.LAZY)
  private List<Resposta> respostas;

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

  public void desativar() {
    this.ativo = false;
    this.dataExclusao = LocalDateTime.now();
  }

  public void reativar() {
    this.ativo = true;
    this.dataExclusao = null;
  }

  public Topico(DadosCadastroTopico dados, Usuario autor, Curso curso) {
    this.titulo = dados.titulo();
    this.mensagem = dados.mensagem();
    this.status = Status.NAO_RESPONDIDO;
    this.ativo = true;
    this.autor = autor;
    this.curso = curso;
  }

  public void atualizar(DadosAtualizacaoTopico dados) {
    if (dados.titulo() != null) {
      this.titulo = dados.titulo();
    }
    if (dados.mensagem() != null) {
      this.mensagem = dados.mensagem();
    }
    if (dados.status() != null) {
      this.status = dados.status();
    }
    if (dados.ativo() != null) {
      this.ativo = dados.ativo();
    }
  }

}
