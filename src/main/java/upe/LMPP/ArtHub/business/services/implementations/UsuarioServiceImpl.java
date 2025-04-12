package upe.LMPP.ArtHub.business.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import upe.LMPP.ArtHub.business.security.TokenService;
import upe.LMPP.ArtHub.business.services.interfaces.PerfilService;
import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioCriadoDTO;
import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioDTO;
import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioEditadoDTO;
import upe.LMPP.ArtHub.infra.entities.Perfil;
import upe.LMPP.ArtHub.infra.entities.Usuario;
import upe.LMPP.ArtHub.infra.enums.UsuarioEnum;
import upe.LMPP.ArtHub.infra.exceptions.usuarioExceptions.UsuarioAdministradorInexistenteException;
import upe.LMPP.ArtHub.infra.exceptions.usuarioExceptions.UsuarioExistenteException;
import upe.LMPP.ArtHub.infra.exceptions.usuarioExceptions.UsuarioInexistenteException;
import upe.LMPP.ArtHub.infra.exceptions.usuarioExceptions.UsuarioNaoAdminException;
import upe.LMPP.ArtHub.infra.repositories.UsuarioRepository;
import upe.LMPP.ArtHub.business.services.interfaces.UsuarioService;

import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PerfilService perfilService;

    @Autowired
    private TokenService tokenService;

    @Override
    public UsuarioDTO cadastrarUsuario(UsuarioCriadoDTO usuarioCriadoDTO, UsuarioEnum usuarioEnum) {
        Optional<Usuario> usuarioBanco = usuarioRepository.findByEmail(usuarioCriadoDTO.email());

        if (usuarioBanco.isPresent()) {
            throw new UsuarioExistenteException();
        }

        String senhaEncriptada = new BCryptPasswordEncoder().encode(usuarioCriadoDTO.senha());

        Usuario usuario = new Usuario(
                null,
                usuarioCriadoDTO.nome(),
                usuarioCriadoDTO.apelido(),
                usuarioCriadoDTO.email(),
                usuarioCriadoDTO.dataNascimento(),
                senhaEncriptada,
                usuarioCriadoDTO.telefone(),
                null,
                usuarioEnum
        );

        Perfil perfilUsuario = perfilService.criarPerfil(usuario);
        usuario.setPerfil(perfilUsuario);

        return UsuarioDTO.UsuarioToDTO(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTO atualizarUsuario(UsuarioEditadoDTO usuario) {
        Optional<Usuario> usuarioBanco = usuarioRepository.findByEmail(usuario.email());

        if (usuarioBanco.isPresent()) {
            Usuario usuarioExistente = usuarioBanco.get();

            if (usuario.senha() != null && !usuario.senha().isEmpty()) {
                String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.senha());
                usuarioExistente.setSenha(senhaCriptografada);
                usuarioExistente.setApelido(usuario.apelido());
                usuarioExistente.setEmail(usuario.email());
                usuarioExistente.setTelefone(usuario.telefone());
            }
            usuarioRepository.save(usuarioExistente);
            return UsuarioDTO.UsuarioToDTO(usuarioExistente);
        }
        throw new UsuarioInexistenteException();
    }

    @Override
    public UsuarioDTO promoverUsuarioParaAdmim(Integer idAdmin, Integer idPromovido) {
        Optional<Usuario> adminNomeadorOptional = usuarioRepository.findById(idAdmin);

        if (adminNomeadorOptional.isEmpty()) {
            throw new UsuarioAdministradorInexistenteException();
        }

        Usuario adminNomeador = adminNomeadorOptional.get();

        if (adminNomeador.getTipoUsuario() != UsuarioEnum.ADMINISTRADOR) {
            throw new UsuarioNaoAdminException();
        }

        Optional<Usuario> usuarioPromovidoOptional = usuarioRepository.findById(idPromovido);

        if (usuarioPromovidoOptional.isEmpty()) {
            throw new UsuarioInexistenteException();
        }

        Usuario usuarioPromovido = usuarioPromovidoOptional.get();

        usuarioPromovido.setTipoUsuario(UsuarioEnum.ADMINISTRADOR);

        return UsuarioDTO.UsuarioToDTO(usuarioRepository.save(usuarioPromovido));
    }

    @Override
    public void removerUsuario(Integer id) {
        perfilService.removerPerfil(id);
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioDTO buscarUsuarioPorId(Integer id) {
        return UsuarioDTO.UsuarioToDTO(usuarioRepository.findById(id)
                .orElseThrow(UsuarioInexistenteException::new));
    }

    @Override
    public UsuarioDTO buscarUsuarioPorApelido(String apelido) {
        return UsuarioDTO.UsuarioToDTO(usuarioRepository.findByApelido(apelido)
                .orElseThrow(UsuarioInexistenteException::new));
    }

    @Override
    public UsuarioDTO buscarUsuarioLogado(String token) {
        try {
            token = token.replace("Bearer ", "");

            String email = tokenService.extractEmailFromToken(token);

            return this.buscarUsuarioPorEmail(email);
        } catch (Exception e) {
            throw new UsuarioInexistenteException();
        }
    }

    @Override
    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        return UsuarioDTO.UsuarioToDTO(usuarioRepository.findByEmail(email)
                .orElseThrow(UsuarioInexistenteException::new));
    }

    @Override
    public Usuario buscarUsuarioPorEmailUserDetails(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(UsuarioInexistenteException::new);
    }
}

