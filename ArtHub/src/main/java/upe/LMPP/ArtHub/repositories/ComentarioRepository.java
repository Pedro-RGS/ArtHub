package upe.LMPP.ArtHub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upe.LMPP.ArtHub.entities.Comentario;
import upe.LMPP.ArtHub.entities.Publicacao;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {


}
