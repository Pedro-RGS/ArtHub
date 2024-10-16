package upe.LMPP.ArtHub.exceptions.usuarioExceptions;

public class UsuarioExistenteException extends RuntimeException {
    public UsuarioExistenteException() {
        super("Esse usuário já existe! Tente outro email!\n" +
                "Erro:\n");
    }
}
