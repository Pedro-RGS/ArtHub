package upe.LMPP.ArtHub.business.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.business.services.interfaces.PerfilService;
import upe.LMPP.ArtHub.controller.DTO.PublicacaoDTO;
import upe.LMPP.ArtHub.controller.DTO.PublicacaoEditadaDTO;
import upe.LMPP.ArtHub.infra.entities.Perfil;
import upe.LMPP.ArtHub.infra.entities.Publicacao;
import upe.LMPP.ArtHub.infra.enums.CategoriaEnum;
import upe.LMPP.ArtHub.infra.exceptions.publicacaoExceptions.PublicacaoInexistenteException;
import upe.LMPP.ArtHub.infra.exceptions.publicacaoExceptions.PublicacaoNaoAutoralException;
import upe.LMPP.ArtHub.infra.repositories.PublicacaoRepository;
import upe.LMPP.ArtHub.business.services.interfaces.PublicacaoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PublicacaoServiceImpl implements PublicacaoService {

    @Value("${PATH_PUBLICACOES}")
    private String caminhoArquivos;

    @Autowired
    PublicacaoRepository publicacaoRepository;

    @Autowired
    PerfilService perfilService;

    @Override
    public Publicacao criarPublicacao(PublicacaoDTO publicacaoDTO, Integer idDono) {
        Perfil perfilDono = perfilService.obterPerfil(idDono);
        Publicacao novaPublicacao = new Publicacao(
                null,
                publicacaoDTO.tipoArquivo(),
                LocalDateTime.now(),
                publicacaoDTO.legenda(),
                publicacaoDTO.nomeConteudo(),
                publicacaoDTO.titulo(),
                0,
                publicacaoDTO.categoria(),
                perfilDono,
                new ArrayList<>());
        return publicacaoRepository.save(novaPublicacao);
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
        return publicacaoRepository.findByPerfil(idDono);
    }

    @Override
    public List<Publicacao> buscarPublicacaoPorCategoria(CategoriaEnum categoria) {
        return publicacaoRepository.findByCategoria(categoria);
    }

    @Override
    public Publicacao atualizarPublicacao(PublicacaoEditadaDTO publicacaoDTO, Integer idDono) {
        Optional<Publicacao> publicacaoBanco = publicacaoRepository.findById(publicacaoDTO.id());

        if (publicacaoBanco.isPresent()) {
            Publicacao publicacaoEntity = publicacaoBanco.get();

            if (!publicacaoEntity.getPerfil().getUsuario().getId().equals(idDono)) {
                throw new PublicacaoNaoAutoralException();
            }

            publicacaoEntity.setTitulo(publicacaoDTO.titulo());
            publicacaoEntity.setLegenda(publicacaoDTO.legenda());
            publicacaoEntity.setNomeConteudo(publicacaoDTO.nomeConteudo());

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

        if (!publicacao.getPerfil().getUsuario().getId().equals(idDono)) {
            throw new PublicacaoNaoAutoralException();
        }

        publicacaoRepository.deleteById(idPublicacao);
    }

    @Override
    public Publicacao addMedia(Integer id, MultipartFile arquivo) {
        try {
            Publicacao publicacao = buscarPublicacao(id);

            PublicacaoEditadaDTO dto = new PublicacaoEditadaDTO(
                    publicacao.getId(),
                    publicacao.getTitulo(),
                    publicacao.getLegenda(),
                    publicacao.getNomeConteudo()
            );

            if (arquivo != null && !arquivo.isEmpty()) {
                byte[] bytes = arquivo.getBytes();
                Path caminho = Paths.get(caminhoArquivos + id + "_" + arquivo.getOriginalFilename());
                Files.write(caminho, bytes);

                // Atualiza o campo nomeConteudo na publicação
                publicacao.setNomeConteudo(id + "_" + arquivo.getOriginalFilename());

                return atualizarPublicacao(dto, publicacao.getPerfil().getUsuario().getId());
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new PublicacaoInexistenteException();
        }
    }
}