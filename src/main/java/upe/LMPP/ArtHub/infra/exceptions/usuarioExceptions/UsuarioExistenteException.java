package upe.LMPP.ArtHub.infra.exceptions.usuarioExceptions;

public class UsuarioExistenteException extends RuntimeException {
    public UsuarioExistenteException() {
        super("Esse usuário já existe! Tente outro email!\n");
    }
}
