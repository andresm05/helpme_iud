package co.edu.iudigital.helpmeiud.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.iudigital.helpmeiud.models.entities.Case;
import co.edu.iudigital.helpmeiud.models.entities.Consumer;

import java.util.List;


public interface ICaseRepository extends JpaRepository<Case, Long>{
    
    List<Case> findByConsumer(Consumer consumer);

    Page<Case> findAll(Pageable pageable);
}
