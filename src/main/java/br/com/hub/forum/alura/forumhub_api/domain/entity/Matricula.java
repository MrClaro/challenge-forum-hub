package br.com.hub.forum.alura.forumhub_api.domain.entity;

import java.time.LocalDateTime;

import br.com.hub.forum.alura.forumhub_api.domain.entity.enums.StatusMatricula;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

  private LocalDateTime dataMatricula;
  private LocalDateTime dataConclusao;

  @Enumerated(EnumType.STRING)
  private StatusMatricula status;
  private double progresso;

  private Integer avaliacaoAluno;
  private String comentario;
}
