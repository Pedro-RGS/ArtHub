package upe.LMPP.ArtHub.infra.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import upe.LMPP.ArtHub.infra.enums.UsuarioEnum;

import java.util.ArrayList;
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
    private String telefone;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Perfil perfil;

    @Enumerated(EnumType.STRING)
    private UsuarioEnum tipoUsuario;

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
