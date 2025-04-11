package upe.LMPP.ArtHub.business.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upe.LMPP.ArtHub.business.services.interfaces.PerfilService;
import upe.LMPP.ArtHub.infra.entities.Comentario;
import upe.LMPP.ArtHub.infra.exceptions.comentarioExceptions.ComentarioInexistenteException;
import upe.LMPP.ArtHub.infra.repositories.ComentarioRepository;
import upe.LMPP.ArtHub.business.services.interfaces.ComentarioService;
import upe.LMPP.ArtHub.business.services.interfaces.PublicacaoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    ComentarioRepository comentarioRepository;

    @Autowired
    PerfilService perfilService;

    @Autowired
    PublicacaoService publicacaoService;

    @Override
    public Comentario publicarComentario(Comentario comentario, Integer idDono, Integer idPublicacao) {
        comentario.setPerfil(perfilService.obterPerfil(idDono));
        comentario.setPublicacao(publicacaoService.buscarPublicacao(idPublicacao));
        comentario.setDataPublicacao(LocalDateTime.now());

        return comentarioRepository.save(comentario);
    }

    @Override
    public void removerComentario(Integer idComentario) {
        Optional<Comentario> comentarioBanco = comentarioRepository.findById(idComentario);

        if (comentarioBanco.isEmpty()) {
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
    public Comentario descurtirComentario(Integer idComentario) {
        Optional<Comentario> comentario = comentarioRepository.findById(idComentario);

        if (comentario.isEmpty()) {
            throw new ComentarioInexistenteException();
        }
        Comentario comentarioCurtido = comentario.get();
        comentarioCurtido.setCurtidas(comentarioCurtido.getCurtidas() - 1);
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
