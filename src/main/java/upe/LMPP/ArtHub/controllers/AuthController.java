package upe.LMPP.ArtHub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import upe.LMPP.ArtHub.entities.DTO.LoginDTO;
import upe.LMPP.ArtHub.entities.DTO.UsuarioDTO;
import upe.LMPP.ArtHub.entities.Usuario;
import upe.LMPP.ArtHub.entities.enums.UsuarioEnum;
import upe.LMPP.ArtHub.security.TokenService;
import upe.LMPP.ArtHub.services.interfaces.UsuarioService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                loginDTO.email(), loginDTO.senha());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((Usuario) auth.getPrincipal());;
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/registrar")
    public ResponseEntity register(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario(usuarioDTO.nome(),
                usuarioDTO.apelido(), usuarioDTO.email(),
                usuarioDTO.dataNascimento(), usuarioDTO.senha());

        usuario.setTipoUsuario(UsuarioEnum.COMUM);

        usuario = usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/resgistrar/admin")
    public ResponseEntity registerAdmin(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario(usuarioDTO.nome(),
                usuarioDTO.apelido(), usuarioDTO.email(),
                usuarioDTO.dataNascimento(), usuarioDTO.senha());

        usuario.setTipoUsuario(UsuarioEnum.ADMINISTRADOR);

        usuario = usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.ok(usuario);
    }
}
