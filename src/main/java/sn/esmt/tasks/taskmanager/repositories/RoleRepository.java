package sn.esmt.tasks.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.esmt.tasks.taskmanager.entities.Role;
import sn.esmt.tasks.taskmanager.entities.enums.TypeRole;

import java.util.List;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String role);

    Role findFirstByType(TypeRole type);

    List<Role> findByType(TypeRole type);

    boolean existsByName(String role);

    boolean existsByType(TypeRole type);

    List<Role> findByNameNot(String role);

    Optional<Role> findByIdAndType(int roleId, TypeRole type);
}
