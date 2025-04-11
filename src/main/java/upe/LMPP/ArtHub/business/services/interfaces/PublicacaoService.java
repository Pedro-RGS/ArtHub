package upe.LMPP.ArtHub.business.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.controller.DTO.PublicacaoDTO;
import upe.LMPP.ArtHub.controller.DTO.PublicacaoEditadaDTO;
import upe.LMPP.ArtHub.infra.entities.Publicacao;
import upe.LMPP.ArtHub.infra.enums.CategoriaEnum;

import java.util.List;

public interface PublicacaoService {

    Publicacao criarPublicacao(PublicacaoDTO publicacaoDTO, Integer idDono);
    Publicacao buscarPublicacao(Integer idPublicacao);
    List<Publicacao> buscarTodasPublicacacoes();
    List<Publicacao> buscarPublicacoesPorUsuario(Integer idDono);
    List<Publicacao> buscarPublicacaoPorCategoria(CategoriaEnum categoria);
    Publicacao atualizarPublicacao(PublicacaoEditadaDTO publicacaoDTO, Integer idDono);
    Publicacao curtirPublicacao(Integer idPublicacao);
    Publicacao descurtirPublicacao(Integer idPublicacao);
    Publicacao associarArquivoAPublicacao(Integer idPublicacao, String caminhoArquivo);
    void excluirPublicacao(Integer idPublicacao, Integer idDono);
    Publicacao addMedia(Integer id, MultipartFile arquivo);
}