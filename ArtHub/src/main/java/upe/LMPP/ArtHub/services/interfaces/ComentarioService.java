package upe.LMPP.ArtHub.services.interfaces;

import upe.LMPP.ArtHub.entities.Comentario;

public interface ComentarioService {

    public Comentario publicarComentario(Comentario comentario);
    public void removerComentario(Comentario comentario);
    public Comentario curtirComentario(Comentario comentario);
    //public List<Comentario> listarComentarios();



}
