package upe.LMPP.ArtHub.entities;


import jakarta.persistence.*;
import lombok.*;
import upe.LMPP.ArtHub.entities.enums.CategoriaEnum;
import upe.LMPP.ArtHub.entities.enums.TipoArquivoEnum;

import java.time.LocalDateTime;

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
    private LocalDateTime data_hora;
    private String legenda;
    private String titulo;
    @Enumerated(EnumType.STRING)
    private CategoriaEnum categoria;
    private Integer curtidas;
    @ManyToOne
    @JoinTable(name = "usuario")
    private int id_usuario;

}
