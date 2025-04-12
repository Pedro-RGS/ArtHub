package upe.LMPP.ArtHub.controller.DTO.usuario;

import java.util.Date;

// Criar um UsuarioCreationDTO
public record UsuarioCriadoDTO(
        String nome,
        String apelido,
        String email,
        Date dataNascimento,
        String senha,
        String telefone) {
}