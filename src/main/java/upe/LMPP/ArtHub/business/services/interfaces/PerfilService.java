package upe.LMPP.ArtHub.business.services.interfaces;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.controller.DTO.pefil.PerfilDTO;
import upe.LMPP.ArtHub.controller.DTO.pefil.PerfilEditadoDTO;
import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioDTO;
import upe.LMPP.ArtHub.infra.entities.Perfil;
import upe.LMPP.ArtHub.infra.entities.Usuario;

import java.util.List;

public interface PerfilService {
    Perfil criarPerfil(Usuario usuario);
    boolean seguirUsuario(Integer usuario, Integer seguindo);
    PerfilDTO uploadFotoPerfil(Integer id, MultipartFile file);
    PerfilDTO uploadFotoBanner(Integer id, MultipartFile file);
    PerfilDTO atualizarPerfil(Integer donoId, PerfilEditadoDTO dto);
    void removerPerfil(Integer idUsuario);
    Perfil obterPerfil(Integer id);
    PerfilDTO getPerfil(Integer id);
    ByteArrayResource buscarFotoPerfil(PerfilDTO perfil);
    ByteArrayResource buscarFotoBanner(PerfilDTO perfil);
    List<UsuarioDTO> obterSeguidos(Integer idUsuario);
    List<UsuarioDTO> obterSeguidores(Integer idUsuario);
}
