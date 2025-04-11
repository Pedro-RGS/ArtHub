package upe.LMPP.ArtHub.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.business.services.interfaces.PerfilService;
import upe.LMPP.ArtHub.infra.entities.Perfil;
import upe.LMPP.ArtHub.infra.entities.Usuario;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/perfis")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @PutMapping("/uploadPerfil/{id}")
    public ResponseEntity<Perfil> uploadFotoPerfil(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(perfilService.uploadFotoPerfil(id, file));
    }

    @PutMapping("/uploadBanner/{id}")
    public ResponseEntity<Perfil> uploadFotoBanner(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(perfilService.uploadFotoBanner(id, file));
    }

    @PutMapping("/{userId}/seguir/{seguindoId}")
    public ResponseEntity<Boolean> seguirUsuario(@PathVariable Integer userId, @PathVariable Integer seguindoId) {
        return ResponseEntity.ok().body(perfilService.seguirUsuario(userId, seguindoId));
    }


    // Trocar para UsuarioDTO
    @GetMapping("/seguidores/{userId}")
    public ResponseEntity<List<Usuario>> listarSeguidores(@PathVariable Integer userId) {
        return ResponseEntity.ok().body(perfilService.obterSeguidores(userId));
    }

    // Trocar para UsuarioDTO
    @GetMapping("/seguindo/{userId}")
    public ResponseEntity<List<Usuario>> listarSeguindo(@PathVariable Integer userId) {
        return ResponseEntity.ok().body(perfilService.obterSeguidos(userId));
    }
}
