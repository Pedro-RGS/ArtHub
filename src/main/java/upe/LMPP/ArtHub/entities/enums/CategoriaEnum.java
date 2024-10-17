package upe.LMPP.ArtHub.entities.enums;

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

    public String categoria() {
        return categoria;
    }
}
