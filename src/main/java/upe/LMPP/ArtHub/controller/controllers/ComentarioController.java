package upe.LMPP.ArtHub.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upe.LMPP.ArtHub.controller.DTO.comentario.ComentarioCriadoDTO;
import upe.LMPP.ArtHub.controller.DTO.comentario.ComentarioDTO;
import upe.LMPP.ArtHub.business.services.interfaces.ComentarioService;

import java.net.URI;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    ComentarioService comentarioService;

    @GetMapping("publicacao/{idPublicacao}")
    public ResponseEntity<List<ComentarioDTO>> getAllComentariosFromPublicacao(@PathVariable Integer idPublicacao) {
        return ResponseEntity.ok().body(comentarioService.listarComentarios(idPublicacao));
    }

    @GetMapping("/{idComentario}")
    public ResponseEntity<ComentarioDTO> getComentario(@PathVariable Integer idComentario){
        return ResponseEntity.ok().body(comentarioService.buscarPorId(idComentario));
    }

    @PostMapping("/{idDono}/{idPublicacao}")
    public ResponseEntity<ComentarioDTO> postComentario(@PathVariable Integer idDono,
                                                        @PathVariable Integer idPublicacao,
                                                        @RequestBody ComentarioCriadoDTO comentario) {
        ComentarioDTO comentarioPublicado = comentarioService.publicarComentario(comentario, idDono, idPublicacao);

        return ResponseEntity.created(URI.create("/comentarios/" + comentarioPublicado.perfil().getUsuario()))
                .body(comentarioPublicado);
    }

    @PutMapping("/curtir/{idComentario}")
    public ResponseEntity<ComentarioDTO> curtirComentario(@PathVariable Integer idComentario) {
        return ResponseEntity.ok().body(comentarioService.curtirComentario(idComentario));
    }

    @PutMapping("/descurtir/{idComentario}")
    public ResponseEntity<ComentarioDTO> descurtirComentario(@PathVariable Integer idComentario) {
        return ResponseEntity.ok().body(comentarioService.descurtirComentario(idComentario));
    }

    @DeleteMapping("remover/{idComentario}")
    public ResponseEntity<Void> removerComentario(@PathVariable Integer idComentario) {
        comentarioService.removerComentario(idComentario);
        return ResponseEntity.noContent().build();
    }
}

