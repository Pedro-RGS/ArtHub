package upe.LMPP.ArtHub.business.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import upe.LMPP.ArtHub.business.services.interfaces.PerfilService;
import upe.LMPP.ArtHub.controller.DTO.UsuarioDTO;
import upe.LMPP.ArtHub.infra.entities.Perfil;
import upe.LMPP.ArtHub.infra.entities.Usuario;
import upe.LMPP.ArtHub.infra.enums.UsuarioEnum;
import upe.LMPP.ArtHub.infra.exceptions.usuarioExceptions.UsuarioExistenteException;
import upe.LMPP.ArtHub.infra.exceptions.usuarioExceptions.UsuarioInexistenteException;
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

    @Override
    public Usuario cadastrarUsuario(UsuarioDTO usuarioDTO, UsuarioEnum usuarioEnum) {
        Optional<Usuario> usuarioBanco = usuarioRepository.findByEmail(usuarioDTO.email());

        if (usuarioBanco.isPresent()) {
            throw new UsuarioExistenteException();
        }

        String senhaEncriptada = new BCryptPasswordEncoder().encode(usuarioDTO.senha());

        Usuario usuario = new Usuario(
                null,
                usuarioDTO.nome(),
                usuarioDTO.apelido(),
                usuarioDTO.email(),
                usuarioDTO.dataNascimento(),
                senhaEncriptada,
                usuarioDTO.telefone(),
                null,
                usuarioEnum
        );

        Perfil perfilUsuario = perfilService.criarPerfil(usuario);
        usuario.setPerfil(perfilUsuario);

        return usuarioRepository.save(usuario);
    }

    // Só está atualizando a senha do usuáio
    @Override
    public Usuario atualizarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioBanco = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioBanco.isPresent()) {
            Usuario usuarioExistente = usuarioBanco.get();

            if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
                String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
                usuarioExistente.setSenha(senhaCriptografada);
            }
            return usuarioRepository.save(usuarioExistente);
        }
        throw new UsuarioInexistenteException();
    }

    @Override
    public void removerUsuario(Integer id) {
        perfilService.removerPerfil(id);
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(UsuarioInexistenteException::new);
    }

    @Override
    public Usuario buscarUsuarioPorApelido(String apelido) {
        return usuarioRepository.findByApelido(apelido).orElseThrow(UsuarioInexistenteException::new);
    }

    @Override
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(UsuarioInexistenteException::new);
    }
}

