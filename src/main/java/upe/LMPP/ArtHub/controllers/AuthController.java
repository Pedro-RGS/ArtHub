package upe.LMPP.ArtHub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import upe.LMPP.ArtHub.entities.DTO.LoginDTO;
import upe.LMPP.ArtHub.entities.DTO.UsuarioDTO;
import upe.LMPP.ArtHub.entities.Usuario;
import upe.LMPP.ArtHub.entities.enums.UsuarioEnum;
import upe.LMPP.ArtHub.security.TokenService;
import upe.LMPP.ArtHub.services.interfaces.UsuarioService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                loginDTO.email(), loginDTO.senha());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((Usuario) auth.getPrincipal());
        System.out.println(token);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> register(@RequestBody UsuarioDTO usuarioDTO){

        Usuario usuario = usuarioService.cadastrarUsuario(usuarioDTO, UsuarioEnum.COMUM);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/resgistrar/admin")
    public ResponseEntity<Usuario> registerAdmin(@RequestBody UsuarioDTO usuarioDTO){

        Usuario usuario = usuarioService.cadastrarUsuario(usuarioDTO, UsuarioEnum.ADMINISTRADOR);
        return ResponseEntity.ok(usuario);
    }
}
