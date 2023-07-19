package co.edu.iudigital.helpmeiud.services.iface;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import co.edu.iudigital.helpmeiud.exceptions.NoValidUsernameException;
import co.edu.iudigital.helpmeiud.exceptions.NotEnabledUserException;
import co.edu.iudigital.helpmeiud.exceptions.UserNotFoundException;
import co.edu.iudigital.helpmeiud.models.dto.request.ConsumerDtoRequest;
import co.edu.iudigital.helpmeiud.models.dto.response.ConsumerDtoResponse;

public interface IConsumerService {

    //find consumer by email
    ConsumerDtoResponse findByUsername(String username);

    //find consumer by id
    Optional<ConsumerDtoResponse> findById(Long id) throws UserNotFoundException;

    //find all consumers
    List<ConsumerDtoResponse> findAll(Pageable pagin);

    //create consumer
    ConsumerDtoResponse create(ConsumerDtoRequest consumerDtoRequest) throws NoValidUsernameException;

    //update consumer
    ConsumerDtoResponse update(Long id, ConsumerDtoRequest consumerDtoRequest) throws UserNotFoundException;

    //delete consumer
    void delete(Long id) throws UserNotFoundException, NotEnabledUserException;
    
    //
}
