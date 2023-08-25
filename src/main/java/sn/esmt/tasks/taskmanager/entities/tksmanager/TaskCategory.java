package sn.esmt.tasks.taskmanager.entities.tksmanager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.boot.context.properties.bind.DefaultValue;
import sn.esmt.tasks.taskmanager.entities.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TaskCategory extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;

    @NotNull
    private String name;
    private String indexColor;

    @ColumnDefault("false")
    @JsonIgnore
    private boolean defaultTaskCategory;

    @ManyToOne
    private Dashboard dashboard;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndexColor() {
        return indexColor;
    }

    public void setIndexColor(String indexColor) {
        this.indexColor = indexColor;
    }

    public boolean isDefaultTaskCategory() {
        return defaultTaskCategory;
    }

    public void setDefaultTaskCategory(boolean defaultTaskCategory) {
        this.defaultTaskCategory = defaultTaskCategory;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }
}
