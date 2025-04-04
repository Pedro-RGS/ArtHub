package upe.LMPP.ArtHub.business.services.interfaces;

import upe.LMPP.ArtHub.infra.entities.Publicacao;
import upe.LMPP.ArtHub.infra.enums.CategoriaEnum;

import java.util.List;

public interface PublicacaoService {
    // Todos os metodos de interfaces do java já são "public" por padrao

    Publicacao criarPublicacao(Publicacao publicacao, Integer idDono);
    Publicacao buscarPublicacao(Integer idPublicacao);
    List<Publicacao> buscarTodasPublicacacoes();
    List<Publicacao> buscarPublicacoesPorUsuario(Integer idDono);
    List<Publicacao> buscarPublicacaoPorCategoria(CategoriaEnum categoria);
    Publicacao atualizarPublicacao(Publicacao publicacao, Integer idDono);
    Publicacao curtirPublicacao(Integer idPublicacao);
    Publicacao descurtirPublicacao(Integer idPublicacao);
    Publicacao associarArquivoAPublicacao(Integer idPublicacao, String caminhoArquivo);
    void excluirPublicacao(Integer idPublicacao, Integer idDono);
}