package upe.LMPP.ArtHub.services.interfaces;

import upe.LMPP.ArtHub.entities.Publicacao;

import java.util.List;

public interface PublicaoService {

    public Publicacao criarPublicacao(Publicacao Publicacao);
    public void excluirPublicacao(Integer idDono, Integer idPublicacao);
    public void curtirPublicacao(Integer idUsuario, Integer idPublicacao);
    public Publicacao buscarPublicacao(Integer idDono);
    public List<Publicacao> buscarPublicacoes();
    public List<Publicacao> buscarPublicacoes(Integer idDono);
    public Publicacao atualizarPublicacao(Publicacao publicacao);
}