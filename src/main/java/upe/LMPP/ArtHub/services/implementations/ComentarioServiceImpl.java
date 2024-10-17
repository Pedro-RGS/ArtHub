package upe.LMPP.ArtHub.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upe.LMPP.ArtHub.entities.Comentario;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.exceptions.comentarioExceptions.ComentarioInexistenteException;
import upe.LMPP.ArtHub.exceptions.publicacaoExceptions.PublicacaoInexistenteException;
import upe.LMPP.ArtHub.repositories.ComentarioRepository;
import upe.LMPP.ArtHub.repositories.PublicacaoRepository;
import upe.LMPP.ArtHub.services.interfaces.ComentarioService;

import java.util.List;
import java.util.Optional;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    ComentarioRepository comentarioRepository;
    PublicacaoRepository publicacaoRepository;


    @Override
    public Comentario publicarComentario(Comentario comentario, Publicacao publicacao) {
        Optional<Publicacao> publicacaoExistente = publicacaoRepository.findById(publicacao.getId());

        if(publicacaoExistente.isEmpty()){
            throw new PublicacaoInexistenteException();
        }
        comentario.setPublicacao(publicacao);
        return comentarioRepository.save(comentario);
    }

    @Override
    public void removerComentario(Integer idComentario) {
        if (idComentario == null) {
            throw new ComentarioInexistenteException();
        }
        comentarioRepository.deleteById(idComentario);
    }

    @Override
    public Comentario curtirComentario(Integer idComentario) {
        Optional<Comentario> comentario = comentarioRepository.findById(idComentario);

        if (comentario.isEmpty()) {
            throw new ComentarioInexistenteException();
        }
        Comentario comentarioCurtido = comentario.get();
        comentarioCurtido.setCurtidas(comentarioCurtido.getCurtidas() + 1);
        return comentarioRepository.save(comentarioCurtido);
    }

    @Override
    public List<Comentario> listarComentarios(Integer idPublicacao) {
        return comentarioRepository.findByPublicacao(idPublicacao);
    }

    @Override
    public Comentario buscarPorId(Integer idComentario) {
        Optional<Comentario> comentario = comentarioRepository.findById(idComentario);
        return comentario.orElseThrow(ComentarioInexistenteException::new);
    }
}
