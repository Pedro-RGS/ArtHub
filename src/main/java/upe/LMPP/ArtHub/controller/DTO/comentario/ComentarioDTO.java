package upe.LMPP.ArtHub.controller.DTO.comentario;

import upe.LMPP.ArtHub.infra.entities.Comentario;
import upe.LMPP.ArtHub.infra.entities.Perfil;

import java.time.LocalDateTime;

public record ComentarioDTO(
        Integer id,
        Integer curtidas,
        LocalDateTime dataPublicacao,
        String conteudo,
        Perfil perfil) {

    public static ComentarioDTO comentarioToDTO(Comentario comentario) {
        return new ComentarioDTO(
                comentario.getId(),
                comentario.getCurtidas(),
                comentario.getDataPublicacao(),
                comentario.getConteudo(),
                comentario.getPerfil()
        );
    }
}
