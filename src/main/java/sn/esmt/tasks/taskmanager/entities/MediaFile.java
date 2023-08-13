package sn.esmt.tasks.taskmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MediaFile extends BaseEntity {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	@JsonIgnore
	@NotBlank
	private String path;
	@JsonIgnore
	private String pathThumbnail;
	private String url;
	private String thumbnailUrl;
	@NotBlank
	private String originalName;
	@NotBlank
	/*
	 * images
	 * pdfs
	 * others
	 * */
	private String type;
	private String description;
	private long size;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	@CreatedBy
	@JsonIgnore
	private User postedBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPathThumbnail() {
		return pathThumbnail;
	}

	public void setPathThumbnail(String pathThumbnail) {
		this.pathThumbnail = pathThumbnail;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public User getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}

	@Override
	public String toString() {
		return "MediaFile{" +
				"id=" + id +
				", path='" + path + '\'' +
				", pathThumbnail='" + pathThumbnail + '\'' +
				", url='" + url + '\'' +
				", thumbnailUrl='" + thumbnailUrl + '\'' +
				", originalName='" + originalName + '\'' +
				", type='" + type + '\'' +
				", description='" + description + '\'' +
				", size=" + size +
				'}';
	}
}
