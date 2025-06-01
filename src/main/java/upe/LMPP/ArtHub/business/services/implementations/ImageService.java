package upe.LMPP.ArtHub.business.services.implementations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    // metodo para determinar o tipo de extenção do arquivo
    public static MediaType getMediaType(String fileName){
        String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        return switch (extension) {
            case ".jpg", ".jpeg" -> MediaType.IMAGE_JPEG;
            case ".png" -> MediaType.IMAGE_PNG;
            case ".gif" -> MediaType.IMAGE_GIF;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }

    // metodo para pegar a imagem baseado no path dela
    public ByteArrayResource getImage(String fileName, String filePath) throws IOException {
        Path path = Paths.get(filePath).resolve(fileName).normalize();
        if(!Files.exists(path)){
            throw new FileNotFoundException("O arquivo com o seguinte path não foi encontrado: " + fileName);
        }
        byte[] imagem = Files.readAllBytes(path);
        return new ByteArrayResource(imagem);
    }
}
