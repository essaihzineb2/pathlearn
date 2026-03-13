package org.example.projet.repository.auth;

import org.example.projet.model.auth.ERole;
import org.example.projet.model.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
