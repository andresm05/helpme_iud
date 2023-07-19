package co.edu.iudigital.helpmeiud.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.iudigital.helpmeiud.models.entities.Consumer;

public interface IConsumerRepository extends JpaRepository<Consumer, Long>{

    Consumer findByUsername(String username);

    Page<Consumer> findByEnabled(Boolean enabled, Pageable pageable);

    Page<Consumer> findAll(Pageable pageable);
    
}
