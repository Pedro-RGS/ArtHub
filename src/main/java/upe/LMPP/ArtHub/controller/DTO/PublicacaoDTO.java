package upe.LMPP.ArtHub.controller.DTO;

import jakarta.validation.constraints.NotNull;
import upe.LMPP.ArtHub.infra.enums.CategoriaEnum;
import upe.LMPP.ArtHub.infra.enums.TipoArquivoEnum;

public record PublicacaoDTO(
        TipoArquivoEnum tipoArquivo,
        String legenda,
        String nomeConteudo,
        String titulo,
        CategoriaEnum categoria) {
}
