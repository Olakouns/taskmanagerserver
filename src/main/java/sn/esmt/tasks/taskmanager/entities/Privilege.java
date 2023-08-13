package sn.esmt.tasks.taskmanager.entities;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import sn.esmt.tasks.taskmanager.entities.enums.TypePrivilege;
import sn.esmt.tasks.taskmanager.entities.enums.TypeRole;

import javax.persistence.*;

@Entity
@Table(name = "privileges")
@NoArgsConstructor
@SQLDelete(sql =
        "UPDATE privileges " +
                "SET deleted = true " +
                "WHERE id = ?")
@Where(clause = "deleted = false")
public class Privilege extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypePrivilege name;
    private String description;
    private String category;
    private TypeRole typeRole;

    public Privilege(TypePrivilege name) {
        super();
        this.name = name;
    }

    public Privilege(String category, TypePrivilege name, String description, TypeRole typeRole) {
        super();
        this.name = name;
        this.description = description;
        this.category = category;
        this.typeRole = typeRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypePrivilege getName() {
        return name;
    }

    public void setName(TypePrivilege name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public TypeRole getTypeRole() {
        return typeRole;
    }

    public void setTypeRole(TypeRole typeRole) {
        this.typeRole = typeRole;
    }
}

