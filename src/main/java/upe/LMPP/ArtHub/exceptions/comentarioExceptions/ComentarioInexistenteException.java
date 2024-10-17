package upe.LMPP.ArtHub.exceptions.comentarioExceptions;

public class ComentarioInexistenteException extends RuntimeException {
    public ComentarioInexistenteException() {
        super("Comentário não encontrado ou não existente.");
    }
}