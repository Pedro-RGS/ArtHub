package upe.LMPP.ArtHub.infra.exceptions.publicacaoExceptions;

public class PublicacaoNaoAutoralException extends RuntimeException {
    public PublicacaoNaoAutoralException() {
        super("Não é possível apagar uma publicação que não é sua");
    }
}
