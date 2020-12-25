package com.scanword.backend.controller;

import com.scanword.backend.entity.Media;
import com.scanword.backend.entity.enums.ExtensionEnum;
import com.scanword.backend.service.MediaRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/media")
public class MediaController {
    private MediaRepositoryService mediaRepositoryService;


    public MediaController(MediaRepositoryService mediaRepositoryService) {
        this.mediaRepositoryService = mediaRepositoryService;
    }

    @GetMapping("/images")
    public List<Media> getImages() {
        return mediaRepositoryService.getAllImages();
    }

    @GetMapping("/sounds")
    public List<Media> getSounds() {
        return mediaRepositoryService.getAllSounds();
    }

    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImageAsByteArray(@RequestParam String path) throws IOException {
        InputStream in = getClass().getResourceAsStream("/images/" + path);
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value ="/sound")
    public void getSoundAsByteArray(
            @RequestParam String path,
            HttpServletResponse response) {
        try {
            InputStream is = getClass().getResourceAsStream("/sounds/" + path);
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            log.info("Error writing file to output stream. Filename was '{}'", path, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }

    }

    @PostMapping(value="/upload")
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestBody MultipartFile file){
        String extension = getExtension(name);
        if (!file.isEmpty() && isValidExtension(extension)) {
            try {
                String relativeWebPath = isSound(extension)
                        ? "src/main/resources/sounds"
                        : "src/main/resources/images";
                String absoluteFilePath = Paths.get(relativeWebPath).toAbsolutePath().toString();
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(absoluteFilePath,name + "-uploaded")));
                stream.write(bytes);
                stream.close();
                return "Вы удачно загрузили " + name + " в " + name + "-uploaded !";
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + name + ", потому что файл пустой или имеет некорректное расширение.";
        }
    }

    private static String getExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        return extension.toUpperCase();
    }

    private static boolean isValidExtension(String extension) {
        return isPic(extension.toUpperCase()) || isSound(extension.toUpperCase());
    }

    private static boolean isPic(String extension) throws IllegalArgumentException {
        try {
            ExtensionEnum.Pics.valueOf(extension);
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    private static boolean isSound(String extension) throws IllegalArgumentException {
        try {
            ExtensionEnum.Sound.valueOf(extension);
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
