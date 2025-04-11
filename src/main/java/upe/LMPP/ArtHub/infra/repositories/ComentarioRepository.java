package upe.LMPP.ArtHub.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import upe.LMPP.ArtHub.infra.entities.Comentario;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

    @Query("SELECT c FROM Comentario c WHERE c.publicacao.id = :idPublicacao")
    List<Comentario> findByPublicacao(Integer idPublicacao);
}
