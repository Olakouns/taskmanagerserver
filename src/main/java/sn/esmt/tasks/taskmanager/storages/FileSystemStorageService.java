package sn.esmt.tasks.taskmanager.storages;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import sn.esmt.tasks.taskmanager.exceptions.StorageFileNotFoundException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final Path imageLocation;
    private String[] subPath = {"images","pdfs", "profiles", "profiles/thumbnails", "others", "sheets", "images/thumbnails"};

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.imageLocation = Paths.get(properties.getLocation()+ File.separator+"images");
        for(int i=0; i<subPath.length; i++) {
            Path fileLocation = Paths.get(properties.getLocation()+ File.separator+subPath[i]);
            if (!Files.exists(fileLocation))
                try {
                    Files.createDirectory(fileLocation);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }

    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new sn.esmt.tasks.taskmanager.exceptions.StorageFileNotFoundException.StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    } 

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
            Files.createDirectory(imageLocation);
        } catch (IOException e) {
            throw new sn.esmt.tasks.taskmanager.exceptions.StorageFileNotFoundException.StorageException("Could not initialize storage", e);
        }
    }

	@Override
	public void store(BufferedImage image, String imageName) {
		// TODO Auto-generated method stub
		//Writing the image to a file
        try {
			ImageIO.write(image, "JPEG", new File(imageLocation.toFile().getAbsolutePath()+File.separator+imageName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    @Override
    public void createThumbnail(MultipartFile file, String filename, int width) throws IOException {
        BufferedImage thumbImg = null;
        BufferedImage img = ImageIO.read(file.getInputStream());
        thumbImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, width, Scalr.OP_ANTIALIAS);
        Path path = imageLocation.resolve("thumbnails").resolve(filename);
        ImageIO.write(thumbImg, file.getContentType().split("/")[1] , path.toFile());
    }

	@Override
	public Path loadImage(String filename) {
		// TODO Auto-generated method stub
		return imageLocation.resolve(filename);
	}

	@Override
	public Resource loadAsImageResource(String filename) {
		// TODO Auto-generated method stub
		try {
            Path file = loadImage(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
	}

	@Override
	public void store(MultipartFile file, String imageName) {
		// TODO Auto-generated method stub
		try {
            if (file.isEmpty()) {
                throw new sn.esmt.tasks.taskmanager.exceptions.StorageFileNotFoundException.StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Path path = imageLocation.resolve(imageName);
            if (Files.exists(path)) {
            	Files.delete(path);
            }
            
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new sn.esmt.tasks.taskmanager.exceptions.StorageFileNotFoundException.StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
	}

    @Override
    public void store(MultipartFile file, String type, String filename) {
        // TODO Auto-generated method stub
        try {
            if (file.isEmpty()) {
                throw new sn.esmt.tasks.taskmanager.exceptions.StorageFileNotFoundException.StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Path path = rootLocation.resolve(type).resolve(filename);
            if (Files.exists(path)) {
                Files.delete(path);
            }

            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new sn.esmt.tasks.taskmanager.exceptions.StorageFileNotFoundException.StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    public void store(File file, String type, String filename){
        // TODO Auto-generated method stub
        try {
            if (!file.exists()) {
                throw new sn.esmt.tasks.taskmanager.exceptions.StorageFileNotFoundException.StorageException("Failed to store empty file " + file.getName());
            }
            Path path = rootLocation.resolve(type).resolve(filename);
            if (Files.exists(path)) {
                Files.delete(path);
            }

            FileInputStream inputStream = new FileInputStream(file);
            Files.write(path, inputStream.readAllBytes());

        } catch (IOException e) {
            throw new sn.esmt.tasks.taskmanager.exceptions.StorageFileNotFoundException.StorageException("Failed to store file " + file.getName(), e);
        }
    }

	@Override
	public void deleteImage(String filename) {
		// TODO Auto-generated method stub
		FileSystemUtils.deleteRecursively(imageLocation.resolve(filename).toFile());
	}
}
