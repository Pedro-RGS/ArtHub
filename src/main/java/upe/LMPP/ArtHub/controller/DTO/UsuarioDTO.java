package upe.LMPP.ArtHub.controller.DTO;

import java.util.Date;

// Criar um UsuarioCreationDTO
public record UsuarioDTO(String nome,
                         String apelido,
                         String email,
                         Date dataNascimento,
                         String senha,
                         String telefone) {

}