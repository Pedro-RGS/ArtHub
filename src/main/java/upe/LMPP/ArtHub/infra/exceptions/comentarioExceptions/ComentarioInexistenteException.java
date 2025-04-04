package upe.LMPP.ArtHub.infra.exceptions.comentarioExceptions;

public class ComentarioInexistenteException extends RuntimeException {
    public ComentarioInexistenteException() {
        super("Comentário não encontrado");
    }
}