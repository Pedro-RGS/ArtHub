package upe.LMPP.ArtHub.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.repositories.PublicacaoRepository;
import upe.LMPP.ArtHub.services.interfaces.PublicacaoService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PublicacaoServiceImpl implements PublicacaoService {

    @Autowired
    PublicacaoRepository publicacaoRepository;

    @Override
    public Publicacao criarPublicacao(Publicacao publicacao) {
        return publicacaoRepository.save(publicacao);
    }

    @Override
    public void excluirPublicacao(Integer idPublicacao) {
        publicacaoRepository.deleteById(idPublicacao);
    }

    @Override
    public void curtirPublicacao(Integer idPublicacao) {
        Optional<Publicacao> publicacao = publicacaoRepository.findById(idPublicacao);

        if(publicacao.isPresent()){
            Publicacao publicacaoEntity = publicacao.get();
            publicacaoEntity.setCurtidas(publicacaoEntity.getCurtidas() + 1);
        }

        // publicacao nao encontrada
    }

    @Override
    public Publicacao buscarPublicacao(Integer idPublicacao) {
        Optional<Publicacao> publicacao = publicacaoRepository.findById(idPublicacao);

        return publicacao.orElse(null);
    }

    @Override
    public List<Publicacao> buscarPublicacoesPorUsuario() {
        return publicacaoRepository.findAll();
    }

    @Override
    public List<Publicacao> buscarPublicacoesPorUsuario(Integer idDono) {
        return publicacaoRepository.findByUsuario(idDono);
    }

    @Override
    public Publicacao atualizarPublicacao(Publicacao publicacao) {
        Optional<Publicacao> publicacaoBanco = publicacaoRepository.findById(publicacao.getId());

        if(publicacaoBanco.isPresent()){
            Publicacao publicacaoEntity = publicacaoBanco.get();

            if (publicacao.getTitulo() != publicacaoEntity.getTitulo() ||
                    publicacao.getLegenda() != publicacaoEntity.getLegenda()){
                return publicacaoRepository.save(publicacao);
            }
        }
        return null;
    }
}