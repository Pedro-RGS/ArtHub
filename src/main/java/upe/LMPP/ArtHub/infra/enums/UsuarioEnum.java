package upe.LMPP.ArtHub.infra.enums;

import lombok.Getter;

@Getter
public enum UsuarioEnum {
    ADMINISTRADOR("Administrador"),
    COMUM("Comum"),
    ;

    private final String tipoUsuario;

    UsuarioEnum(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

}
