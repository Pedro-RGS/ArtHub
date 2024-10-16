package upe.LMPP.ArtHub.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upe.LMPP.ArtHub.entities.Comentario;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.exceptions.comentarioExceptions.ComentarioExistenteException;
import upe.LMPP.ArtHub.exceptions.comentarioExceptions.ComentarioNotFoundPubliExceptiton;
import upe.LMPP.ArtHub.repositories.ComentarioRepository;
import upe.LMPP.ArtHub.repositories.PublicacaoRepository;
import upe.LMPP.ArtHub.services.interfaces.ComentarioService;

import java.util.List;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    ComentarioRepository comentarioRepository;
    PublicacaoRepository publicacaoRepository;


    @Override
    public Comentario publicarComentario(Comentario comentario, Publicacao publicacao) throws ComentarioNotFoundPubliExceptiton {
        Publicacao publi = publicacaoRepository.findById(publicacao.getId()).orElseThrow(() -> new ComentarioNotFoundPubliExceptiton("A publicação não foi encontrada ou não existe."));
        comentario.setPublicacao(publi);
        return comentarioRepository.save(comentario);
    }

    @Override
    public void removerComentario(Integer idComentario) throws ComentarioExistenteException {
        if(idComentario == null) {
            throw new ComentarioExistenteException("O comentário não foi encontrado ou não existe");
        }
        comentarioRepository.deleteById(idComentario);
    }

    @Override
    public Comentario curtirComentario(Comentario comentario) throws ComentarioExistenteException {
        if(comentario.getId() == null) {
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
