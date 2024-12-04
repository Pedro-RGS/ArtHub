package upe.LMPP.ArtHub.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.entities.Usuario;
import upe.LMPP.ArtHub.exceptions.usuarioExceptions.UsuarioExistenteException;
import upe.LMPP.ArtHub.exceptions.usuarioExceptions.UsuarioInexistenteException;
import upe.LMPP.ArtHub.repositories.UsuarioRepository;
import upe.LMPP.ArtHub.security.TokenService;
import upe.LMPP.ArtHub.services.interfaces.UsuarioService;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private static String caminhoArquivosPerfis = "C:\\Users\\muril\\OneDrive\\Área de Trabalho\\Engenharia de Software - UPE\\4º Semestre\\Programação para Web (60H)\\ArtHub\\src\\main\\java\\upe\\LMPP\\ArtHub\\arquivos\\perfis";
    private static String caminhoArquivosBanners = "C:\\Users\\muril\\OneDrive\\Área de Trabalho\\Engenharia de Software - UPE\\4º Semestre\\Programação para Web (60H)\\ArtHub\\src\\main\\java\\upe\\LMPP\\ArtHub\\arquivos\\banners";

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;


    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);

        return ResponseEntity.created(URI.create("/usuarios/" + usuarioCadastrado.getId())).body(usuarioCadastrado);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Integer id) {
        Usuario usuarioRemovido = usuarioService.buscarUsuarioPorId(id);
        usuarioService.removerUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/apelido/{apelido}")
    public ResponseEntity<Usuario> buscarUsuarioPorApelido(@PathVariable String apelido) {
        Usuario usuario = usuarioService.buscarUsuarioPorApelido(apelido);

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);

        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/uploadPerfil/{id}")
    public ResponseEntity<String> uploadFotoPerfil(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }

            // Gerar nome único para o arquivo
            String nomeArquivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File destino = new File(caminhoArquivosPerfis + nomeArquivo);
            file.transferTo(destino);

            // Atualizar o caminho no atributo do usuário
            usuario.setFotoPerfil(nomeArquivo);
            usuarioService.atualizarUsuario(usuario);

            return ResponseEntity.ok("Foto de perfil enviada com sucesso.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar foto de perfil.");
        }
    }

    @PutMapping("/uploadBanner/{id}")
    public ResponseEntity<String> uploadFotoBanner(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }

            // Gerar nome único para o arquivo
            String nomeArquivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File destino = new File(caminhoArquivosBanners + nomeArquivo);
            file.transferTo(destino);

            // Atualizar o caminho no atributo do usuário
            usuario.setBanner(nomeArquivo);
            usuarioService.atualizarUsuario(usuario);

            return ResponseEntity.ok("Foto do banner enviada com sucesso.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar banner.");
        }
    }

    @GetMapping("/dados")
    public ResponseEntity<Usuario> buscarUsuarioLogado(@RequestHeader("Authorization") String token) {
        try {
            token = token.replace("Bearer ", "");

            String email = tokenService.extractEmailFromToken(token);
            Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);

            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}