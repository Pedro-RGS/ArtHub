package upe.LMPP.ArtHub.business.services.interfaces;

import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioCriadoDTO;
import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioDTO;
import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioEditadoDTO;
import upe.LMPP.ArtHub.infra.entities.Usuario;
import upe.LMPP.ArtHub.infra.enums.UsuarioEnum;

public interface UsuarioService {
    UsuarioDTO cadastrarUsuario(UsuarioCriadoDTO usuario, UsuarioEnum usuarioEnum);
    UsuarioDTO atualizarUsuario(UsuarioEditadoDTO usuario);
    UsuarioDTO promoverUsuarioParaAdmim(Integer idAdmin, Integer idPromovido);
    void removerUsuario(Integer id);
    UsuarioDTO buscarUsuarioPorId(Integer id);
    UsuarioDTO buscarUsuarioPorApelido(String apelido);
    UsuarioDTO buscarUsuarioLogado(String token);
    UsuarioDTO buscarUsuarioPorEmail(String email);
    Usuario buscarUsuarioPorEmailUserDetails(String email);
}