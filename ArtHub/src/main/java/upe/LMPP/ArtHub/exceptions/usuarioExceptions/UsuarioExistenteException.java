package upe.LMPP.ArtHub.exceptions.usuarioExceptions;

public class UsuarioExistenteException extends RuntimeException {
    public UsuarioExistenteException(String message) {
        super("Esse usuário já existe! Tente outro email!\n" +
                "Erro:\n" + message);
    }
}
