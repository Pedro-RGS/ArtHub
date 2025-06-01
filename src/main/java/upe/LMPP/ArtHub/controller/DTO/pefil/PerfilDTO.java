package upe.LMPP.ArtHub.controller.DTO.pefil;

import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioDTO;
import upe.LMPP.ArtHub.infra.entities.Perfil;

public record PerfilDTO(
        Integer id,
        String biografia,
        String fotoPerfil,
        String banner,
        UsuarioDTO usuario
) {

    public static PerfilDTO perfilToDTO(Perfil perfil){
        return new PerfilDTO(
                perfil.getId(),
                perfil.getBiografia(),
                perfil.getFotoPerfil(),
                perfil.getBiografia(),
                UsuarioDTO.UsuarioToDTO(perfil.getUsuario())
        );
    }
}
