package upe.LMPP.ArtHub.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import upe.LMPP.ArtHub.infra.entities.Perfil;

import java.util.List;
import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    @Query("SELECT p FROM Perfil p WHERE p.usuario.id = :idUsuario")
    Optional<Perfil> findByIdUsuario(@Param("idUsuario") Integer id);
    List<Perfil> findByUsuarioNomeContainingIgnoreCaseOrUsuarioApelidoContainingIgnoreCase(String nome, String apelido);
}
