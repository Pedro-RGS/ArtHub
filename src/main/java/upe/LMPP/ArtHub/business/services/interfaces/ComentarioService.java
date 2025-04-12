package upe.LMPP.ArtHub.business.services.interfaces;

import upe.LMPP.ArtHub.controller.DTO.comentario.ComentarioCriadoDTO;
import upe.LMPP.ArtHub.controller.DTO.comentario.ComentarioDTO;
import upe.LMPP.ArtHub.infra.entities.Comentario;

import java.util.List;

public interface ComentarioService {

    ComentarioDTO publicarComentario(ComentarioCriadoDTO comentario, Integer idDono, Integer idPublicacao);
    ComentarioDTO curtirComentario(Integer idComentario);
    ComentarioDTO descurtirComentario(Integer idComentario);
    List<ComentarioDTO> listarComentarios(Integer idPublicacao);
    ComentarioDTO buscarPorId(Integer idComentario);
    void removerComentario(Integer idComentario);
}
