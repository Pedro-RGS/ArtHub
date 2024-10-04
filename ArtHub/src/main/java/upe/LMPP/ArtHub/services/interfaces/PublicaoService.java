package upe.LMPP.ArtHub.services.interfaces;

import upe.LMPP.ArtHub.entities.Publicacao;

import java.util.List;

public interface PublicaoService {

    public Publicacao criarPublicacao(Publicacao Publicacao);
    public void excluirPublicacao(Integer IdDono, Integer IdPublicacao);



}