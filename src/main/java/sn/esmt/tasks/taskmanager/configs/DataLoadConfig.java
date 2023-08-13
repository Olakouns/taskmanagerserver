package sn.esmt.tasks.taskmanager.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import sn.esmt.tasks.taskmanager.entities.Privilege;
import sn.esmt.tasks.taskmanager.entities.Profile;
import sn.esmt.tasks.taskmanager.entities.Role;
import sn.esmt.tasks.taskmanager.entities.User;
import sn.esmt.tasks.taskmanager.entities.enums.StatusUser;
import sn.esmt.tasks.taskmanager.entities.enums.TypePrivilege;
import sn.esmt.tasks.taskmanager.entities.enums.TypeRole;
import sn.esmt.tasks.taskmanager.repositories.PrivilegeRepository;
import sn.esmt.tasks.taskmanager.repositories.ProfileRepository;
import sn.esmt.tasks.taskmanager.repositories.RoleRepository;
import sn.esmt.tasks.taskmanager.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class DataLoadConfig {
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public DataLoadConfig(RoleRepository roleRepository, PrivilegeRepository privilegeRepository, PasswordEncoder passwordEncoder,
                          UserRepository userRepository, ProfileRepository profileRepository) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @PostConstruct
    public void loadData() {
        loadRole();
    }

    public void loadRole() {
        if (roleRepository.existsByType(TypeRole.AUTHENTICATION))
            return;

        List<Privilege> privilegeAdmin = new ArrayList<>();
        for (int i = 1; i < TypePrivilege.values().length; i++) {
            String description = TypePrivilege.values()[i].toString().replace("_", " ");
            String category = description.split(" ")[description.split(" ").length - 1];
            privilegeAdmin.add(privilegeRepository.save(new Privilege(category, TypePrivilege.values()[i], description, TypeRole.AUTHENTICATION)));
        }


        Role roleAdmin = new Role("Super Admin", TypeRole.AUTHENTICATION);
        roleAdmin.setPrivileges(privilegeAdmin);
        roleAdmin = roleRepository.save(roleAdmin);

        User admin = new User();
        admin.setActive(true);
        admin.setEmail("lazare.kounasso@esmt.sn");
        admin.setName("Super Administrateur");
        admin.setUsername("lazare.kounasso@esmt.sn");
        admin.setStatus(StatusUser.ACTIVE);
        admin.setPassword(passwordEncoder.encode("mot2P@ss"));
        admin.setRoles(Collections.singletonList(roleAdmin));
        admin = userRepository.save(admin);

        Profile profile = new Profile();
        profile.setEmail("lazare.kounasso@esmt.sn");
        profile.setFirstName("Administrateur");
        profile.setLastName("Super");
        profile.setGender("M");
        profile.setPhone("+221785900131");
        profile.setUser(admin);
        profile.setUsername("lazare.kounasso@esmt.sn");
        profileRepository.save(profile);

    }

}
