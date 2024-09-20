package upe.LMPP.ArtHub.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="comentario")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_publicacao")
    private Publicacao publicacao;
    private Integer curtidas;
    private LocalDateTime dataPublicacao;

    @ManyToMany(mappedBy = "comentariosCurtidos")
    private List<Usuario> usuariosQueCurtiram;
}
