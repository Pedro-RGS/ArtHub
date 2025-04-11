package upe.LMPP.ArtHub.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upe.LMPP.ArtHub.infra.entities.Usuario;
import upe.LMPP.ArtHub.business.services.interfaces.UsuarioService;


@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(usuarioService.buscarUsuarioPorId(id));
    }

    @GetMapping("/buscarApelido/{apelido}")
    public ResponseEntity<Usuario> getUsuarioByApelido(@PathVariable String apelido) {
        return ResponseEntity.ok().body(usuarioService.buscarUsuarioPorApelido(apelido));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> getUsuarioByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(usuarioService.buscarUsuarioPorEmail(email));
    }

    @GetMapping("/dados")
    public ResponseEntity<Usuario> getUsuarioLogado(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(usuarioService.buscarUsuarioLogado(token));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Usuario> putUsuario(@RequestBody Usuario usuarioAtualizado) {
        return ResponseEntity.ok().body(usuarioService.atualizarUsuario(usuarioAtualizado));
    }

    @PutMapping("/registrar/admin/{id}")
    public ResponseEntity<?> promoverUsuarioParaAdmim(@RequestParam Integer adminId, @PathVariable Integer id) {
        return ResponseEntity.ok().body(usuarioService.promoverUsuarioParaAdmim(adminId, id));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.removerUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}