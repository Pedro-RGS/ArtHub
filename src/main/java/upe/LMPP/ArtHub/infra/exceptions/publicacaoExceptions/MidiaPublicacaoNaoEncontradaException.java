package upe.LMPP.ArtHub.infra.exceptions.publicacaoExceptions;

public class MidiaPublicacaoNaoEncontradaException extends RuntimeException {
    public MidiaPublicacaoNaoEncontradaException() {
        super("A midia da publicação não foi encontrada");
    }
}
