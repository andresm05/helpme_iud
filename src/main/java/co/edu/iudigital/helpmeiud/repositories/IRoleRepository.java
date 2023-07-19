package co.edu.iudigital.helpmeiud.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.iudigital.helpmeiud.models.entities.Role;

public interface IRoleRepository extends JpaRepository<Role, Long>{

    Optional<Role> findByName(String name);
    
}
