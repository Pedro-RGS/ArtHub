package upe.LMPP.ArtHub.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upe.LMPP.ArtHub.entities.Usuario;
import upe.LMPP.ArtHub.exceptions.usuarioExceptions.UsuarioExistenteException;
import upe.LMPP.ArtHub.exceptions.usuarioExceptions.UsuarioInexistenteException;
import upe.LMPP.ArtHub.services.interfaces.UsuarioService;

import java.net.URI;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

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
}
