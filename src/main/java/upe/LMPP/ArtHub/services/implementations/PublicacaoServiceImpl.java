package upe.LMPP.ArtHub.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.entities.Usuario;
import upe.LMPP.ArtHub.exceptions.publicacaoExceptions.PublicacaoInexistenteException;
import upe.LMPP.ArtHub.exceptions.publicacaoExceptions.PublicacaoNaoAutoralException;
import upe.LMPP.ArtHub.exceptions.usuarioExceptions.UsuarioInexistenteException;
import upe.LMPP.ArtHub.repositories.PublicacaoRepository;
import upe.LMPP.ArtHub.repositories.UsuarioRepository;
import upe.LMPP.ArtHub.services.interfaces.PublicacaoService;
import upe.LMPP.ArtHub.services.interfaces.UsuarioService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PublicacaoServiceImpl implements PublicacaoService {

    @Autowired
    PublicacaoRepository publicacaoRepository;
    UsuarioService usuarioService;

    @Override
    public Publicacao criarPublicacao(Publicacao publicacao, Integer idDono) {
        Usuario dono = usuarioService.buscarUsuarioPorId(idDono);
        publicacao.setUsuario(dono);
        return publicacaoRepository.save(publicacao);
    }

    @Override
    public void excluirPublicacao(Integer idPublicacao, Integer idDono) {
        Optional<Publicacao> publicacaoBanco = publicacaoRepository.findById(idPublicacao);

        if(publicacaoBanco.isEmpty()){
            throw new PublicacaoInexistenteException();
        }

        if(publicacaoBanco.get().getUsuario().getId() != idDono){
            throw new PublicacaoNaoAutoralException();
        }

        publicacaoRepository.deleteById(idPublicacao);
    }

    @Override
    public void curtirPublicacao(Integer idPublicacao) {
        Optional<Publicacao> publicacao = publicacaoRepository.findById(idPublicacao);

        if(publicacao.isEmpty()){
            throw new PublicacaoInexistenteException();
        }

        Publicacao publicacaoEntity = publicacao.get();
        publicacaoEntity.setCurtidas(publicacaoEntity.getCurtidas() + 1);
    }

    @Override
    public Publicacao buscarPublicacao(Integer idPublicacao) {
        Optional<Publicacao> publicacao = publicacaoRepository.findById(idPublicacao);

        return publicacao.orElseThrow(PublicacaoInexistenteException::new);
    }

    @Override
    public List<Publicacao> buscarTodasPublicacacoes() {
        return publicacaoRepository.findAll();
    }

    @Override
    public List<Publicacao> buscarPublicacoesPorUsuario(Integer idDono) {
        return publicacaoRepository.findByUsuario(idDono);
    }

    @Override
    public Publicacao atualizarPublicacao(Publicacao publicacao, Integer idDono) {
        Optional<Publicacao> publicacaoBanco = publicacaoRepository.findById(publicacao.getId());

        if(publicacaoBanco.isPresent()){
            Publicacao publicacaoEntity = publicacaoBanco.get();

            if (publicacao.getTitulo() != publicacaoEntity.getTitulo() ||
                    publicacao.getLegenda() != publicacaoEntity.getLegenda()){
                return publicacaoRepository.save(publicacao);
            }

            if(publicacaoBanco.get().getUsuario().getId() != idDono){
                throw new PublicacaoNaoAutoralException();
            }
        }
        throw new PublicacaoInexistenteException();
    }
}