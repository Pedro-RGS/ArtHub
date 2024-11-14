package upe.LMPP.ArtHub.entities.DTO;

import java.util.Date;

public record UsuarioDTO(String nome,
                         String apelido,
                         String email,
                         Date dataNascimento,
                         String senha) {

}
