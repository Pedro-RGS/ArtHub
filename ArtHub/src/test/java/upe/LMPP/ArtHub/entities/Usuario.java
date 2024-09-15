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
    private Date data_nascimento;
    private String senha;
    @Enumerated(EnumType.STRING)
    private UsuarioEnum tipo_usuario;
    private String biografia;
    private Byte [] fotoperfil;
    private Byte [] banner;
    private String chave_pix;
}