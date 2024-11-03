package upe.LMPP.ArtHub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private byte[] conteudo;
    private String titulo;
    @Enumerated(EnumType.STRING)
    private CategoriaEnum categoria;
    private Integer curtidas;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @JsonIgnore
    @ManyToMany(mappedBy = "publicacoesCurtidas")
    private List<Usuario> usuariosQueCurtiram;
}