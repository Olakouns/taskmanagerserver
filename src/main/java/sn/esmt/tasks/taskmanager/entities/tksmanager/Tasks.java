package sn.esmt.tasks.taskmanager.entities.tksmanager;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import sn.esmt.tasks.taskmanager.entities.BaseEntity;
import sn.esmt.tasks.taskmanager.entities.MediaFile;
import sn.esmt.tasks.taskmanager.entities.Profile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Tasks extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;

    @NotNull
    private String title;

    private String description;

    @ManyToOne
    private MediaFile imageDescription;

    @ElementCollection
    private List<String> tags;

    @ElementCollection
    private List<String> badgeColor;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date deadline;

    @ManyToOne
    private TaskCategory taskCategory;

    @ManyToOne
    @JsonIgnore
    private Dashboard dashboard;


    @OneToMany
    private List<Profile> profiles;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<MediaFile> mediaFiles;


    @OneToMany(cascade = CascadeType.REMOVE)
    private List<TaskComment> taskComments;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MediaFile getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(MediaFile imageDescription) {
        this.imageDescription = imageDescription;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getBadgeColor() {
        return badgeColor;
    }

    public void setBadgeColor(List<String> badgeColor) {
        this.badgeColor = badgeColor;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public TaskCategory getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(TaskCategory taskCategory) {
        this.taskCategory = taskCategory;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public List<MediaFile> getMediaFiles() {
        return mediaFiles;
    }

    public void setMediaFiles(List<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TaskComment> getTaskComments() {
        return taskComments;
    }

    public void setTaskComments(List<TaskComment> taskComments) {
        this.taskComments = taskComments;
    }
}
