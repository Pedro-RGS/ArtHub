package upe.LMPP.ArtHub.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.business.services.implementations.ImageService;
import upe.LMPP.ArtHub.business.services.interfaces.PerfilService;
import upe.LMPP.ArtHub.controller.DTO.pefil.PerfilDTO;
import upe.LMPP.ArtHub.controller.DTO.pefil.PerfilEditadoDTO;
import upe.LMPP.ArtHub.controller.DTO.publicacao.PublicacaoDTO;
import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioDTO;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/perfis")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping("/seguidores/{userId}")
    public ResponseEntity<List<UsuarioDTO>> getSeguidores(@PathVariable Integer userId) {
        return ResponseEntity.ok().body(perfilService.obterSeguidores(userId));
    }

    @GetMapping("/seguindo/{userId}")
    public ResponseEntity<List<UsuarioDTO>> getSeguindo(@PathVariable Integer userId) {
        return ResponseEntity.ok().body(perfilService.obterSeguidos(userId));
    }

//    @GetMapping("/imagem/{idPerfil}")
//    public ResponseEntity<ByteArrayResource> getImage(@PathVariable Integer idPerfil){
//        PublicacaoDTO publicacao = perfilService.bus(idPublicacao);
//        MediaType mediaType = ImageService.getMediaType(publicacao.nomeConteudo());
//        ByteArrayResource imagem = publicacaoService.buscarImagem(publicacao);
//
//        return ResponseEntity.ok().contentType(mediaType).body(imagem);
//    }
//
//    @GetMapping("/imagem/{idPublicacao}")
//    public ResponseEntity<ByteArrayResource> getImage(@PathVariable Integer idPublicacao){
//        PublicacaoDTO publicacao = publicacaoService.buscarPublicacao(idPublicacao);
//        MediaType mediaType = ImageService.getMediaType(publicacao.nomeConteudo());
//        ByteArrayResource imagem = publicacaoService.buscarImagem(publicacao);
//
//        return ResponseEntity.ok().contentType(mediaType).body(imagem);
//    }

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

    @PutMapping("/{userId}/seguir/{seguindoId}")
    public ResponseEntity<Boolean> seguirUsuario(@PathVariable Integer userId, @PathVariable Integer seguindoId) {
        return ResponseEntity.ok().body(perfilService.seguirUsuario(userId, seguindoId));
    }
}
