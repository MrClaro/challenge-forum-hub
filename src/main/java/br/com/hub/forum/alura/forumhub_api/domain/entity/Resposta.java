package br.com.hub.forum.alura.forumhub_api.domain.entity;

import java.time.LocalDateTime;

import br.com.hub.forum.alura.forumhub_api.domain.dto.resposta.DadosCadastroResposta;
import br.com.hub.forum.alura.forumhub_api.infra.exception.resposta.DadosAtualizacaoResposta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "respostas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String mensagem;

  @ManyToOne
  @JoinColumn(name = "topico_id")
  private Topico topico;

  @ManyToOne
  @JoinColumn(name = "autor_id")
  private Usuario autor;

  private Boolean solucao;

  private Boolean ativo;

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

  public Resposta(String mensagem, Topico topico, Usuario usuario) {
    this.mensagem = mensagem;
    this.topico = topico;
    this.autor = usuario;
    this.ativo = true;
    this.solucao = false;
  }

  public void atualizar(DadosAtualizacaoResposta dados) {
    if (dados.mensagem() != null) {
      this.mensagem = dados.mensagem();
    }
    if (dados.solucao() != null) {
      this.solucao = dados.solucao();
    }
  }

}
