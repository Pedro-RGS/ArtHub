package upe.LMPP.ArtHub.infra.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import upe.LMPP.ArtHub.infra.enums.CategoriaEnum;
import upe.LMPP.ArtHub.infra.enums.TipoArquivoEnum;
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
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TipoArquivoEnum tipoArquivo;
    private LocalDateTime dataPublicacao;
    private String legenda;
    private String nomeConteudo;
    private String titulo;
    private Integer curtidas;

    @Enumerated(EnumType.STRING)
    private CategoriaEnum categoria;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

    @JsonIgnore
    @ManyToMany(mappedBy = "publicacoesCurtidas")
    private List<Perfil> perfisQueCurtiram;
}