package upe.LMPP.ArtHub.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.entities.Usuario;
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
            return null; //Aqui vai a Exception de quando já existir o usuário
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario atualizarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioBanco = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioBanco.isPresent()){
            return usuarioRepository.save(usuario);
        }

        return null; //Aqui vai a Exception de quando não existir usuário
    }

    @Override
    public void removerUsuario(Usuario usuario) {

    }

    @Override
    public Usuario buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario buscarUsuarioPorApelido(String apelido) {
        return usuarioRepository.findByApelido(apelido).orElse(null);
    }

    @Override
    public List<Publicacao> buscarPublicacoesPorUsuario(Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()){
            return usuario.get().getPublicacoes();
        }

        return null;
    }

    @Override
    public List<Publicacao> buscarPublicacoesCurtidasPorUsuario(Integer id) {

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()){
            return usuario.get().getPublicacoesCurtidas();
        }

        return null;
    }
}

