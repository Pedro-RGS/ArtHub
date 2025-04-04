package upe.LMPP.ArtHub.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upe.LMPP.ArtHub.infra.entities.Usuario;
import upe.LMPP.ArtHub.business.security.TokenService;
import upe.LMPP.ArtHub.infra.enums.UsuarioEnum;
import upe.LMPP.ArtHub.business.services.interfaces.UsuarioService;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuarioAtualizado) {
        Usuario usuario = usuarioService.atualizarUsuario(usuarioAtualizado);
        return ResponseEntity.ok(usuario);
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

    @GetMapping("/buscarApelido/{apelido}")
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
            String nomeArquivo = file.getOriginalFilename();
            File destino = new File(caminhoArquivosPerfis + id + "_" + nomeArquivo);
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
            String nomeArquivo = file.getOriginalFilename();
            File destino = new File(caminhoArquivosBanners + id + "_" + nomeArquivo);
            file.transferTo(destino);

            // Atualizar o caminho no atributo do usuário
            usuario.setBanner(nomeArquivo);
            usuarioService.atualizarUsuario(usuario);

            return ResponseEntity.ok("Foto do banner enviada com sucesso.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar banner.");
        }
    }

    @PutMapping("/registrar/admin/{id}")
    public ResponseEntity<?> registrarAdmin(@RequestParam Integer adminId, @PathVariable Integer id) {
        Usuario adminNomeador = usuarioService.buscarUsuarioPorId(adminId);

        if (adminNomeador == null || adminNomeador.getTipoUsuario() != UsuarioEnum.ADMINISTRADOR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário não autorizado para promover admins.");
        }

        Usuario usuarioPromovido = usuarioService.buscarUsuarioPorId(id);

        if (usuarioPromovido == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        usuarioPromovido.setTipoUsuario(UsuarioEnum.ADMINISTRADOR);
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuarioPromovido);

        return ResponseEntity.ok(usuarioAtualizado);
    }


    @PutMapping("/{userId}/seguir/{seguindoId}")
    public ResponseEntity<String> seguirUsuario(@PathVariable Integer userId, @PathVariable Integer seguindoId) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(userId);
        Usuario seguindo = usuarioService.buscarUsuarioPorId(seguindoId);

        if (usuario == null || seguindo == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        if (usuario.equals(seguindo)) {
            return ResponseEntity.badRequest().body("Um usuário não pode seguir a si mesmo.");
        }

        boolean sucesso = usuarioService.seguirUsuario(usuario, seguindo);
        if (sucesso) {
            return ResponseEntity.ok("Agora você está seguindo " + seguindo.getNome() + ".");
        }
        return ResponseEntity.badRequest().body("Você já está seguindo este usuário.");
    }

    @GetMapping("/seguidores/{userId}")
    public ResponseEntity<List<Usuario>> listarSeguidores(@PathVariable Integer userId) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(userId);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario.getSeguidores());
    }

    @GetMapping("/seguindo/{userId}")
    public ResponseEntity<List<Usuario>> listarSeguindo(@PathVariable Integer userId) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(userId);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario.getSeguindo());
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