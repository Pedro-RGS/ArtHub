package upe.LMPP.ArtHub.business.services.implementations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    @Value("${PATH_PUBLICACOES}")
    private String caminhoArquivos;

    // metodo para determinar o tipo de extenção do arquivo
    public MediaType getMediaType(String fileName){
        String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        System.out.println(extension);

        return switch (extension) {
            case ".jpg", ".jpeg" -> MediaType.IMAGE_JPEG;
            case ".png" -> MediaType.IMAGE_PNG;
            case ".gif" -> MediaType.IMAGE_GIF;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }

    // metodo para pegar a imagem baseado no path dela
    public byte[] getImage(String filePath) throws IOException {
        Path path = Paths.get(caminhoArquivos).resolve(filePath).normalize();

        if(!Files.exists(path)){
            throw new FileNotFoundException("O arquivo com o seguinte path não foi encontrado: " + filePath);
        }
        return Files.readAllBytes(path);
    }
}
