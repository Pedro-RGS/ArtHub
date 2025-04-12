package upe.LMPP.ArtHub.controller.DTO.publicacao;

import upe.LMPP.ArtHub.infra.entities.Perfil;
import upe.LMPP.ArtHub.infra.entities.Publicacao;
import upe.LMPP.ArtHub.infra.enums.CategoriaEnum;
import upe.LMPP.ArtHub.infra.enums.TipoArquivoEnum;

import java.util.List;

public record PublicacaoDTO(
        TipoArquivoEnum tipoArquivo,
        String legenda,
        String nomeConteudo,
        String titulo,
        CategoriaEnum categoria,
        Integer curtidas,
        Perfil perfil,
        List<Perfil> perfisQueCurtiram) {

    public static PublicacaoDTO PublicacaoToDTO(Publicacao publicacao) {
        return new PublicacaoDTO(
                publicacao.getTipoArquivo(),
                publicacao.getLegenda(),
                publicacao.getNomeConteudo(),
                publicacao.getTitulo(),
                publicacao.getCategoria(),
                publicacao.getCurtidas(),
                publicacao.getPerfil(),
                publicacao.getPerfisQueCurtiram());
    }
}
