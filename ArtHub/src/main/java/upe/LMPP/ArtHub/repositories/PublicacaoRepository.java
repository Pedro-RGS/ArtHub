package upe.LMPP.ArtHub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import upe.LMPP.ArtHub.entities.Publicacao;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Integer> {
}
