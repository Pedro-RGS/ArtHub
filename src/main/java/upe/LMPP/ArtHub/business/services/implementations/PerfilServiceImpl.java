package upe.LMPP.ArtHub.business.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.business.services.interfaces.PerfilService;
import upe.LMPP.ArtHub.controller.DTO.pefil.PerfilDTO;
import upe.LMPP.ArtHub.controller.DTO.pefil.PerfilEditadoDTO;
import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioDTO;
import upe.LMPP.ArtHub.infra.entities.Perfil;
import upe.LMPP.ArtHub.infra.entities.Usuario;
import upe.LMPP.ArtHub.infra.exceptions.perfilExceptions.ImagemBannerNaoEncontradaException;
import upe.LMPP.ArtHub.infra.exceptions.perfilExceptions.ImagemPerfilNaoEncontradaException;
import upe.LMPP.ArtHub.infra.exceptions.perfilExceptions.PerfilInexistenteException;
import upe.LMPP.ArtHub.infra.exceptions.usuarioExceptions.UsuarioInexistenteException;
import upe.LMPP.ArtHub.infra.repositories.PerfilRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PerfilServiceImpl implements PerfilService {

    @Value("${PATH_PERFIS}")
    private String caminhoArquivosPerfis;
    @Value("${PATH_BANNERS}")
    private String caminhoArquivosBanners;

    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    MediaService imageService;

    @Override
    public Perfil criarPerfil(Usuario usuario) {
        Perfil novoPerfil = Perfil
                .builder()
                .usuario(usuario)
                .build();

        return perfilRepository.save(novoPerfil);
    }

    @Override
    public boolean seguirUsuario(Integer usuario, Integer seguindo) {
        Perfil perfilUsuario = perfilRepository
                .findById(usuario)
                .orElseThrow(PerfilInexistenteException::new);

        Perfil perfilSeguindo = perfilRepository
                .findById(seguindo)
                .orElseThrow(PerfilInexistenteException::new);

        if (perfilUsuario.getSeguindo().contains(perfilSeguindo.getUsuario().getPerfil())){
            return false;
        }

        perfilUsuario.getSeguindo().add(perfilSeguindo.getUsuario().getPerfil());
        perfilSeguindo.getSeguindo().add(perfilUsuario.getUsuario().getPerfil());
        perfilRepository.save(perfilSeguindo);
        perfilRepository.save(perfilUsuario);

        return true;
    }

    // email, senha, foto de perfil, banner, nome, telefone, bio
    @Override
    public PerfilDTO atualizarBio(Integer donoId, PerfilEditadoDTO dto) {
        Perfil perfil = this.obterPerfil(donoId);
        perfil.setBiografia(dto.biografia());
        perfilRepository.save(perfil);
        return PerfilDTO.perfilToDTO(perfil);
    }

    @Override
    public PerfilDTO uploadFotoPerfil(Integer id, MultipartFile file) {
        try {
            Perfil perfil = obterPerfil(id);

            // Gerar nome único para o arquivo
            String nomeArquivo = file.getOriginalFilename();
            File destino = new File(caminhoArquivosPerfis + "\\" + id + "_" + nomeArquivo);
            file.transferTo(destino);

            // Atualizar o caminho no atributo do usuário
            perfil.setFotoPerfil(nomeArquivo);
            perfilRepository.save(perfil);

            return PerfilDTO.perfilToDTO(perfil);
        } catch (IOException e) {
            throw new PerfilInexistenteException();
        }
    }

    public PerfilDTO uploadFotoBanner(Integer id, MultipartFile file){
        try {
            Perfil perfil = this.obterPerfil(id);

            // Gerar nome único para o arquivo
            String nomeArquivo = file.getOriginalFilename();
            File destino = new File(caminhoArquivosBanners + "\\" + id + "_" + nomeArquivo);
            file.transferTo(destino);

            // Atualizar o caminho no atributo do usuário
            perfil.setBanner(nomeArquivo);
            perfilRepository.save(perfil);

            return PerfilDTO.perfilToDTO(perfil);
        } catch (IOException e) {
            throw new PerfilInexistenteException();
        }
    }

    @Override
    public void removerPerfil(Integer idUsuario) {
        Optional<Perfil> perfil = perfilRepository.findByIdUsuario(idUsuario);

        if (perfil.isEmpty()){
            throw new UsuarioInexistenteException();
        }

        perfilRepository.delete(perfil.get());
    }

    @Override
    public Perfil obterPerfil(Integer id) {
        return perfilRepository
                .findById(id)
                .orElseThrow(PerfilInexistenteException::new);
    }

    @Override
    public ByteArrayResource buscarFotoPerfil(PerfilDTO perfil) {
        try {
            return imageService.getFile(perfil.fotoPerfil(), caminhoArquivosPerfis);
        } catch (IOException e) {
            throw new ImagemPerfilNaoEncontradaException();
        }
    }

    @Override
    public ByteArrayResource buscarFotoBanner(PerfilDTO perfil) {
        try {
            return imageService.getFile(perfil.fotoPerfil(), caminhoArquivosBanners);
        } catch (IOException e) {
            throw new ImagemBannerNaoEncontradaException();
        }
    }

    @Override
    public List<UsuarioDTO> obterSeguidos(Integer idUsuario) {
        Optional<Perfil> perfil = perfilRepository.findByIdUsuario(idUsuario);

        if (perfil.isEmpty()){
            throw new UsuarioInexistenteException();
        }

        return perfil.get().getSeguindo().stream().map(Perfil::getUsuario).map(UsuarioDTO::UsuarioToDTO).toList();
    }

    @Override
    public List<UsuarioDTO> obterSeguidores(Integer idUsuario) {
        Optional<Perfil> perfil = perfilRepository.findByIdUsuario(idUsuario);

        if (perfil.isEmpty()){
            throw new UsuarioInexistenteException();
        }

        return perfil.get().getSeguidores().stream().map(Perfil::getUsuario).map(UsuarioDTO::UsuarioToDTO).toList();
    }

    @Override
    public PerfilDTO getPerfil(Integer id) {
        return PerfilDTO.perfilToDTO(
                perfilRepository.findByIdUsuario(id).orElseThrow(PerfilInexistenteException::new));
    }
}
