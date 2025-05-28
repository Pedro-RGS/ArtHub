package upe.LMPP.ArtHub.controller.DTO.publicacao;

import upe.LMPP.ArtHub.controller.DTO.pefil.PerfilDTO;
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
        PerfilDTO perfil,
        List<PerfilDTO> perfisQueCurtiram) {

    public static PublicacaoDTO PublicacaoToDTO(Publicacao publicacao) {
        return new PublicacaoDTO(
                publicacao.getTipoArquivo(),
                publicacao.getLegenda(),
                publicacao.getNomeConteudo(),
                publicacao.getTitulo(),
                publicacao.getCategoria(),
                publicacao.getCurtidas(),
                PerfilDTO.perfilToDTO(publicacao.getPerfil()),
                publicacao.getPerfisQueCurtiram().stream().map(PerfilDTO::perfilToDTO).toList()
        );
    }
}
