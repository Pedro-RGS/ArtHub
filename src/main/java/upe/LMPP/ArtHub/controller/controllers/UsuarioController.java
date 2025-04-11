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
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/buscarApelido/{apelido}")
    public ResponseEntity<Usuario> getUsuarioByApelido(@PathVariable String apelido) {
        Usuario usuario = usuarioService.buscarUsuarioPorApelido(apelido);

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> getUsuarioByEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/dados")
    public ResponseEntity<Usuario> getUsuarioLogado(@RequestHeader("Authorization") String token) {
        try {
            token = token.replace("Bearer ", "");

            String email = tokenService.extractEmailFromToken(token);
            Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);

            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Usuario> putUsuario(@RequestBody Usuario usuarioAtualizado) {
        Usuario usuario = usuarioService.atualizarUsuario(usuarioAtualizado);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/registrar/admin/{id}")
    public ResponseEntity<?> promoverUsuarioParaAdmim(@RequestParam Integer adminId, @PathVariable Integer id) {
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

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        Usuario usuarioRemovido = usuarioService.buscarUsuarioPorId(id);
        usuarioService.removerUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}