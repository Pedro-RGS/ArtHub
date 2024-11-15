package upe.LMPP.ArtHub.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.entities.enums.CategoriaEnum;

import java.util.List;
import java.util.Optional;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Integer> {
    @Query("SELECT p FROM Publicacao p WHERE p.usuario.id = :idDono")
    List<Publicacao> findByUsuario(Integer idDono);

    List<Publicacao> findByCategoria(CategoriaEnum categoria);
}
