package itu.services;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageServices {
    private final String UPLOAD_DIR = "/upload";
    public List<String> uploadImage(List<MultipartFile> file) throws IOException {
        List<String> ret = new ArrayList<>();
        String fileName = "";
        Path uploadPath = Path.of(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        for(MultipartFile f : file){
            fileName = UUID.randomUUID().toString().replaceAll("-","") + "_" + f.getOriginalFilename();
            Path filePath = Path.of(UPLOAD_DIR, fileName);
            Files.copy(f.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            ret.add(fileName);
        }
        return ret;
    }

    public ResponseEntity<byte[]> downloadImage(String path) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get(UPLOAD_DIR + "/" + path));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(determineMediaType(path));
        headers.setContentLength(imageBytes.length);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    public MediaType determineMediaType(String imageName) {
        if (imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (imageName.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (imageName.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }


}
