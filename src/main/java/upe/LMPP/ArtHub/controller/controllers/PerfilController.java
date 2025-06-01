package upe.LMPP.ArtHub.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.business.services.interfaces.PerfilService;
import upe.LMPP.ArtHub.controller.DTO.pefil.PerfilDTO;
import upe.LMPP.ArtHub.controller.DTO.pefil.PerfilEditadoDTO;
import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioDTO;
import upe.LMPP.ArtHub.infra.entities.Perfil;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/perfis")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping("/{perfilId}")
    public ResponseEntity<PerfilDTO> getPerfil(@PathVariable Integer perfilId ) {
        return ResponseEntity.ok().body(perfilService.getPerfil(perfilId));

    }

    @GetMapping("/seguidores/{perfilId}")
    public ResponseEntity<List<UsuarioDTO>> getSeguidores(@PathVariable Integer perfilId) {
        return ResponseEntity.ok().body(perfilService.obterSeguidores(perfilId));
    }

    @GetMapping("/seguindo/{perfilId}")
    public ResponseEntity<List<UsuarioDTO>> getSeguindo(@PathVariable Integer perfilId) {
        return ResponseEntity.ok().body(perfilService.obterSeguidos(perfilId));
    }

    @PutMapping("/{donoId}")
    public ResponseEntity<PerfilDTO> atualizarPerfil(@PathVariable Integer donoId,
                                                     @RequestBody PerfilEditadoDTO perfilEditadoDTO) {
        return ResponseEntity.ok().body(perfilService.atualizarBio(donoId, perfilEditadoDTO));
    }

    @PutMapping("/uploadPerfil/{id}")
    public ResponseEntity<PerfilDTO> uploadFotoPerfil(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(perfilService.uploadFotoPerfil(id, file));
    }

    @PutMapping("/uploadBanner/{id}")
    public ResponseEntity<PerfilDTO> uploadFotoBanner(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(perfilService.uploadFotoBanner(id, file));
    }

    @PutMapping("/{perfilId}/seguir/{seguindoId}")
    public ResponseEntity<Boolean> seguirUsuario(@PathVariable Integer perfilId, @PathVariable Integer seguindoId) {
        return ResponseEntity.ok().body(perfilService.seguirUsuario(perfilId, seguindoId));
    }
}
