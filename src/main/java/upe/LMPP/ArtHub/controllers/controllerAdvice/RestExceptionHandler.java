package upe.LMPP.ArtHub.controllers.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import upe.LMPP.ArtHub.exceptions.comentarioExceptions.ComentarioInexistenteException;
import upe.LMPP.ArtHub.exceptions.publicacaoExceptions.PublicacaoInexistenteException;
import upe.LMPP.ArtHub.exceptions.publicacaoExceptions.PublicacaoNaoAutoralException;
import upe.LMPP.ArtHub.exceptions.usuarioExceptions.UsuarioExistenteException;
import upe.LMPP.ArtHub.exceptions.usuarioExceptions.UsuarioInexistenteException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ComentarioInexistenteException.class)
    public ResponseEntity<String> comentarioInexistente(
            ComentarioInexistenteException e
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
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
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
}
