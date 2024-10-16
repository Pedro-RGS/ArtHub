package upe.LMPP.ArtHub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.services.interfaces.PublicacaoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/publicacoes")
public class PublicacaoController {

    @Autowired
    PublicacaoService publicacaoService;

    @GetMapping
    public ResponseEntity<List<Publicacao>> getAllPublicacao(Pageable pageable){
        List<Publicacao> paginas = publicacaoService.buscarTodasPublicacacoes();

        return ResponseEntity.ok().body(paginas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publicacao> getPublicacao(@PathVariable Integer id){
        Publicacao publicacaoEncontrada = publicacaoService.buscarPublicacao(id);
        return ResponseEntity.ok().body(publicacaoEncontrada);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Publicacao>> getPublicacaoByUsuario(@PathVariable Integer idUsuario){
        List<Publicacao> publicacaoEncontrada = publicacaoService.buscarPublicacoesPorUsuario(idUsuario);
        return ResponseEntity.ok().body(publicacaoEncontrada);
    }

    @PostMapping
    public ResponseEntity<Publicacao> postPublicacao(@RequestBody Publicacao publicacao){
        Publicacao novaPublicacao = publicacaoService.criarPublicacao(publicacao);
        return ResponseEntity.created(URI.create("/publicacoes/" + novaPublicacao.getId())).body(novaPublicacao);
    }

    @PutMapping("/{idDono}")
    public ResponseEntity<Publicacao> putPublicacao(@RequestBody Publicacao publicacao,
                                                    @PathVariable Integer idDono){
        return ResponseEntity.ok().body(publicacaoService.atualizarPublicacao(publicacao, idDono));
    }

    @DeleteMapping("delete/{idUsuario}/{idPublicacao}")
    public ResponseEntity<Void> deletePublicacao(@PathVariable Integer idUsuario,
                                                 @PathVariable Integer idPublicacao){
        publicacaoService.excluirPublicacao(idPublicacao, idUsuario);
        return ResponseEntity.noContent().build();
    }
}
