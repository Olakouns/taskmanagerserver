package sn.esmt.tasks.taskmanager.entities.tksmanager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sn.esmt.tasks.taskmanager.entities.BaseEntity;
import sn.esmt.tasks.taskmanager.entities.Profile;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TaskComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "text")
    private String text;

    @ManyToOne
    @JsonIgnore
    private Tasks tasks;

    @ManyToOne
    private Profile profile;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "TaskComment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
