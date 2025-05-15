package upe.LMPP.ArtHub.business.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.controller.DTO.publicacao.PublicacaoCriadaDTO;
import upe.LMPP.ArtHub.controller.DTO.publicacao.PublicacaoDTO;
import upe.LMPP.ArtHub.controller.DTO.publicacao.PublicacaoEditadaDTO;
import upe.LMPP.ArtHub.infra.enums.CategoriaEnum;

import java.util.List;

public interface PublicacaoService {

    PublicacaoDTO criarPublicacao(PublicacaoCriadaDTO publicacao, Integer idDono);
    PublicacaoDTO buscarPublicacao(Integer idPublicacao);
    List<PublicacaoDTO> buscarTodasPublicacacoes(int pagina, int itens);
    List<PublicacaoDTO> buscarPublicacoesPorUsuario(Integer idDono);
    List<PublicacaoDTO> buscarPublicacaoPorCategoria(CategoriaEnum categoria, int pagina, int itens);
    PublicacaoDTO atualizarPublicacao(PublicacaoEditadaDTO publicacaoDTO, Integer idDono);
    PublicacaoDTO curtirPublicacao(Integer idPublicacao, Integer idPerfil);
    PublicacaoDTO descurtirPublicacao(Integer idPublicacao, Integer idPerfil);
    PublicacaoDTO associarArquivoAPublicacao(Integer idPublicacao, String caminhoArquivo);
    void excluirPublicacao(Integer idPublicacao, Integer idDono);
    PublicacaoDTO addMedia(Integer id, MultipartFile arquivo);
}