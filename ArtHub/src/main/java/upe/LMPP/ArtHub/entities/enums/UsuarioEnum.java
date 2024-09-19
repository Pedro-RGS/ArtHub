package upe.LMPP.ArtHub.entities.enums;

public enum UsuarioEnum {
    ADMINISTRADOR("Administrador"),
    COMUM("Comum"),
    ;

    private final String tipoUsuario;

    UsuarioEnum(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }
}
