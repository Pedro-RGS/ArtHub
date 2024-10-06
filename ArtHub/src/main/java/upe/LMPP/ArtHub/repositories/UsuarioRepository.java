package upe.LMPP.ArtHub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upe.LMPP.ArtHub.entities.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    public Optional<Usuario> findByEmail(String email);
    public Optional<Usuario> findByApelido(String apelido);

}
