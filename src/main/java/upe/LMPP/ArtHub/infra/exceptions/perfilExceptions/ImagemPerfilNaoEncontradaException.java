package upe.LMPP.ArtHub.infra.exceptions.perfilExceptions;

public class ImagemPerfilNaoEncontradaException extends RuntimeException {
    public ImagemPerfilNaoEncontradaException() {
        super("A imagem do perfil não foi encontrada");
    }
}
