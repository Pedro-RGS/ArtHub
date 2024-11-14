package upe.LMPP.ArtHub.exceptions.usuarioExceptions;

public class UsuarioInexistenteException extends RuntimeException {
    public UsuarioInexistenteException() {
        super("Não encontramos você em nossos registros! Tente Novamente!\n");
    }
}
