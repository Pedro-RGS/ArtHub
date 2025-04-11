package upe.LMPP.ArtHub.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upe.LMPP.ArtHub.infra.entities.Comentario;
import upe.LMPP.ArtHub.business.services.interfaces.ComentarioService;

import java.net.URI;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    ComentarioService comentarioService;

    @GetMapping("/{idPublicacao}")
    public ResponseEntity<List<Comentario>> getAllComentariosFromPublicacao(@PathVariable Integer idPublicacao) {
        return ResponseEntity.ok().body(comentarioService.listarComentarios(idPublicacao));
    }

    @GetMapping("/{idComentario}")
    public ResponseEntity<Comentario> getComentario(@PathVariable Integer idComentario){
        return ResponseEntity.ok().body(comentarioService.buscarPorId(idComentario));
    }

    @PostMapping("/{idUsuario}/{idPublicacao}")
    public ResponseEntity<Comentario> postComentario(@RequestBody Comentario comentario,
                                                         @PathVariable Integer idUsuario,
                                                         @PathVariable Integer idPublicacao) {
        Comentario comentarioPublicado = comentarioService.publicarComentario(comentario, idUsuario, idPublicacao);

        return ResponseEntity.created(URI.create("/comentarios/" +
                comentarioPublicado.getId())).body(comentarioPublicado);
    }

    @PutMapping("/curtir/{idComentario}")
    public ResponseEntity<Comentario> curtirComentario(@PathVariable Integer idComentario) {
        return ResponseEntity.ok().body(comentarioService.curtirComentario(idComentario));
    }

    @PutMapping("/descurtir/{idComentario}")
    public ResponseEntity<Comentario> descurtirComentario(@PathVariable Integer idComentario) {
        return ResponseEntity.ok().body(comentarioService.descurtirComentario(idComentario));
    }

    @DeleteMapping("remover/{idComentario}")
    public ResponseEntity<Void> removerComentario(@PathVariable Integer idComentario) {
        comentarioService.removerComentario(idComentario);
        return ResponseEntity.noContent().build();
    }
}

