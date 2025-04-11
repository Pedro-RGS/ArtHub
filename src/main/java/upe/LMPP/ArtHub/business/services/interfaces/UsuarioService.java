package upe.LMPP.ArtHub.business.services.interfaces;

import upe.LMPP.ArtHub.controller.DTO.UsuarioDTO;
import upe.LMPP.ArtHub.infra.entities.Usuario;
import upe.LMPP.ArtHub.infra.enums.UsuarioEnum;

public interface UsuarioService {
    public Usuario cadastrarUsuario(UsuarioDTO usuario, UsuarioEnum usuarioEnum);
    public Usuario atualizarUsuario(Usuario usuario);
    public void removerUsuario(Integer id);
    public Usuario buscarUsuarioPorId(Integer id);
    public Usuario buscarUsuarioPorApelido(String apelido);
    public Usuario buscarUsuarioPorEmail(String email);
}