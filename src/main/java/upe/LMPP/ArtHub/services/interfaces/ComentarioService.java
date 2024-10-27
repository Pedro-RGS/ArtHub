package upe.LMPP.ArtHub.services.interfaces;

import jakarta.persistence.criteria.CriteriaBuilder;
import upe.LMPP.ArtHub.entities.Comentario;
import upe.LMPP.ArtHub.entities.Publicacao;

import java.util.List;

public interface ComentarioService {

    public Comentario publicarComentario(Comentario comentario, Integer idDono, Integer idPublicacao);
    public void removerComentario(Integer idComentario);
    public Comentario curtirComentario(Integer idComentario);
    public List<Comentario> listarComentarios(Integer idPublicacao);
    public Comentario buscarPorId(Integer idComentario);



}
