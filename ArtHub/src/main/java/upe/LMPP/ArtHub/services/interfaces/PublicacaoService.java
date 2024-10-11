package upe.LMPP.ArtHub.services.interfaces;

import upe.LMPP.ArtHub.entities.Publicacao;

import java.util.List;

public interface PublicacaoService {
    public Publicacao criarPublicacao(Publicacao publicacao);
    public void excluirPublicacao(Integer idPublicacao);
    public void curtirPublicacao(Integer idPublicacao);
    public Publicacao buscarPublicacao(Integer idPublicacao);
    public List<Publicacao> buscarPublicacoesPorUsuario();
    public List<Publicacao> buscarPublicacoesPorUsuario(Integer idDono);
    public Publicacao atualizarPublicacao(Publicacao publicacao);
}