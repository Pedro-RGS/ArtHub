package upe.LMPP.ArtHub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import upe.LMPP.ArtHub.entities.Comentario;
import upe.LMPP.ArtHub.entities.Publicacao;

import java.util.List;
import java.util.Optional;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

    @Query("SELECT c FROM Comentario c WHERE c.publicacao.id = :idPublicacao")
    public List<Comentario> findByPublicacao(Integer idPublicacao);
    public Optional<Comentario> findById(Integer idComentario);
}
