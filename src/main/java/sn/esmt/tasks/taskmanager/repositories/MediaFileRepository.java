package sn.esmt.tasks.taskmanager.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.esmt.tasks.taskmanager.entities.MediaFile;

import java.util.Optional;

public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {

	Page<MediaFile> findByType(String type, Pageable of);

	Optional<MediaFile> findByPath(String string);
	Optional<MediaFile> findByPathThumbnail(String string);

}
