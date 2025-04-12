package upe.LMPP.ArtHub.controller.DTO.pefil;

import upe.LMPP.ArtHub.infra.entities.Perfil;

public record PerfilDTO(
        String biografia,
        String fotoPerfil,
        String banner) {

    public static PerfilDTO toPerfilDTO (Perfil perfil){
        return new PerfilDTO(perfil.getBiografia(), perfil.getFotoPerfil(), perfil.getBiografia());
    }
}
