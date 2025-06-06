package upe.LMPP.ArtHub.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import upe.LMPP.ArtHub.controller.DTO.usuario.LoginDTO;
import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioCriadoDTO;
import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioDTO;
import upe.LMPP.ArtHub.infra.entities.Usuario;
import upe.LMPP.ArtHub.infra.enums.UsuarioEnum;
import upe.LMPP.ArtHub.business.security.TokenService;
import upe.LMPP.ArtHub.business.services.interfaces.UsuarioService;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
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
    public ResponseEntity<UsuarioDTO> register(@Valid @RequestBody UsuarioCriadoDTO usuarioCriadoDTO){
        return ResponseEntity.ok().body(usuarioService.cadastrarUsuario(usuarioCriadoDTO, UsuarioEnum.COMUM));
    }

    @PostMapping("/registrar/admin")
    public ResponseEntity<UsuarioDTO> registerAdmin(@Valid @RequestBody UsuarioCriadoDTO usuarioCriadoDTO){
        return ResponseEntity.ok().body(usuarioService.cadastrarUsuario(usuarioCriadoDTO, UsuarioEnum.ADMINISTRADOR));
    }
}
