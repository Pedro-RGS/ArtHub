package upe.LMPP.ArtHub.exceptions.publicacaoExceptions;

public class PublicacaoInexistenteException extends RuntimeException {
    public PublicacaoInexistenteException() {
        super("Essa publicação não existe");
    }
}
