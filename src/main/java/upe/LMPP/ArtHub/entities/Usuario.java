package upe.LMPP.ArtHub.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import upe.LMPP.ArtHub.entities.enums.UsuarioEnum;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario implements UserDetails {
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
    private String fotoPerfil;
    private String banner;
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

    public Usuario(String nome, String apelido, String email, Date dataNascimento, String senha){
        this.nome = nome;
        this.apelido = apelido;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.senha = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.tipoUsuario == UsuarioEnum.ADMINISTRADOR){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"), new SimpleGrantedAuthority("ROLE_COMUM"));

        }
        return List.of(new SimpleGrantedAuthority("ROLE_COMUM"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.apelido;
    }

}