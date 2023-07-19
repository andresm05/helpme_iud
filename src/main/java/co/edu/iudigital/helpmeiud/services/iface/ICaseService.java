package co.edu.iudigital.helpmeiud.services.iface;

import java.util.List;

import org.springframework.data.domain.Pageable;

import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.models.dto.request.CaseDtoRequest;
import co.edu.iudigital.helpmeiud.models.dto.response.CaseDtoResponse;

public interface ICaseService {
    
    //find all cases
    List<CaseDtoResponse> findAll(Pageable pageable);

    //create case
    CaseDtoResponse create(CaseDtoRequest caseDtoRequest) throws RestException;

    //update case
    CaseDtoResponse update(Long id, CaseDtoRequest caseDtoRequest) throws  RestException;

    //delete case
    void delete(Long id) throws RestException;

    //find case by consumer
    List<CaseDtoResponse> findByConsumer() throws RestException;
}
