package sn.esmt.tasks.taskmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import sn.esmt.tasks.taskmanager.entities.enums.TypeRole;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;


@Entity
@Table(name = "roles")
@SQLDelete(sql =
        "UPDATE roles " +
                "SET deleted = true " +
                "WHERE id = ?")
@Where(clause = "deleted = false")
public class Role extends BaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = 5611760913680161176L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String name;
    private String description;
    private TypeRole type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    @JsonIgnore
    private Collection<Privilege> privileges;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private User createdBy;

    public Role() {
        super();
        // TODO Auto-generated constructor stub
    }

//	public Role(String name) {
//		super();
//		this.name = name;
//	}

    public Role(String name, TypeRole type) {
        super();
        this.name = name;
        this.type = type;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the privileges
     */
    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param privileges the privileges to set
     */
    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the createdBy
     */
    public User getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public TypeRole getType() {
        return type;
    }

    public void setType(TypeRole type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }
}
