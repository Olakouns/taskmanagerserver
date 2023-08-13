package sn.esmt.tasks.taskmanager.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.esmt.tasks.taskmanager.entities.User;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByEmailAndActive(String email, boolean active);

    Boolean existsByPhoneNumber(String phone);

    void deleteById(String idUser);

    Page<User> findByRoles_id(int idRole, Pageable page);

    Page<User> findByRoles_idIn(int[] idRole, Pageable page);

    Page<User> findByNameLike(String keyword1, Pageable page);

    Optional<User> findByPhoneNumber(String phone);
}
