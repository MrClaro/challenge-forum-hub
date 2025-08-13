package br.com.hub.forum.alura.forumhub_api.infra.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.hub.forum.alura.forumhub_api.infra.exception.curso.CursoInativoException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.curso.CursoJaExisteException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.curso.CursoNotFoundException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.matricula.MatriculaCanceladaException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.matricula.MatriculaJaExisteException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.matricula.MatriculaNotFoundException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.resposta.RespostaInativaException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.resposta.RespostaNotFoundException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.topico.TopicoInativoException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.topico.TopicoNotFoundException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.usuario.EmailJaCadastradoException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.usuario.UsuarioInativoException;
import br.com.hub.forum.alura.forumhub_api.infra.exception.usuario.UsuarioNotFoundException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  // =====================================================
  // EXCEÇÕES DE AUTENTICAÇÃO
  // =====================================================
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
    log.warn("Credenciais inválidas fornecidas");
    var error = new ErrorResponse("Credenciais inválidas", "Email ou senha incorretos");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
  }

  @ExceptionHandler(DisabledException.class)
  public ResponseEntity<ErrorResponse> handleUserDisabled(DisabledException ex) {
    log.warn("Tentativa de login com usuário desabilitado");
    var error = new ErrorResponse("Conta desabilitada", "Sua conta foi desabilitada");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
  }
  // =====================================================
  // EXCEÇÕES DE USUÁRIO
  // =====================================================

  @ExceptionHandler(UsuarioNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUsuarioNotFound(UsuarioNotFoundException ex) {
    log.warn("Usuário não encontrado: {}", ex.getMessage());
    var error = new ErrorResponse("Usuário não encontrado", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(EmailJaCadastradoException.class)
  public ResponseEntity<ErrorResponse> handleEmailJaCadastrado(EmailJaCadastradoException ex) {
    log.warn("Email já cadastrado: {}", ex.getMessage());
    var error = new ErrorResponse("Conflito de dados", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(UsuarioInativoException.class)
  public ResponseEntity<ErrorResponse> handleUsuarioInativo(UsuarioInativoException ex) {
    log.warn("Usuário inativo: {}", ex.getMessage());
    var error = new ErrorResponse("Usuário inativo", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  // =====================================================
  // EXCEÇÕES DE CURSO
  // ====================================================

  @ExceptionHandler(CursoJaExisteException.class)
  public ResponseEntity<ErrorResponse> handleCursoJaExiste(CursoJaExisteException ex) {
    log.warn("Curso já cadastrado: {}", ex.getMessage());
    var error = new ErrorResponse("Curso já cadastrado", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(CursoNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleCursoNotFound(CursoNotFoundException ex) {
    log.warn("Curso não encontrado: {}", ex.getMessage());
    var error = new ErrorResponse("Curso não encontrado", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(CursoInativoException.class)
  public ResponseEntity<ErrorResponse> handleCursoInativo(CursoInativoException ex) {
    log.warn("Curso inativo: {}", ex.getMessage());
    var error = new ErrorResponse("Curso inativo", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }
  // =====================================================
  // EXCEÇÕES DE MATRÍCULA
  // ====================================================

  @ExceptionHandler(MatriculaNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleMatriculaNotFound(MatriculaNotFoundException ex) {
    log.warn("Matrícula não encontrada: {}", ex.getMessage());
    var error = new ErrorResponse("Matrícula não encontrada", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(MatriculaCanceladaException.class)
  public ResponseEntity<ErrorResponse> handleMatriculaInativa(MatriculaCanceladaException ex) {
    log.warn("Matrícula cancelada: {}", ex.getMessage());
    var error = new ErrorResponse("Matrícula cancelada", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(MatriculaJaExisteException.class)
  public ResponseEntity<ErrorResponse> handleMatriculaJaExiste(MatriculaJaExisteException ex) {
    log.warn("Matrícula já existe: {}", ex.getMessage());
    var error = new ErrorResponse("Matrícula já existe", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  // =====================================================
  // EXCEÇÕES DE TÓPICOS
  // ====================================================

  @ExceptionHandler(TopicoNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleTopicoNotFound(TopicoNotFoundException ex) {
    log.warn("Tópico não encontrado: {}", ex.getMessage());
    var error = new ErrorResponse("Tópico não encontrado", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(TopicoInativoException.class)
  public ResponseEntity<ErrorResponse> handleTopicoInativo(TopicoInativoException ex) {
    log.warn("Tópico com curso inativo: {}", ex.getMessage());
    var error = new ErrorResponse("Tópico Inativo", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  // =====================================================
  // EXCEÇÕES DE RESPOSTA
  // ====================================================
  @ExceptionHandler(RespostaNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleRespostaNotFound(RespostaNotFoundException ex) {
    log.warn("Resposta não encontrada: {}", ex.getMessage());
    var error = new ErrorResponse("Resposta não encontrada", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(RespostaInativaException.class)
  public ResponseEntity<ErrorResponse> handleRespostaInativa(RespostaInativaException ex) {
    log.warn("Resposta inativa: {}", ex.getMessage());
    var error = new ErrorResponse("Resposta inativa", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  // =====================================================
  // EXCEÇÕES DE GERAIS
  // ====================================================

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
    log.warn("Argumento inválido: {}", ex.getMessage());
    var error = new ErrorResponse("Dados inválidos", ex.getMessage());
    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
    var errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(", "));

    log.error("Erro de validação: {}", errors);
    var errorResponse = new ErrorResponse("Erro de validação", errors);
    return ResponseEntity.badRequest().body(errorResponse);
  }
}
