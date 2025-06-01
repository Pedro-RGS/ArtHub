package upe.LMPP.ArtHub.infra.exceptions.perfilExceptions;

public class ImagemBannerNaoEncontradaException extends RuntimeException {
    public ImagemBannerNaoEncontradaException() {
        super("A imagem do banner não foi encontrada");
    }
}
