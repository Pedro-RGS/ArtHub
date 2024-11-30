package upe.LMPP.ArtHub.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.entities.Usuario;
import upe.LMPP.ArtHub.entities.enums.CategoriaEnum;
import upe.LMPP.ArtHub.exceptions.publicacaoExceptions.PublicacaoInexistenteException;
import upe.LMPP.ArtHub.exceptions.publicacaoExceptions.PublicacaoNaoAutoralException;
import upe.LMPP.ArtHub.repositories.PublicacaoRepository;
import upe.LMPP.ArtHub.services.interfaces.PublicacaoService;
import upe.LMPP.ArtHub.services.interfaces.UsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PublicacaoServiceImpl implements PublicacaoService {

    @Autowired
    PublicacaoRepository publicacaoRepository;

    @Autowired
    UsuarioService usuarioService;

    @Override
    public Publicacao criarPublicacao(Publicacao publicacao, Integer idDono) {
        Usuario dono = usuarioService.buscarUsuarioPorId(idDono);
        publicacao.setUsuario(dono);
        publicacao.setDataPublicacao(LocalDateTime.now());
        return publicacaoRepository.save(publicacao);
    }

    @Override
    public Publicacao associarArquivoAPublicacao(Integer idPublicacao, String caminhoArquivo) {
        Publicacao publicacao = buscarPublicacao(idPublicacao);
        publicacao.setNomeConteudo(caminhoArquivo);
        return publicacaoRepository.save(publicacao);
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
    public List<Publicacao> buscarPublicacaoPorCategoria(CategoriaEnum categoria) {
        return publicacaoRepository.findByCategoria(categoria);
    }

    @Override
    public Publicacao atualizarPublicacao(Publicacao publicacao, Integer idDono) {
        Optional<Publicacao> publicacaoBanco = publicacaoRepository.findById(publicacao.getId());

        if (publicacaoBanco.isPresent()) {
            Publicacao publicacaoEntity = publicacaoBanco.get();

            if (!publicacaoEntity.getUsuario().getId().equals(idDono)) {
                throw new PublicacaoNaoAutoralException();
            }

            // Atualização de título e legenda
            publicacaoEntity.setTitulo(publicacao.getTitulo());
            publicacaoEntity.setLegenda(publicacao.getLegenda());

            return publicacaoRepository.save(publicacaoEntity);
        }
        throw new PublicacaoInexistenteException();
    }

    @Override
    public Publicacao curtirPublicacao(Integer idPublicacao) {
        Publicacao publicacao = buscarPublicacao(idPublicacao);
        publicacao.setCurtidas(publicacao.getCurtidas() + 1);
        return publicacaoRepository.save(publicacao);
    }

    @Override
    public Publicacao descurtirPublicacao(Integer idPublicacao) {
        Publicacao publicacao = buscarPublicacao(idPublicacao);
        publicacao.setCurtidas(publicacao.getCurtidas() - 1);
        return publicacaoRepository.save(publicacao);
    }

    @Override
    public void excluirPublicacao(Integer idPublicacao, Integer idDono) {
        Publicacao publicacao = buscarPublicacao(idPublicacao);

        if (!publicacao.getUsuario().getId().equals(idDono)) {
            throw new PublicacaoNaoAutoralException();
        }

        publicacaoRepository.deleteById(idPublicacao);
    }
}