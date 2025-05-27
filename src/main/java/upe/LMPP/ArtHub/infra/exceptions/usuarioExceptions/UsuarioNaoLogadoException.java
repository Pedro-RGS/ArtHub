package upe.LMPP.ArtHub.infra.exceptions.usuarioExceptions;

public class UsuarioNaoLogadoException extends RuntimeException {
    public UsuarioNaoLogadoException() {
        super("Você não está logado no sistema");
    }
}
