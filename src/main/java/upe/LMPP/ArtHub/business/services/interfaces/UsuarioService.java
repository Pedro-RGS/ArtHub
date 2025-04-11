package upe.LMPP.ArtHub.business.services.interfaces;

import upe.LMPP.ArtHub.controller.DTO.UsuarioDTO;
import upe.LMPP.ArtHub.infra.entities.Usuario;
import upe.LMPP.ArtHub.infra.enums.UsuarioEnum;

public interface UsuarioService {
    Usuario cadastrarUsuario(UsuarioDTO usuario, UsuarioEnum usuarioEnum);
    Usuario atualizarUsuario(Usuario usuario);
    Usuario promoverUsuarioParaAdmim(Integer idAdmin, Integer idPromovido);
    void removerUsuario(Integer id);
    Usuario buscarUsuarioPorId(Integer id);
    Usuario buscarUsuarioPorApelido(String apelido);
    Usuario buscarUsuarioLogado(String token);
    Usuario buscarUsuarioPorEmail(String email);
}