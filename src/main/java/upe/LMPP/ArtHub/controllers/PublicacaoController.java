package upe.LMPP.ArtHub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.entities.Publicacao;
import upe.LMPP.ArtHub.entities.enums.CategoriaEnum;
import upe.LMPP.ArtHub.services.interfaces.PublicacaoService;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/publicacoes")
public class PublicacaoController {

    private static String caminhoArquivos = "C:\\Users\\muril\\OneDrive\\Área de Trabalho\\Engenharia de Software - UPE\\4º Semestre\\Programação para Web (60H)\\ArtHub\\src\\main\\java\\upe\\LMPP\\ArtHub\\arquivos\\publicacoes";

    @Autowired
    PublicacaoService publicacaoService;

    @GetMapping
    public ResponseEntity<List<Publicacao>> getAllPublicacao(){
        List<Publicacao> paginas = publicacaoService.buscarTodasPublicacacoes();

        return ResponseEntity.ok().body(paginas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publicacao> getPublicacao(@PathVariable Integer id){
        Publicacao publicacaoEncontrada = publicacaoService.buscarPublicacao(id);
        return ResponseEntity.ok().body(publicacaoEncontrada);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Publicacao>> getPublicacaoByUsuario(@PathVariable Integer idUsuario){
        List<Publicacao> publicacaoEncontrada = publicacaoService.buscarPublicacoesPorUsuario(idUsuario);
        return ResponseEntity.ok().body(publicacaoEncontrada);
    }

    @GetMapping("categoria/{categoria}")
    public ResponseEntity<List<Publicacao>> getPublicacaoByCategoria(@PathVariable CategoriaEnum categoria){
        List<Publicacao> publicacoesEncontradas = publicacaoService.buscarPublicacaoPorCategoria(categoria);
        return ResponseEntity.ok().body(publicacoesEncontradas);
    }

    @PostMapping("/{idDono}")
    public ResponseEntity<Publicacao> postPublicacao(@RequestBody Publicacao publicacao,
                                                     @PathVariable Integer idDono) {
        Publicacao novaPublicacao = publicacaoService.criarPublicacao(publicacao, idDono);

        return ResponseEntity.created(URI.create("/publicacoes/" + novaPublicacao.getId())).body(novaPublicacao);
    }

    @PutMapping("/add-media/{id}")
    public ResponseEntity<Publicacao> addMedia(@PathVariable Integer id,
                                               @RequestParam("file") MultipartFile arquivo) {
        try {
            Publicacao publicacao = publicacaoService.buscarPublicacao(id);

            if (arquivo != null && !arquivo.isEmpty()) {
                byte[] bytes = arquivo.getBytes();
                Path caminho = Paths.get(caminhoArquivos + id + "_" + arquivo.getOriginalFilename());
                Files.write(caminho, bytes);

                // Atualiza o campo nomeConteudo na publicação
                publicacao.setNomeConteudo(id + "_" + arquivo.getOriginalFilename());
                Publicacao atualizada = publicacaoService.atualizarPublicacao(publicacao, publicacao.getUsuario().getId());

                return ResponseEntity.ok().body(atualizada);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/{idDono}")
    public ResponseEntity<Publicacao> putPublicacao(@RequestBody Publicacao publicacao,
                                                    @PathVariable Integer idDono){
        return ResponseEntity.ok().body(publicacaoService.atualizarPublicacao(publicacao, idDono));
    }

    @PutMapping("cutir/{idPublicacao}")
    public ResponseEntity<Publicacao> curtirPublicacao(@PathVariable Integer idPublicacao){
        publicacaoService.curtirPublicacao(idPublicacao);
        return ResponseEntity.ok().body(publicacaoService.buscarPublicacao(idPublicacao));
    }

    @PutMapping("descurtir/{idPublicacao}")
    public ResponseEntity<Publicacao> descurtirPublicacao(@PathVariable Integer idPublicacao){
        publicacaoService.descurtirPublicacao(idPublicacao);
        return ResponseEntity.ok().body(publicacaoService.buscarPublicacao(idPublicacao));
    }

    @DeleteMapping("remover/{idUsuario}/{idPublicacao}")
    public ResponseEntity<Void> deletePublicacao(@PathVariable Integer idUsuario,
                                                 @PathVariable Integer idPublicacao){
        publicacaoService.excluirPublicacao(idPublicacao, idUsuario);
        return ResponseEntity.noContent().build();
    }
}
