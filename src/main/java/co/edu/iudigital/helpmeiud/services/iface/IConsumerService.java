package co.edu.iudigital.helpmeiud.services.iface;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.models.dto.request.ConsumerDtoRequest;
import co.edu.iudigital.helpmeiud.models.dto.response.ConsumerDtoResponse;
import jakarta.mail.MessagingException;

public interface IConsumerService {

    //find consumer by email
    ConsumerDtoResponse findByUsername(String username);

    //find consumer by id
    Optional<ConsumerDtoResponse> findById(Long id) throws RestException;

    //find all consumers
    List<ConsumerDtoResponse> findAll(Pageable pagin);

    //create consumer
    ConsumerDtoResponse create(ConsumerDtoRequest consumerDtoRequest) throws RestException, MessagingException;

    //update consumer
    ConsumerDtoResponse update(Long id, ConsumerDtoRequest consumerDtoRequest) throws RestException;

    //delete consumer
    void delete(Long id) throws RestException;

    Map<String, Object> renewToken(String token) throws RestException;
    
    //get username info
    ConsumerDtoResponse getConsumerInfo(String token) throws RestException;
}
