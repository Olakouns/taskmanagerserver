package sn.esmt.tasks.taskmanager.controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.esmt.tasks.taskmanager.entities.MediaFile;
import sn.esmt.tasks.taskmanager.repositories.MediaFileRepository;
import sn.esmt.tasks.taskmanager.repositories.UserRepository;
import sn.esmt.tasks.taskmanager.security.CurrentUser;
import sn.esmt.tasks.taskmanager.security.UserPrincipal;
import sn.esmt.tasks.taskmanager.storages.StorageService;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;


@RestController
@RequestMapping("/api/medias")
public class MediaController {

    private final StorageService storageService;
    private final MediaFileRepository mediaFileRepository;
    private final UserRepository userRepository;


    public MediaController(StorageService storageService, MediaFileRepository mediaFileRepository, UserRepository userRepository) {
        this.storageService = storageService;
        this.mediaFileRepository = mediaFileRepository;
        this.userRepository = userRepository;
    }

    @PostMapping()
    public ResponseEntity<?> saveMedia(@RequestParam(value = "file", required = true) MultipartFile file,
                                       @RequestParam(value = "type", required = true) String type, @RequestParam("description") String description,
                                       @CurrentUser UserPrincipal principal) {
        MediaFile mediaFile = saveMediaFile(file, type, description, principal);

        return new ResponseEntity<Object>(mediaFile, HttpStatus.OK);
    }

    @GetMapping()
    public Page<MediaFile> getMediaFiles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size,
                                         @RequestParam(defaultValue = "") String type) {
        if (type.trim().isEmpty()) {
            return mediaFileRepository.findAll(PageRequest.of(page, size));
        } else {
            return mediaFileRepository.findByType(type, PageRequest.of(page, size));
        }
    }

    @DeleteMapping("{id}")
    public boolean deleteMedia(@PathVariable(required = true) long id) {
        return true;
    }

    private MediaFile saveMediaFile(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "type", required = true) String type, @RequestParam("description") String description, @CurrentUser UserPrincipal principal) {
        String filename = UUID.randomUUID() +
                Objects.requireNonNull(file.getOriginalFilename()).toLowerCase()
                        .substring(file.getOriginalFilename().toLowerCase().lastIndexOf("."));
        storageService.store(file, type, filename);
        MediaFile mediaFile = new MediaFile();
        mediaFile.setOriginalName(file.getOriginalFilename());
        mediaFile.setType(type);
        mediaFile.setDescription(description);
        mediaFile.setPath(type + "/" + filename);
        mediaFile.setUrl("medias/" + type + "/" + filename);
        mediaFile.setSize(file.getSize());
        if (type.equals("images") || type.equals("profiles")) {
            try {
                storageService.createThumbnail(file, filename, 500);
                mediaFile.setPathThumbnail("images/thumbnails/" + filename);
                mediaFile.setThumbnailUrl("medias/images/thumbnails/" + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mediaFile.setPostedBy(userRepository.getReferenceById(principal.getId()));
        return mediaFileRepository.save(mediaFile);
    }

}
