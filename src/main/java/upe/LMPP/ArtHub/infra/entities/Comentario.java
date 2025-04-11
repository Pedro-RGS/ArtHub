package upe.LMPP.ArtHub.infra.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private Integer curtidas;
    private LocalDateTime dataPublicacao;
    private String conteudo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_publicacao")
    private Publicacao publicacao;
}