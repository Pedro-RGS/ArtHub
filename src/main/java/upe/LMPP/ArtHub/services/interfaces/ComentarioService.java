package upe.LMPP.ArtHub.services.interfaces;

import upe.LMPP.ArtHub.entities.Comentario;
import upe.LMPP.ArtHub.entities.Publicacao;

import java.util.List;

public interface ComentarioService {

    public Comentario publicarComentario(Comentario comentario, Publicacao publicacao);
    public void removerComentario(Integer idComentario);
    public Comentario curtirComentario(Integer idComentario);
    public List<Comentario> listarComentarios(Integer idPublicacao);
    public Comentario buscarPorId(Integer idComentario);



}
