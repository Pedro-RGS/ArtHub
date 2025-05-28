package upe.LMPP.ArtHub.controller.DTO.usuario;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

// Criar um UsuarioCreationDTO
public record UsuarioCriadoDTO(
        String nome,
        String apelido,
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@upe\\.br$", message = "O e-mail deve ser institucional (@upe.br)")
        String email,
        Date dataNascimento,
        String senha,
        String telefone) {
}