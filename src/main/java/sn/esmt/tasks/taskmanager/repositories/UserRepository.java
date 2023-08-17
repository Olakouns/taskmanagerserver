package sn.esmt.tasks.taskmanager.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.esmt.tasks.taskmanager.entities.User;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailOrUsername(String email, String username);

    Boolean existsByEmail(String email);

    Boolean existsByEmailAndActive(String email, boolean active);

    Boolean existsByPhoneNumber(String phone);
}
