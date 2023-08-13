package sn.esmt.tasks.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.esmt.tasks.taskmanager.entities.Privilege;
import sn.esmt.tasks.taskmanager.entities.enums.TypePrivilege;
import sn.esmt.tasks.taskmanager.entities.enums.TypeRole;

import java.util.List;


public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(TypePrivilege privilege);

    List<Privilege> findByNameNotIn(TypePrivilege[] typePrivileges);

    boolean existsByName(TypePrivilege privilege);

    List<Privilege> findByTypeRole(TypeRole typeRole);
}
