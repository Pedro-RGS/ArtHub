package upe.LMPP.ArtHub.infra.exceptions.publicacaoExceptions;

public class ImagemPublicacaoNaoEncontradaException extends RuntimeException {
    public ImagemPublicacaoNaoEncontradaException() {
        super("A imagem da publicação não foi encontrada");
    }
}
