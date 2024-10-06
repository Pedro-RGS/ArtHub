package upe.LMPP.ArtHub.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upe.LMPP.ArtHub.dtos.ComentarioDTO;
import upe.LMPP.ArtHub.services.comentarioService.ComentarioServiceImpl;

// Foram implementados todos os métodas as assinaturas dos métodos
// necessários, agora é preciso desenvolver o service completamente
// para implementar os métodos
@RestController
@Getter
@Setter
@AllArgsConstructor
public class ComentarioController {

    private ComentarioServiceImpl service;

    // Por causa de cada comentário estar atrelado a um post
    // é necessários que seja passado o id do post na URI
    @GetMapping("/{id_publicacao}/comentarios")
    public ResponseEntity<ComentarioDTO> getAll(
            @PathVariable(value = "id_publicacao") Integer id){

    }

    @GetMapping("/{id_publicacao}/{id}")
    public ResponseEntity<ComentarioDTO> getById(
            @PathVariable(value = "id_publicacao") Integer idPublicacao,
            @PathVariable(value = "id") Integer idComentario){

    }

    @PutMapping("/{id_publicacao}")
    public ResponseEntity<ComentarioDTO> create(
            @RequestBody ComentarioDTO dto,
            @PathVariable(value = "id_publicacao") Integer id){

    }

    @DeleteMapping("{id_publicacao}/{id}")
    public ResponseEntity<ComentarioDTO> delete(
            @PathVariable(value = "id_publicacao") Integer idPublicacao,
            @PathVariable(value = "id") Integer idComentario){

    }

    // Essa classe não o usará o PUT, pois não será possível
    // editar comentários realizados, apenas deletálos.
}
