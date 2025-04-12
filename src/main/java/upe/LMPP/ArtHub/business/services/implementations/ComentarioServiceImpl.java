package upe.LMPP.ArtHub.business.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upe.LMPP.ArtHub.business.services.interfaces.PerfilService;
import upe.LMPP.ArtHub.controller.DTO.comentario.ComentarioCriadoDTO;
import upe.LMPP.ArtHub.controller.DTO.comentario.ComentarioDTO;
import upe.LMPP.ArtHub.infra.entities.Comentario;
import upe.LMPP.ArtHub.infra.exceptions.comentarioExceptions.ComentarioInexistenteException;
import upe.LMPP.ArtHub.infra.exceptions.publicacaoExceptions.PublicacaoNaoAutoralException;
import upe.LMPP.ArtHub.infra.exceptions.usuarioExceptions.UsuarioInexistenteException;
import upe.LMPP.ArtHub.infra.repositories.ComentarioRepository;
import upe.LMPP.ArtHub.business.services.interfaces.ComentarioService;
import upe.LMPP.ArtHub.business.services.interfaces.PublicacaoService;
import upe.LMPP.ArtHub.infra.repositories.PerfilRepository;
import upe.LMPP.ArtHub.infra.repositories.PublicacaoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    ComentarioRepository comentarioRepository;

    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    PublicacaoRepository publicacaoRepository;

    @Override
    public ComentarioDTO publicarComentario(ComentarioCriadoDTO comentario, Integer idDono, Integer idPublicacao) {
        Comentario novoComentario = new Comentario(
                null,
                0,
                LocalDateTime.now(),
                comentario.conteudo(),
                perfilRepository.findById(idDono).orElseThrow(UsuarioInexistenteException::new),
                publicacaoRepository.findById(idPublicacao).orElseThrow(PublicacaoNaoAutoralException::new));

        return ComentarioDTO.comentarioToDTO(comentarioRepository.save(novoComentario));
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
    public ComentarioDTO curtirComentario(Integer idComentario) {
        Optional<Comentario> comentario = comentarioRepository.findById(idComentario);

        if (comentario.isEmpty()) {
            throw new ComentarioInexistenteException();
        }
        Comentario comentarioCurtido = comentario.get();
        comentarioCurtido.setCurtidas(comentarioCurtido.getCurtidas() + 1);
        return ComentarioDTO.comentarioToDTO(comentarioRepository.save(comentarioCurtido));
    }

    @Override
    public ComentarioDTO descurtirComentario(Integer idComentario) {
        Optional<Comentario> comentario = comentarioRepository.findById(idComentario);

        if (comentario.isEmpty()) {
            throw new ComentarioInexistenteException();
        }
        Comentario comentarioCurtido = comentario.get();
        comentarioCurtido.setCurtidas(comentarioCurtido.getCurtidas() - 1);
        return ComentarioDTO.comentarioToDTO(comentarioRepository.save(comentarioCurtido));
    }

    @Override
    public List<ComentarioDTO> listarComentarios(Integer idPublicacao) {
        return comentarioRepository.findByPublicacao(idPublicacao)
                .stream().map(ComentarioDTO::comentarioToDTO).toList();
    }

    @Override
    public ComentarioDTO buscarPorId(Integer idComentario) {
        Comentario comentario = comentarioRepository.findById(idComentario)
                .orElseThrow(ComentarioInexistenteException::new);
        return ComentarioDTO.comentarioToDTO(comentario);
    }
}
