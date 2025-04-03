package upe.LMPP.ArtHub.services.interfaces;

import upe.LMPP.ArtHub.entities.DTO.UsuarioDTO;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.entities.Usuario;
import upe.LMPP.ArtHub.entities.enums.UsuarioEnum;

import java.util.List;

public interface UsuarioService {
    public Usuario cadastrarUsuario(UsuarioDTO usuario, UsuarioEnum usuarioEnum);
    public Usuario atualizarUsuario(Usuario usuario);
    public void removerUsuario(Integer id);
    public Usuario buscarUsuarioPorId(Integer id);
    public Usuario buscarUsuarioPorApelido(String apelido);
    public Usuario buscarUsuarioPorEmail(String email);
    public boolean seguirUsuario(Usuario usuario, Usuario seguindo);
}