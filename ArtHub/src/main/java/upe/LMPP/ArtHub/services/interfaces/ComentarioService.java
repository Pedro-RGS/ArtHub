package upe.LMPP.ArtHub.services.interfaces;

import upe.LMPP.ArtHub.entities.Comentario;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.exceptions.comentarioExceptions.ComentarioExistenteException;
import upe.LMPP.ArtHub.exceptions.comentarioExceptions.ComentarioNotFoundPubliExceptiton;

import java.util.List;

public interface ComentarioService {

    public Comentario publicarComentario(Comentario comentario, Publicacao publicacao) throws ComentarioNotFoundPubliExceptiton;
    public void removerComentario(Comentario comentario) throws ComentarioExistenteException;
    public Comentario curtirComentario(Comentario comentario);
    public List<Comentario> listarComentarios(Publicacao publicacao) throws ComentarioNotFoundPubliExceptiton;



}
