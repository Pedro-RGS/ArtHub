package upe.LMPP.ArtHub.services.interfaces;

import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.entities.Usuario;

import java.util.List;

public interface UsuarioService {
    public Usuario cadastrarUsuario(Usuario usuario);
    public Usuario atualizarUsuario(Usuario usuario);
    public void removerUsuario(Usuario usuario);
    public Usuario buscarUsuarioPorId(Integer id);
    public Usuario buscarUsuarioPorApelido(String apelido);
    public List<Publicacao> buscarPublicacoesPorUsuario(Integer id);
    public List<Publicacao> buscarPublicacoesCurtidasPorUsuario(Integer id);

}
