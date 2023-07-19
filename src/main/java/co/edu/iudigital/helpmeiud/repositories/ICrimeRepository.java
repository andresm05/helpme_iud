package co.edu.iudigital.helpmeiud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.iudigital.helpmeiud.models.entities.Crime;


public interface ICrimeRepository extends JpaRepository<Crime, Long>{
    
    List<Crime> findByName(String name);
}
