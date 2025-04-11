package upe.LMPP.ArtHub.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.business.services.interfaces.PerfilService;
import upe.LMPP.ArtHub.infra.entities.Perfil;
import upe.LMPP.ArtHub.infra.entities.Usuario;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/perfis")
public class PefilController {

    private static final String caminhoArquivosPerfis = "C:\\Users\\muril\\OneDrive\\Área de Trabalho\\Engenharia de Software - UPE\\4º Semestre\\Programação para Web (60H)\\ArtHub\\src\\main\\java\\upe\\LMPP\\ArtHub\\arquivos\\perfis";
    private static final String caminhoArquivosBanners = "C:\\Users\\muril\\OneDrive\\Área de Trabalho\\Engenharia de Software - UPE\\4º Semestre\\Programação para Web (60H)\\ArtHub\\src\\main\\java\\upe\\LMPP\\ArtHub\\arquivos\\banners";

    @Autowired
    private PerfilService perfilService;

    @PutMapping("/uploadPerfil/{id}")
    public ResponseEntity<Perfil> uploadFotoPerfil(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            Perfil perfil = perfilService.obterPerfil(id);

            // Gerar nome único para o arquivo
            String nomeArquivo = file.getOriginalFilename();
            File destino = new File(caminhoArquivosPerfis + id + "_" + nomeArquivo);
            file.transferTo(destino);

            // Atualizar o caminho no atributo do usuário
            perfil.setFotoPerfil(nomeArquivo);
            perfilService.atualizarPerfil(perfil);

            return ResponseEntity.ok().body(perfil);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/uploadBanner/{id}")
    public ResponseEntity<Perfil> uploadFotoBanner(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            Perfil perfil = perfilService.obterPerfil(id);

            // Gerar nome único para o arquivo
            String nomeArquivo = file.getOriginalFilename();
            File destino = new File(caminhoArquivosBanners + id + "_" + nomeArquivo);
            file.transferTo(destino);

            // Atualizar o caminho no atributo do usuário
            perfil.setBanner(nomeArquivo);
            perfilService.atualizarPerfil(perfil);

            return ResponseEntity.ok().body(perfil);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{userId}/seguir/{seguindoId}")
    public ResponseEntity<Boolean> seguirUsuario(@PathVariable Integer userId, @PathVariable Integer seguindoId) {
        return ResponseEntity.ok(perfilService.seguirUsuario(userId, seguindoId));
    }


    // Trocar para UsuarioDTO
    @GetMapping("/seguidores/{userId}")
    public ResponseEntity<List<Usuario>> listarSeguidores(@PathVariable Integer userId) {
        return ResponseEntity.ok(perfilService.obterSeguidores(userId));
    }

    // Trocar para UsuarioDTO
    @GetMapping("/seguindo/{userId}")
    public ResponseEntity<List<Usuario>> listarSeguindo(@PathVariable Integer userId) {
        return ResponseEntity.ok(perfilService.obterSeguidos(userId));
    }
}
