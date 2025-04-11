package upe.LMPP.ArtHub.infra.enums;

import lombok.Getter;

@Getter
public enum CategoriaEnum {
    POEMA("Poema"),
    MUSICA("Musica"),
    PINTURA("Pintura"),
    DESENHO("Desenho"),
    ESCULTURA("Escultura"),
    FOTOGRAFIA("Fotografia");

    private final String categoria;

    CategoriaEnum(String categoria) {
        this.categoria = categoria;
    }
}
