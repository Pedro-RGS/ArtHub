package upe.LMPP.ArtHub.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioDTO;
import upe.LMPP.ArtHub.controller.DTO.usuario.UsuarioEditadoDTO;
import upe.LMPP.ArtHub.business.services.interfaces.UsuarioService;


@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(usuarioService.buscarUsuarioPorId(id));
    }

    @GetMapping("/buscarApelido/{apelido}")
    public ResponseEntity<UsuarioDTO> getUsuarioByApelido(@PathVariable String apelido) {
        return ResponseEntity.ok().body(usuarioService.buscarUsuarioPorApelido(apelido));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> getUsuarioByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(usuarioService.buscarUsuarioPorEmail(email));
    }

    @GetMapping("/dados")
    public ResponseEntity<UsuarioDTO> getUsuarioLogado(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(usuarioService.buscarUsuarioLogado(token));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody UsuarioEditadoDTO usuarioAtualizado) {
        return ResponseEntity.ok().body(usuarioService.atualizarUsuario(usuarioAtualizado));
    }

    @PutMapping("/promover/admin/{adminId}/{id}")
    public ResponseEntity<UsuarioDTO> promoverUsuarioParaAdmim(@RequestParam Integer adminId, @PathVariable Integer id) {
        return ResponseEntity.ok().body(usuarioService.promoverUsuarioParaAdmim(adminId, id));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.removerUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}