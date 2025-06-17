package upe.LMPP.ArtHub.controller.DTO.publicacao;

import upe.LMPP.ArtHub.infra.enums.CategoriaEnum;

public record PublicacaoEditadaDTO(
        Integer id,
        String titulo,
        String legenda,
        CategoriaEnum categoria,
        String nomeConteudo) {
}
