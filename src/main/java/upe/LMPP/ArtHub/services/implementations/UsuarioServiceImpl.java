package upe.LMPP.ArtHub.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import upe.LMPP.ArtHub.entities.DTO.UsuarioDTO;
import upe.LMPP.ArtHub.entities.Usuario;
import upe.LMPP.ArtHub.entities.enums.UsuarioEnum;
import upe.LMPP.ArtHub.exceptions.usuarioExceptions.UsuarioExistenteException;
import upe.LMPP.ArtHub.exceptions.usuarioExceptions.UsuarioInexistenteException;
import upe.LMPP.ArtHub.repositories.UsuarioRepository;
import upe.LMPP.ArtHub.services.interfaces.UsuarioService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Usuario cadastrarUsuario(UsuarioDTO usuarioDTO, UsuarioEnum usuarioEnum) {
        Optional<Usuario> usuarioBanco = usuarioRepository.findByEmail(usuarioDTO.email());

        if (usuarioBanco.isPresent()) {
            throw new UsuarioExistenteException();
        }

        String senhaEncriptada = new BCryptPasswordEncoder().encode(usuarioDTO.senha());

        Usuario usuario = Usuario.builder()
                .nome(usuarioDTO.nome())
                .apelido(usuarioDTO.apelido())
                .email(usuarioDTO.email())
                .dataNascimento(usuarioDTO.dataNascimento())
                .senha(senhaEncriptada).build();

        return usuarioRepository.save(usuario);
    }

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

    @Override
    public boolean seguirUsuario(Usuario usuario, Usuario seguindo) {
        if (usuario.getSeguindo().contains(seguindo)) {
            return false;
        }

        usuario.getSeguindo().add(seguindo);
        seguindo.getSeguidores().add(usuario);
        usuarioRepository.save(usuario);
        usuarioRepository.save(seguindo);
        return true;
    }
}

