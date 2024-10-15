package upe.LMPP.ArtHub.services.interfaces;

import upe.LMPP.ArtHub.entities.Comentario;
import upe.LMPP.ArtHub.entities.Publicacao;

import java.util.List;

public interface ComentarioService {

    public Comentario publicarComentario(Comentario comentario, Publicacao publicacao);
    public void removerComentario(Comentario comentario);
    public Comentario curtirComentario(Comentario comentario);
    public List<Comentario> listarComentarios(Publicacao publicacao);



}
