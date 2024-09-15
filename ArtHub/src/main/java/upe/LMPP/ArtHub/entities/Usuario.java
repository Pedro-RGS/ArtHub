package upe.LMPP.ArtHub.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import upe.LMPP.ArtHub.entities.enums.UsuarioEnum;

import java.util.Date;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String apelido;
    private String email;
    private Date dataNascimento;
    private String senha;
    @Enumerated(EnumType.STRING)
    private UsuarioEnum tipoUsuario;
    private String biografia;
    private Byte [] fotoPerfil;
    private Byte [] banner;
    private String chavePix;
}