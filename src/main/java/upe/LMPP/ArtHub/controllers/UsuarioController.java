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

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping()
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        try{
            Usuario usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);
            return ResponseEntity.ok(usuarioCadastrado);

        } catch (UsuarioExistenteException e) {
            return ResponseEntity.badRequest().body(usuario);
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario) {
        try{
            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (UsuarioInexistenteException e){
            return ResponseEntity.badRequest().body(usuario);
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Integer id) {
        Usuario usuarioRemovido = usuarioService.buscarUsuarioPorId(id);

        if (usuarioRemovido != null) {
            usuarioService.removerUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/apelido/{apelido}")
    public ResponseEntity<Usuario> buscarUsuarioPorApelido(@PathVariable String apelido) {
        Usuario usuario = usuarioService.buscarUsuarioPorApelido(apelido);

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
