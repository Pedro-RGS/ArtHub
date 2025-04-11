package upe.LMPP.ArtHub.infra.enums;

import lombok.Getter;

@Getter
public enum TipoArquivoEnum {
    IMAGEM("Imagem"),
    AUDIO("Audio"),
    TEXTO("Texto");

    private final String tipoArquivo;

    TipoArquivoEnum(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }

}
