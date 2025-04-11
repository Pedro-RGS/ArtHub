package upe.LMPP.ArtHub.business.services.interfaces;

import upe.LMPP.ArtHub.infra.entities.Comentario;

import java.util.List;

public interface ComentarioService {

    Comentario publicarComentario(Comentario comentario, Integer idDono, Integer idPublicacao);
    void removerComentario(Integer idComentario);
    Comentario curtirComentario(Integer idComentario);
    Comentario descurtirComentario(Integer idComentario);
    List<Comentario> listarComentarios(Integer idPublicacao);
    Comentario buscarPorId(Integer idComentario);
}
