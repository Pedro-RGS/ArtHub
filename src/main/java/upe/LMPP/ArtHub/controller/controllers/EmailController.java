package upe.LMPP.ArtHub.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upe.LMPP.ArtHub.controller.DTO.EmailDTO;
import upe.LMPP.ArtHub.business.services.implementations.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarEmail(@RequestBody EmailDTO emailDTO) {
        emailService.enviarEmail(
            emailDTO.getDestino(),
            emailDTO.getAssunto(),
            emailDTO.getCorpo()
        );
        return ResponseEntity.ok("E-mail enviado com sucesso!");
    }
}

