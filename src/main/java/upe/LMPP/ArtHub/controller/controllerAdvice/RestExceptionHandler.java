package upe.LMPP.ArtHub.controller.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import upe.LMPP.ArtHub.infra.exceptions.comentarioExceptions.ComentarioInexistenteException;
import upe.LMPP.ArtHub.infra.exceptions.perfilExceptions.ImagemBannerNaoEncontradaException;
import upe.LMPP.ArtHub.infra.exceptions.perfilExceptions.ImagemPerfilNaoEncontradaException;
import upe.LMPP.ArtHub.infra.exceptions.publicacaoExceptions.MidiaPublicacaoNaoEncontradaException;
import upe.LMPP.ArtHub.infra.exceptions.perfilExceptions.PerfilInexistenteException;
import upe.LMPP.ArtHub.infra.exceptions.publicacaoExceptions.PublicacaoInexistenteException;
import upe.LMPP.ArtHub.infra.exceptions.publicacaoExceptions.PublicacaoNaoAutoralException;
import upe.LMPP.ArtHub.infra.exceptions.usuarioExceptions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ComentarioInexistenteException.class)
    public ResponseEntity<String> comentarioInexistente(
            ComentarioInexistenteException e
    ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(PerfilInexistenteException.class)
    public ResponseEntity<String> perfilInexistente(
            PerfilInexistenteException e
    ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(PublicacaoInexistenteException.class)
    public ResponseEntity<String> publicacoInexistente(
            PublicacaoInexistenteException e
    ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(PublicacaoNaoAutoralException.class)
    public ResponseEntity<String> publicacaoNaoAutoral(
            PublicacaoNaoAutoralException e
    ){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
    }

    @ExceptionHandler(UsuarioAdministradorInexistenteException.class)
    public ResponseEntity<String> administradorNaoExitente(
            UsuarioAdministradorInexistenteException e
    ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<String> usuarioExistente(
            UsuarioExistenteException e
    ){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(UsuarioInexistenteException.class)
    public ResponseEntity<String> usuarioInexistente(
            UsuarioInexistenteException e
    ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UsuarioNaoAdminException.class)
    public ResponseEntity<String> usuarioNaoAdministrador(
            UsuarioNaoAdminException e
    ){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
    }

    @ExceptionHandler(MidiaPublicacaoNaoEncontradaException.class)
    public ResponseEntity<String> imagemPublicacaoNaoEncontrada(
            MidiaPublicacaoNaoEncontradaException e
    ){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(ImagemPerfilNaoEncontradaException.class)
    public ResponseEntity<String> imagemPublicacaoNaoEncontrada(
            ImagemPerfilNaoEncontradaException e
    ){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(ImagemBannerNaoEncontradaException.class)
    public ResponseEntity<String> imagemPublicacaoNaoEncontrada(
            ImagemBannerNaoEncontradaException e
    ){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(UsuarioNaoLogadoException.class)
    public ResponseEntity<String> usuarioNaoLogado(
            UsuarioNaoLogadoException e
    ){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String message = error.getDefaultMessage();
            String field = error.getField();
            System.out.println("Erro de validação - Campo: " + field + ", Mensagem: " + message);
            errors.put(field, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
