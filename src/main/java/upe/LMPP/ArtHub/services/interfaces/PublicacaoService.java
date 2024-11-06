package upe.LMPP.ArtHub.services.interfaces;

import upe.LMPP.ArtHub.entities.Publicacao;

import java.util.List;

public interface PublicacaoService {
    // Todos os metodos de interfaces do java já são "public" por padrao

    Publicacao criarPublicacao(Publicacao publicacao, Integer idDono);
    Publicacao buscarPublicacao(Integer idPublicacao);
    List<Publicacao> buscarTodasPublicacacoes();
    List<Publicacao> buscarPublicacoesPorUsuario(Integer idDono);
    Publicacao atualizarPublicacao(Publicacao publicacao, Integer idDono);
    Publicacao curtirPublicacao(Integer idPublicacao);
    Publicacao descurtirPublicacao(Integer idPublicacao);
    void excluirPublicacao(Integer idPublicacao, Integer idDono);
}