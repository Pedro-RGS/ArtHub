package upe.LMPP.ArtHub.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.controller.DTO.PublicacaoDTO;
import upe.LMPP.ArtHub.controller.DTO.PublicacaoEditadaDTO;
import upe.LMPP.ArtHub.infra.entities.Publicacao;
import upe.LMPP.ArtHub.infra.enums.CategoriaEnum;
import upe.LMPP.ArtHub.business.services.interfaces.PublicacaoService;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/publicacoes")
public class PublicacaoController {

    @Autowired
    PublicacaoService publicacaoService;

    @GetMapping
    public ResponseEntity<List<Publicacao>> getAllPublicacao(){
        return ResponseEntity.ok().body(publicacaoService.buscarTodasPublicacacoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publicacao> getPublicacao(@PathVariable Integer id){
        return ResponseEntity.ok().body(publicacaoService.buscarPublicacao(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Publicacao>> getPublicacaoByUsuario(@PathVariable Integer idUsuario){
        return ResponseEntity.ok().body(publicacaoService.buscarPublicacoesPorUsuario(idUsuario));
    }

    @GetMapping("categoria/{categoria}")
    public ResponseEntity<List<Publicacao>> getPublicacaoByCategoria(@PathVariable CategoriaEnum categoria){
        return ResponseEntity.ok().body(publicacaoService.buscarPublicacaoPorCategoria(categoria));
    }

    @PostMapping("/{idDono}")
    public ResponseEntity<Publicacao> postPublicacao(@RequestBody PublicacaoDTO publicacaoDTO,
                                                     @PathVariable Integer idDono) {
        Publicacao novaPublicacao = publicacaoService.criarPublicacao(publicacaoDTO, idDono);

        return ResponseEntity.created(URI.create("/publicacoes/" + novaPublicacao.getId())).body(novaPublicacao);
    }

    @PutMapping("/add-media/{id}")
    public ResponseEntity<Publicacao> addMedia(@PathVariable Integer id,
                                               @RequestParam("file") MultipartFile arquivo) {
        return ResponseEntity.ok().body(publicacaoService.addMedia(id, arquivo));
    }

    @PutMapping("/{idDono}")
    public ResponseEntity<Publicacao> putPublicacao(@RequestBody PublicacaoEditadaDTO publicacaoDTO,
                                                    @PathVariable Integer idDono){
        return ResponseEntity.ok().body(publicacaoService.atualizarPublicacao(publicacaoDTO, idDono));
    }

    @PutMapping("curtir/{idPublicacao}")
    public ResponseEntity<Publicacao> curtirPublicacao(@PathVariable Integer idPublicacao){
        return ResponseEntity.ok().body(publicacaoService.curtirPublicacao(idPublicacao));
    }

    @PutMapping("descurtir/{idPublicacao}")
    public ResponseEntity<Publicacao> descurtirPublicacao(@PathVariable Integer idPublicacao){
        return ResponseEntity.ok().body(publicacaoService.descurtirPublicacao(idPublicacao));
    }

    @DeleteMapping("remover/{idUsuario}/{idPublicacao}")
    public ResponseEntity<Void> deletePublicacao(@PathVariable Integer idUsuario,
                                                 @PathVariable Integer idPublicacao){
        publicacaoService.excluirPublicacao(idPublicacao, idUsuario);
        return ResponseEntity.noContent().build();
    }
}
