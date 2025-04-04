package upe.LMPP.ArtHub.business.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import upe.LMPP.ArtHub.infra.entities.Usuario;
import upe.LMPP.ArtHub.infra.exceptions.usuarioExceptions.UsuarioInexistenteException;
import upe.LMPP.ArtHub.infra.repositories.UsuarioRepository;

import java.util.Optional;

@Service
public class AuthServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Usuario> talvezUsuario = usuarioRepository.findByEmail(email);

        if(talvezUsuario.isEmpty()) {
            throw new UsuarioInexistenteException();
        }

        return talvezUsuario.get();
    }
}
