package upe.LMPP.ArtHub.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import upe.LMPP.ArtHub.entities.enums.CategoriaEnum;
import upe.LMPP.ArtHub.entities.enums.TipoArquivoEnum;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "publicacao")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Publicacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private TipoArquivoEnum tipoArquivo;
    private LocalDateTime dataPublicacao;
    private String legenda;
    private String titulo;
    @Enumerated(EnumType.STRING)
    private CategoriaEnum categoria;
    private Integer curtidas;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToMany(mappedBy = "publicacoesCurtidas")
    private List<Usuario> usuariosQueCurtiram;

}