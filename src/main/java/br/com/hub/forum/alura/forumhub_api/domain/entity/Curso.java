package br.com.hub.forum.alura.forumhub_api.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import br.com.hub.forum.alura.forumhub_api.domain.dto.curso.DadosAtualizacaoCurso;
import br.com.hub.forum.alura.forumhub_api.domain.dto.curso.DadosCadastroCurso;
import br.com.hub.forum.alura.forumhub_api.domain.entity.enums.Categoria;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cursos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String nome;

  @Enumerated(EnumType.STRING)
  private Categoria categoria;

  private Boolean ativo;

  private int duracaoHoras;
  private double avaliacaoMedia;
  private int totalAvaliacoes;

  @ManyToOne
  @JoinColumn(name = "instrutor_id")
  private Usuario instrutor;

  @OneToMany(mappedBy = "curso")
  private List<Matricula> matriculas;

  @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY)
  private List<Topico> topicos;

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

  public Curso(DadosCadastroCurso dados, Usuario instrutor) {
    this.nome = dados.nome();
    this.categoria = dados.categoria();
    this.ativo = true;
    this.duracaoHoras = dados.duracaoHoras();
    this.avaliacaoMedia = 0.0;
    this.totalAvaliacoes = 0;
    this.instrutor = instrutor;
  }

  public void atualizarDados(DadosAtualizacaoCurso dados, Usuario usuario) {
    if (dados.nome() != null && !dados.nome().isBlank()) {
      this.nome = dados.nome();
    }
    if (dados.categoria() != null) {
      this.categoria = dados.categoria();
    }
    if (dados.duracaoHoras() > 0) {
      this.duracaoHoras = dados.duracaoHoras();
    }
    if (dados.instrutorId() != null && !dados.instrutorId().isBlank()) {
      this.instrutor = usuario;
    }
  }

}
