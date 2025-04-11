package upe.LMPP.ArtHub.infra.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String biografia;
    private String fotoPerfil;
    private String banner;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "perfil")
    private List<Publicacao> publicacoes;

    @JsonIgnore
    @OneToMany(mappedBy = "perfil")
    private List<Comentario> comentarios;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "curtir_publicacao",
            joinColumns = @JoinColumn(name = "id_perfil"),
            inverseJoinColumns = @JoinColumn(name = "id_publicacao")
    )
    private List<Publicacao> publicacoesCurtidas;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "perfil_seguindo",
            joinColumns = @JoinColumn(name = "perfil_id"),
            inverseJoinColumns = @JoinColumn(name = "seguindo_id")
    )
    private List<Perfil> seguindo = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "seguindo")
    private List<Perfil> seguidores = new ArrayList<>();
}
