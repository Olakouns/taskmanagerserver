package sn.esmt.tasks.taskmanager.storages;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(BufferedImage image, String imageName);
    void store(MultipartFile file, String imageName);
    void store(MultipartFile file, String type, String filename);
    void store(File file, String type, String filename);
    void createThumbnail(MultipartFile file, String filename, int width) throws IOException;

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    Path loadImage(String filename);
    Resource loadAsImageResource(String filename);
    void deleteImage(String filename);

    void deleteAll();
}
