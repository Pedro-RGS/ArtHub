package upe.LMPP.ArtHub.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upe.LMPP.ArtHub.entities.Comentario;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.exceptions.comentarioExceptions.ComentarioExistenteException;
import upe.LMPP.ArtHub.exceptions.comentarioExceptions.ComentarioNotFoundPubliExceptiton;
import upe.LMPP.ArtHub.repositories.ComentarioRepository;
import upe.LMPP.ArtHub.services.interfaces.ComentarioService;

import java.util.List;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    ComentarioRepository comentarioRepository;


    @Override
    public Comentario publicarComentario(Comentario comentario, Publicacao publicacao) throws ComentarioNotFoundPubliExceptiton {
        //Publicacao publicacao = publicacaoRepository.findById(publicacao.getId());
        if (publicacao == null) {
            throw new ComentarioNotFoundPubliExceptiton("A publicação não foi encontrada ou não existe.");
        }
        //comentario.setPublicacao(publicacao);
        return comentarioRepository.save(comentario);
    }

    @Override
    public void removerComentario(Comentario comentario) throws ComentarioExistenteException {
        if(comentario == null) {
            throw new ComentarioExistenteException("O comentário não foi encontrado ou não existe");
        }
        comentarioRepository.delete(comentario);
    }

    @Override
    public Comentario curtirComentario(Comentario comentario) throws ComentarioExistenteException {
        if(comentario == null) {
            throw new ComentarioExistenteException("O comentário não foi encontrado ou não existe");
        }
        comentario.setCurtidas(comentario.getCurtidas() + 1);
        return comentarioRepository.save(comentario);
    }

    @Override
    public List<Comentario> listarComentarios(Publicacao publicacao) throws ComentarioNotFoundPubliExceptiton {
        if ((publicacao == null)) {
            throw new ComentarioNotFoundPubliExceptiton("A publicação não foi encontrada ou não existe.");
        }
        return comentarioRepository.findByPublicacao(publicacao);
    }
}
