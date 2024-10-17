package upe.LMPP.ArtHub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upe.LMPP.ArtHub.entities.Comentario;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.services.interfaces.ComentarioService;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("api/v1/comentarios")
public class ComentarioController {

    @Autowired
    ComentarioService comentarioService;

    @PostMapping
    public ResponseEntity<Comentario> publicarComentario(@RequestBody Comentario comentario, @RequestParam Publicacao publicacao) {
        Comentario comentarioPublicado = comentarioService.publicarComentario(comentario, publicacao);
        return ResponseEntity.created(URI.create("/comentarios/" + comentarioPublicado.getId())).body(comentarioPublicado);
    }

    @DeleteMapping("delete/{idComentario}")
    public ResponseEntity<Void> removerComentario(@PathVariable Integer idComentario) {
        comentarioService.removerComentario(idComentario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idComentario}")
    public ResponseEntity<Comentario> curtirComentario(@PathVariable Integer idComentario) {
        return ResponseEntity.ok(comentarioService.curtirComentario(idComentario));
    }

    @GetMapping("/{idPublicacao}")
    public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable Integer idPublicacao) {
        List<Comentario> comentarios = comentarioService.listarComentarios(idPublicacao);
        return ResponseEntity.ok(comentarios);
    }
}

