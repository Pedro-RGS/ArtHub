package upe.LMPP.ArtHub.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import upe.LMPP.ArtHub.entities.Usuario;
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
    public Usuario cadastrarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioBanco = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioBanco.isPresent()) {
            throw new UsuarioExistenteException();
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario atualizarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioBanco = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioBanco.isPresent()){
            return usuarioRepository.save(usuario);
        }

       throw new UsuarioInexistenteException();
    }

    @Override
    public void removerUsuario(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario buscarUsuarioPorApelido(String apelido) {
        return usuarioRepository.findByApelido(apelido).orElse(null);
    }

}

