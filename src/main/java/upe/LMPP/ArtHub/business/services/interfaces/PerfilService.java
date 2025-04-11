package upe.LMPP.ArtHub.business.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.infra.entities.Perfil;
import upe.LMPP.ArtHub.infra.entities.Usuario;

import java.util.List;

public interface PerfilService {
    Perfil criarPerfil(Usuario usuario);
    boolean seguirUsuario(Integer usuario, Integer seguindo);
    Perfil uploadFotoPerfil(Integer id, MultipartFile file);
    Perfil uploadFotoBanner(Integer id, MultipartFile file);
    Perfil atualizarPerfil(Perfil perfil);
    void removerPerfil(Integer idUsuario);
    Perfil obterPerfil(Integer id);
    List<Usuario> obterSeguidos(Integer idUsuario);
    List<Usuario> obterSeguidores(Integer idUsuario);
}
