package br.com.hub.forum.alura.forumhub_api.domain.dto.topico;

import java.time.LocalDateTime;
import java.util.List;

import br.com.hub.forum.alura.forumhub_api.domain.dto.resposta.DadosDetalhamentoResposta;
import br.com.hub.forum.alura.forumhub_api.domain.entity.Topico;
import br.com.hub.forum.alura.forumhub_api.domain.entity.enums.Status;

public record DadosDetalhamentoTopico(
    String id,
    String titulo,
    String mensagem,
    Status status,
    Boolean ativo,
    String autorNome,
    String cursoNome,
    LocalDateTime dataCriacao,
    LocalDateTime dataAtualizacao,
    List<DadosDetalhamentoResposta> respostas) {
  public DadosDetalhamentoTopico(Topico topico) {
    this(
        topico.getId(),
        topico.getTitulo(),
        topico.getMensagem(),
        topico.getStatus(),
        topico.getAtivo(),
        topico.getAutor().getNome(),
        topico.getCurso().getNome(),
        topico.getDataCriacao(),
        topico.getDataAtualizacao(),
        topico.getRespostas() != null ? topico.getRespostas().stream()
            .map(DadosDetalhamentoResposta::new)
            .toList() : List.of());
  }
}
