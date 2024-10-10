package upe.LMPP.ArtHub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upe.LMPP.ArtHub.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
