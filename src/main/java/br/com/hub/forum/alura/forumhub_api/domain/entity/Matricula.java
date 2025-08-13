package br.com.hub.forum.alura.forumhub_api.domain.entity;

import java.time.LocalDateTime;

import br.com.hub.forum.alura.forumhub_api.domain.dto.matricula.DadosAtualizacaoMatricula;
import br.com.hub.forum.alura.forumhub_api.domain.entity.enums.StatusMatricula;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matriculas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Matricula {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  @ManyToOne
  @JoinColumn(name = "curso_id")
  private Curso curso;

  @Column(name = "data_matricula")
  private LocalDateTime dataMatricula;
  @Column(name = "data_conclusao")
  private LocalDateTime dataConclusao;

  @Enumerated(EnumType.STRING)
  private StatusMatricula status;
  private double progresso;

  @Column(name = "avaliacao_aluno")
  private Integer avaliacaoAluno;
  private String comentario;

  @PrePersist
  protected void onCreate() {
    dataMatricula = LocalDateTime.now();
  }

  public Matricula(Usuario usuario, Curso curso) {
    this.usuario = usuario;
    this.curso = curso;
    this.status = StatusMatricula.ATIVO;
    this.progresso = 0.0;
  }

  public void cancelar() {
    this.status = StatusMatricula.CANCELADO;
  }

  public void reativar() {
    this.status = StatusMatricula.ATIVO;
  }

  public void atualizarDados(@Valid DadosAtualizacaoMatricula dados) {
    if (dados.status() != null) {
      this.status = dados.status();
    }
    if (dados.progresso() > 0 && dados.progresso() < 100) {
      this.progresso = dados.progresso();
    }
    if (dados.avaliacaoAluno() != null) {
      this.avaliacaoAluno = dados.avaliacaoAluno();
    }
    if (dados.comentario() != null) {
      this.comentario = dados.comentario();
    }
    if (dados.progresso() == 100) {
      this.dataConclusao = LocalDateTime.now();
      this.status = StatusMatricula.CONCLUIDO;
      this.progresso = 100.0;
    }
  }
}
