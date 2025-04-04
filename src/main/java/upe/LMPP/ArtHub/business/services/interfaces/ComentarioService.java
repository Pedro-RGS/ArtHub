package upe.LMPP.ArtHub.business.services.interfaces;

import upe.LMPP.ArtHub.infra.entities.Comentario;

import java.util.List;

public interface ComentarioService {

    public Comentario publicarComentario(Comentario comentario, Integer idDono, Integer idPublicacao);
    public void removerComentario(Integer idComentario);
    public Comentario curtirComentario(Integer idComentario);
    public Comentario descurtirComentario(Integer idComentario);
    public List<Comentario> listarComentarios(Integer idPublicacao);
    public Comentario buscarPorId(Integer idComentario);
}
