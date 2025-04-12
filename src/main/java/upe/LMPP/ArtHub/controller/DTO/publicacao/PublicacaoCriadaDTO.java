package upe.LMPP.ArtHub.controller.DTO.publicacao;

import upe.LMPP.ArtHub.infra.enums.CategoriaEnum;
import upe.LMPP.ArtHub.infra.enums.TipoArquivoEnum;

public record PublicacaoCriadaDTO(
        TipoArquivoEnum tipoArquivo,
        String legenda,
        String nomeConteudo,
        String titulo,
        CategoriaEnum categoria) {
}
