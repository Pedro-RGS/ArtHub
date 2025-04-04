package upe.LMPP.ArtHub.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import upe.LMPP.ArtHub.infra.entities.Publicacao;
import upe.LMPP.ArtHub.infra.enums.CategoriaEnum;

import java.util.List;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Integer> {
    @Query("SELECT p FROM Publicacao p WHERE p.usuario.id = :idDono")
    List<Publicacao> findByUsuario(Integer idDono);

    List<Publicacao> findByCategoria(CategoriaEnum categoria);
}
