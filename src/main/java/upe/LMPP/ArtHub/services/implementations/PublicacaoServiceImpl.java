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
    public List<Publicacao> buscarPublicacaoPorCategoria(CategoriaEnum categoria){
        return publicacaoRepository.findByCategoria(categoria);
    }

    @Override
    public Publicacao atualizarPublicacao(Publicacao publicacao, Integer idDono) {
        Optional<Publicacao> publicacaoBanco = publicacaoRepository.findById(publicacao.getId());

        if(publicacaoBanco.isPresent()){
            Publicacao publicacaoEntity = publicacaoBanco.get();

            //só é possível atualizar o titulo e a legenda de um post feito anteriomente
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

    @Override
    public Publicacao curtirPublicacao(Integer idPublicacao) {
        Optional<Publicacao> publicacao = publicacaoRepository.findById(idPublicacao);

        if(publicacao.isEmpty()){
            throw new PublicacaoInexistenteException();
        }

        Publicacao publicacaoEntity = publicacao.get();
        publicacaoEntity.setCurtidas(publicacaoEntity.getCurtidas() + 1);
        return publicacaoRepository.save(publicacaoEntity);
    }

    @Override
    public Publicacao descurtirPublicacao(Integer idPublicacao) {
        Optional<Publicacao> publicacaoBanco = publicacaoRepository.findById(idPublicacao);

        if (publicacaoBanco.isEmpty()){
            throw new PublicacaoInexistenteException();
        }

        Publicacao publicacao = publicacaoBanco.get();
        publicacao.setCurtidas(publicacao.getCurtidas() - 1);
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
}