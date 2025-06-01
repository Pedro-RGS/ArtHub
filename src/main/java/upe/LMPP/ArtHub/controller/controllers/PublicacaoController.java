package upe.LMPP.ArtHub.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import upe.LMPP.ArtHub.business.services.implementations.ImageService;
import upe.LMPP.ArtHub.controller.DTO.publicacao.PublicacaoCriadaDTO;
import upe.LMPP.ArtHub.controller.DTO.publicacao.PublicacaoDTO;
import upe.LMPP.ArtHub.controller.DTO.publicacao.PublicacaoEditadaDTO;
import upe.LMPP.ArtHub.infra.enums.CategoriaEnum;
import upe.LMPP.ArtHub.business.services.interfaces.PublicacaoService;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/publicacoes")
public class PublicacaoController {

    @Autowired
    PublicacaoService publicacaoService;

    @GetMapping
    public ResponseEntity<List<PublicacaoDTO>> getAllPublicacao(@RequestParam(required = false, defaultValue = "0") int pagina,
                                                                @RequestParam(required = false, defaultValue = "14") int itens){
        return ResponseEntity.ok().body(publicacaoService.buscarTodasPublicacacoes(pagina, itens));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacaoDTO> getPublicacao(@PathVariable Integer id){
        return ResponseEntity.ok().body(publicacaoService.buscarPublicacao(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PublicacaoDTO>> getPublicacaoByUsuario(@PathVariable Integer idUsuario){
        return ResponseEntity.ok().body(publicacaoService.buscarPublicacoesPorUsuario(idUsuario));
    }

    @GetMapping("categoria/{categoria}")
    public ResponseEntity<List<PublicacaoDTO>> getPublicacaoByCategoria(@PathVariable CategoriaEnum categoria,
                                                                        @RequestParam(required = false, defaultValue = "1") int pagina,
                                                                        @RequestParam(required = false, defaultValue = "8") int itens){
        return ResponseEntity.ok().body(publicacaoService.buscarPublicacaoPorCategoria(categoria, pagina, itens));
    }

    @GetMapping("/imagem/{idPublicacao}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable Integer idPublicacao){
        PublicacaoDTO publicacao = publicacaoService.buscarPublicacao(idPublicacao);
        MediaType mediaType = ImageService.getMediaType(publicacao.nomeConteudo());
        ByteArrayResource imagem = publicacaoService.buscarImagem(publicacao);

        return ResponseEntity.ok().contentType(mediaType).body(imagem);
    }

    @PostMapping("/{idDono}")
    public ResponseEntity<PublicacaoDTO> postPublicacao(@RequestBody PublicacaoCriadaDTO publicacaoDTO,
                                                     @PathVariable Integer idDono) {
        PublicacaoDTO novaPublicacao = publicacaoService.criarPublicacao(publicacaoDTO, idDono);

        return ResponseEntity.created(URI.create("/publicacoes/" + novaPublicacao.titulo())).body(novaPublicacao);
    }

    @PutMapping("/add-media/{id}")
    public ResponseEntity<PublicacaoDTO> addMedia(@PathVariable Integer id,
                                               @RequestParam("file") MultipartFile arquivo) {
        return ResponseEntity.ok().body(publicacaoService.addMedia(id, arquivo));
    }

    @PutMapping("/{idDono}")
    public ResponseEntity<PublicacaoDTO> putPublicacao(@RequestBody PublicacaoEditadaDTO publicacaoDTO,
                                                    @PathVariable Integer idDono){
        return ResponseEntity.ok().body(publicacaoService.atualizarPublicacao(publicacaoDTO, idDono));
    }

    @PutMapping("curtir/{idPublicacao}/{idPerfil}")
    public ResponseEntity<PublicacaoDTO> curtirPublicacao(@PathVariable Integer idPublicacao, @PathVariable Integer idPerfil){
        return ResponseEntity.ok().body(publicacaoService.curtirPublicacao(idPublicacao, idPerfil));
    }

    @PutMapping("descurtir/{idPublicacao}/{idPerfil}")
    public ResponseEntity<PublicacaoDTO> descurtirPublicacao(@PathVariable Integer idPublicacao, @PathVariable Integer idPerfil){
        return ResponseEntity.ok().body(publicacaoService.descurtirPublicacao(idPublicacao, idPerfil));
    }

    @DeleteMapping("remover/{idUsuario}/{idPublicacao}")
    public ResponseEntity<Void> deletePublicacao(@PathVariable Integer idUsuario,
                                                 @PathVariable Integer idPublicacao){
        publicacaoService.excluirPublicacao(idPublicacao, idUsuario);
        return ResponseEntity.noContent().build();
    }
}
