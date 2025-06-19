package upe.LMPP.ArtHub.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.business.services.implementations.MediaService;
import upe.LMPP.ArtHub.business.services.interfaces.PerfilService;
import upe.LMPP.ArtHub.controller.DTO.pefil.PerfilDTO;
import upe.LMPP.ArtHub.controller.DTO.pefil.PerfilEditadoDTO;
import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioDTO;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/perfis")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping("/pesquisar")
    public ResponseEntity<List<PerfilDTO>> pesquisarPerfis(@RequestParam("q") String query) {
        // Agora você precisa criar o método 'pesquisarPerfis' no seu PerfilService
        List<PerfilDTO> resultados = perfilService.pesquisarPerfis(query);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<PerfilDTO> getPerfil(@PathVariable Integer usuarioId ) {
        return ResponseEntity.ok().body(perfilService.getPerfil(usuarioId));
    }

    @GetMapping("/seguidores/{usuarioId}")
    public ResponseEntity<List<UsuarioDTO>> getSeguidores(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok().body(perfilService.obterSeguidores(usuarioId));
    }

    @GetMapping("/seguindo/{usuarioId}")
    public ResponseEntity<List<UsuarioDTO>> getSeguindo(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok().body(perfilService.obterSeguidos(usuarioId));
    }

    @GetMapping("/fotoPerfil/{idPerfil}")
    public ResponseEntity<ByteArrayResource> getImagePerfil(@PathVariable Integer idPerfil){
        PerfilDTO perfil = perfilService.getPerfil(idPerfil);
        MediaType mediaType = MediaService.getMediaType(perfil.fotoPerfil());

        if (mediaType == null){
            return ResponseEntity.ok().body(null);
        }

        ByteArrayResource imagem = perfilService.buscarFotoPerfil(perfil);
        return ResponseEntity.ok().contentType(mediaType).body(imagem);
    }

    @GetMapping("/banner/{idPerfil}")
    public ResponseEntity<ByteArrayResource> getImageBanner(@PathVariable Integer idPerfil){
        PerfilDTO perfil = perfilService.getPerfil(idPerfil);
        MediaType mediaType = MediaService.getMediaType(perfil.banner());

        if (mediaType == null){
            return ResponseEntity.ok().body(null);
        }

        ByteArrayResource imagem = perfilService.buscarFotoBanner(perfil);
        return ResponseEntity.ok().contentType(mediaType).body(imagem);
    }

    @PutMapping("/{donoId}")
    public ResponseEntity<PerfilDTO> atualizarPerfil(@PathVariable Integer donoId,
                                                     @RequestBody PerfilEditadoDTO perfilEditadoDTO) {
        return ResponseEntity.ok().body(perfilService.atualizarPerfil(donoId, perfilEditadoDTO));
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
