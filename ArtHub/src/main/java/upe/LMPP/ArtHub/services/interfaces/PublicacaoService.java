package upe.LMPP.ArtHub.services.interfaces;

import upe.LMPP.ArtHub.entities.Publicacao;

import java.util.List;

public interface PublicacaoService {
    public Publicacao criarPublicacao(Publicacao publicacao);
    public Publicacao buscarPublicacao(Integer idPublicacao);
    public List<Publicacao> buscarTodasPublicacacoes();
    public List<Publicacao> buscarPublicacoesPorUsuario(Integer idDono);
    public Publicacao atualizarPublicacao(Publicacao publicacao, Integer idDono);
    public void excluirPublicacao(Integer idPublicacao, Integer idDono);
    public void curtirPublicacao(Integer idPublicacao);
}