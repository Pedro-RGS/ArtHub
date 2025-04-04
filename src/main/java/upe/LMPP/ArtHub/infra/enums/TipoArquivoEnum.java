package upe.LMPP.ArtHub.infra.enums;

public enum TipoArquivoEnum {
    IMAGEM("Imagem"),
    AUDIO("Audio"),
    TEXTO("Texto");

    private final String tipoArquivo;

    TipoArquivoEnum(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }

    public String getTipoArquivo() {
        return tipoArquivo;
    }
}
