package upe.LMPP.ArtHub.business.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.business.services.interfaces.PerfilService;
import upe.LMPP.ArtHub.controller.DTO.publicacao.PublicacaoCriadaDTO;
import upe.LMPP.ArtHub.controller.DTO.publicacao.PublicacaoDTO;
import upe.LMPP.ArtHub.controller.DTO.publicacao.PublicacaoEditadaDTO;
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
    public PublicacaoDTO criarPublicacao(PublicacaoCriadaDTO publicacao, Integer idDono) {
        Perfil perfilDono = perfilService.obterPerfil(idDono);
        Publicacao novaPublicacao = new Publicacao(
                null,
                publicacao.tipoArquivo(),
                LocalDateTime.now(),
                publicacao.legenda(),
                publicacao.nomeConteudo(),
                publicacao.titulo(),
                0,
                publicacao.categoria(),
                perfilDono,
                new ArrayList<>());
        return PublicacaoDTO.PublicacaoToDTO(publicacaoRepository.save(novaPublicacao));
    }

    @Override
    public PublicacaoDTO associarArquivoAPublicacao(Integer idPublicacao, String caminhoArquivo) {
        Publicacao publicacao = publicacaoRepository.findById(idPublicacao)
                .orElseThrow(PublicacaoInexistenteException::new);
        publicacao.setNomeConteudo(caminhoArquivo);
        return PublicacaoDTO.PublicacaoToDTO(publicacaoRepository.save(publicacao));
    }

    @Override
    public PublicacaoDTO buscarPublicacao(Integer idPublicacao) {
        Optional<Publicacao> publicacao = publicacaoRepository.findById(idPublicacao);
        return PublicacaoDTO.PublicacaoToDTO(publicacao.orElseThrow(PublicacaoInexistenteException::new));
    }

    @Override
    public List<PublicacaoDTO> buscarTodasPublicacacoes(int pagina, int itens) {
        System.out.println("tá chegando no método do service");
        return publicacaoRepository.findAll(PageRequest.of(pagina, itens))
                .stream().map(PublicacaoDTO::PublicacaoToDTO).toList();
    }

    @Override
    public List<PublicacaoDTO> buscarPublicacoesPorUsuario(Integer idDono) {
        return publicacaoRepository.findByPerfil(idDono).stream().map(PublicacaoDTO::PublicacaoToDTO).toList();
    }

    @Override
    public List<PublicacaoDTO> buscarPublicacaoPorCategoria(CategoriaEnum categoria, int pagina, int itens) {
        return publicacaoRepository.findByCategoria(categoria, PageRequest.of(pagina, itens))
                .stream().map(PublicacaoDTO::PublicacaoToDTO).toList();
    }

    @Override
    public PublicacaoDTO atualizarPublicacao(PublicacaoEditadaDTO publicacaoDTO, Integer idDono) {
        Optional<Publicacao> publicacaoBanco = publicacaoRepository.findById(publicacaoDTO.id());

        if (publicacaoBanco.isPresent()) {
            Publicacao publicacaoEntity = publicacaoBanco.get();

            if (!publicacaoEntity.getPerfil().getUsuario().getId().equals(idDono)) {
                throw new PublicacaoNaoAutoralException();
            }

            publicacaoEntity.setTitulo(publicacaoDTO.titulo());
            publicacaoEntity.setLegenda(publicacaoDTO.legenda());
            publicacaoEntity.setNomeConteudo(publicacaoDTO.nomeConteudo());

            return PublicacaoDTO.PublicacaoToDTO(publicacaoRepository.save(publicacaoEntity));
        }
        throw new PublicacaoInexistenteException();
    }

    @Override
    public PublicacaoDTO curtirPublicacao(Integer idPublicacao, Integer idPerfil) {
        Publicacao publicacao = publicacaoRepository.findById(idPublicacao)
                .orElseThrow(PublicacaoInexistenteException::new);
        publicacao.setCurtidas(publicacao.getCurtidas() + 1);
        publicacao.getPerfisQueCurtiram().add(perfilService.obterPerfil(idPerfil));
        return PublicacaoDTO.PublicacaoToDTO(publicacaoRepository.save(publicacao));
    }

    @Override
    public PublicacaoDTO descurtirPublicacao(Integer idPublicacao, Integer idPerfil) {
        Publicacao publicacao = publicacaoRepository.findById(idPublicacao)
                .orElseThrow(PublicacaoInexistenteException::new);
        publicacao.setCurtidas(publicacao.getCurtidas() - 1);
        publicacao.getPerfisQueCurtiram().add(perfilService.obterPerfil(idPerfil));
        return PublicacaoDTO.PublicacaoToDTO(publicacaoRepository.save(publicacao));
    }

    @Override
    public void excluirPublicacao(Integer idPublicacao, Integer idDono) {
        Publicacao publicacao = publicacaoRepository.findById(idPublicacao)
                .orElseThrow(PublicacaoInexistenteException::new);

        if (!publicacao.getPerfil().getUsuario().getId().equals(idDono)) {
            throw new PublicacaoNaoAutoralException();
        }

        publicacaoRepository.deleteById(idPublicacao);
    }

    @Override
    public PublicacaoDTO addMedia(Integer id, MultipartFile arquivo) {
        try {
            Publicacao publicacao = publicacaoRepository.findById(id)
                    .orElseThrow(PublicacaoInexistenteException::new);

            if (arquivo != null && !arquivo.isEmpty()) {
                byte[] bytes = arquivo.getBytes();
                Path caminho = Paths.get(caminhoArquivos + "\\" + id + "_" + arquivo.getOriginalFilename());
                System.out.println(caminho);
                Files.write(caminho, bytes);

                // Atualiza o campo nomeConteudo na publicação
                publicacao.setNomeConteudo(id + "_" + arquivo.getOriginalFilename());
                PublicacaoEditadaDTO dto = new PublicacaoEditadaDTO(
                        publicacao.getId(),
                        publicacao.getTitulo(),
                        publicacao.getLegenda(),
                        publicacao.getNomeConteudo()
                );

                return atualizarPublicacao(dto, publicacao.getPerfil().getUsuario().getId());
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new PublicacaoInexistenteException();
        }
    }
}