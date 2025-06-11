package upe.LMPP.ArtHub.business.services.implementations;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MediaService {

    // metodo para determinar o tipo de extenção do arquivo
    public static MediaType getMediaType(String fileName){
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }

        String extension = "";
        int dotIndex = fileName.lastIndexOf(".");

        if (dotIndex > 0){
            extension = fileName.substring(dotIndex).toLowerCase();
        }
        return switch (extension) {
            case ".jpg", ".jpeg" -> MediaType.IMAGE_JPEG;
            case ".png" -> MediaType.IMAGE_PNG;
            case ".gif" -> MediaType.IMAGE_GIF;
            case ".mp4" -> MediaType.valueOf("video/mp4");
            case ".webm" -> MediaType.valueOf("video/webm");
            case ".mp3" -> MediaType.valueOf("audio/mpeg");
            case ".wav" -> MediaType.valueOf("audio/wav");
            case ".ogg" -> MediaType.valueOf("audio/ogg");
            case ".opus" -> MediaType.valueOf("audio/opus");
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }

    // metodo para pegar a imagem baseado no path dela
    public ByteArrayResource getFile(String fileName, String filePath) throws IOException {
        Path path = Paths.get(filePath).resolve(fileName).normalize();
        if(!Files.exists(path)){
            throw new FileNotFoundException("O arquivo com o seguinte path não foi encontrado: " + fileName);
        }
        byte[] fileBytes = Files.readAllBytes(path);
        return new ByteArrayResource(fileBytes);
    }
}
