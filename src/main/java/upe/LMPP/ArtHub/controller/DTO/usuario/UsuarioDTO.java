package upe.LMPP.ArtHub.controller.DTO.usuario;

import upe.LMPP.ArtHub.infra.entities.Usuario;
import upe.LMPP.ArtHub.infra.enums.UsuarioEnum;


public record UsuarioDTO(
        Integer id,
        String nome,
        String apelido,
        String email,
        String telefone,
        UsuarioEnum tipoUsuario) {

    public static UsuarioDTO UsuarioToDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getApelido(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getTipoUsuario());
    }
}
