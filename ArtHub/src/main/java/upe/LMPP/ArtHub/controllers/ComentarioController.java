package upe.LMPP.ArtHub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upe.LMPP.ArtHub.entities.Comentario;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.exceptions.comentarioExceptions.ComentarioNotFoundPubliExceptiton;
import upe.LMPP.ArtHub.services.interfaces.ComentarioService;

import java.util.List;


@RestController
@RequestMapping("")
public class ComentarioController {

    @Autowired
    ComentarioService comentarioService;

    @PostMapping
    public ResponseEntity<Comentario> publicarComentario(@RequestBody Comentario comentario, @RequestParam int idPubli) {
        try {
            Publicacao publicacao = new Publicacao();
            publicacao.setId(idPubli);
            Comentario comentarioPublicado = comentarioService.publicarComentario(comentario, publicacao);
            return ResponseEntity.ok(comentarioPublicado);
        } catch (ComentarioNotFoundPubliExceptiton e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> removerComentario(@PathVariable Integer idComentario) {
        comentarioService.removerComentario(idComentario);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Comentario> curtirComentario(@PathVariable Integer idComentario) {
        Comentario comentario = new Comentario();
        comentario.setId(idComentario);
        Comentario comentarioCurtido = comentarioService.curtirComentario(comentario);
        return ResponseEntity.ok(comentarioCurtido);
    }

    @GetMapping
    public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable Integer idPublicacao) {
        try {
            Publicacao publicacao = new Publicacao();
            publicacao.setId(idPublicacao);
            List<Comentario> comentarios = comentarioService.listarComentarios(publicacao);
            return ResponseEntity.ok(comentarios);
        } catch (ComentarioNotFoundPubliExceptiton e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

