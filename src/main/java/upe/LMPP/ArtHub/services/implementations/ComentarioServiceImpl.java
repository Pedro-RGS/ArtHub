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
import upe.LMPP.ArtHub.services.interfaces.PublicacaoService;
import upe.LMPP.ArtHub.services.interfaces.UsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    ComentarioRepository comentarioRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PublicacaoService publicacaoService;

    @Override
    public Comentario publicarComentario(Comentario comentario, Integer idDono, Integer idPublicacao) {
        comentario.setUsuario(usuarioService.buscarUsuarioPorId(idDono));
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
