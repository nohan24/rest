package itu.services;

import org.hibernate.internal.util.MutableLong;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
}
