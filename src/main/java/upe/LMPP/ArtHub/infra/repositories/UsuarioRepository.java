package upe.LMPP.ArtHub.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upe.LMPP.ArtHub.infra.entities.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    public Optional<Usuario> findByEmail(String email);
    public Optional<Usuario> findByApelido(String apelido);
    Optional<Usuario> findById(Integer id);

}
