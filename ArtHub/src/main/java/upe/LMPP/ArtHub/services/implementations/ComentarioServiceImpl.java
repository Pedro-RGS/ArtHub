package upe.LMPP.ArtHub.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upe.LMPP.ArtHub.entities.Comentario;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.repositories.ComentarioRepository;
import upe.LMPP.ArtHub.services.interfaces.ComentarioService;

import java.util.List;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    ComentarioRepository comentarioRepository;


    @Override
    public Comentario publicarComentario(Comentario comentario, Publicacao publicacao) {
        //Publicacao publicacao = publicacaoRepository.findById(publicacao);
        //comentario.setPublicacao(publicacao);
        return comentarioRepository.save(comentario);
    }

    @Override
    public void removerComentario(Comentario comentario) {
        comentarioRepository.delete(comentario);
    }

    @Override
    public Comentario curtirComentario(Comentario comentario) {
        comentario.setCurtidas(comentario.getCurtidas() + 1);
        return comentarioRepository.save(comentario);
    }

    @Override
    public List<Comentario> listarComentarios(Publicacao publicacao) {
        return comentarioRepository.findByPublicacao(publicacao);
    }
}
