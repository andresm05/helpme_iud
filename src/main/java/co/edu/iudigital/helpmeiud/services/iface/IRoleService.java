package co.edu.iudigital.helpmeiud.services.iface;

import java.util.Optional;

import co.edu.iudigital.helpmeiud.models.entities.Role;

public interface IRoleService {
    
    //find role by name
    Optional<Role> findByName(String name);
}
