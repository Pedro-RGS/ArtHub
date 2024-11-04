package upe.LMPP.ArtHub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import upe.LMPP.ArtHub.entities.enums.UsuarioEnum;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String apelido;
    private String email;
    private Date dataNascimento;
    private String senha;

    @Enumerated(EnumType.STRING)
    private UsuarioEnum tipoUsuario;

    private String biografia;
    private byte[] fotoPerfil;
    private byte[] banner;
    private String chavePix;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Telefone> telefones;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Publicacao> publicacoes;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Comentario> comentarios;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "curtir_publicacao",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_publicacao")
    )
    private List<Publicacao> publicacoesCurtidas;
}