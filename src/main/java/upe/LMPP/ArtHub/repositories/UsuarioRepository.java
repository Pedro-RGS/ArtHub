package upe.LMPP.ArtHub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    public Optional<Usuario> findByEmail(String email);
    public Optional<Usuario> findByApelido(String apelido);

    @Query("SELECT p FROM Publicacao p WHERE p.usuario.id = :id_usuario")
    public List<Publicacao> findPublicacaoByUsuario(@Param("id_usuario") Integer id);
}
