package co.edu.iudigital.helpmeiud.services.iface;

import java.util.List;

import org.springframework.data.domain.Pageable;

import co.edu.iudigital.helpmeiud.exceptions.CaseNotFoundException;
import co.edu.iudigital.helpmeiud.exceptions.CrimeNotFoundException;
import co.edu.iudigital.helpmeiud.exceptions.UnauthorizedException;
import co.edu.iudigital.helpmeiud.exceptions.UserNotFoundException;
import co.edu.iudigital.helpmeiud.models.dto.request.CaseDtoRequest;
import co.edu.iudigital.helpmeiud.models.dto.response.CaseDtoResponse;

public interface ICaseService {
    
    //find all cases
    List<CaseDtoResponse> findAll(Pageable pageable);

    //create case
    CaseDtoResponse create(CaseDtoRequest caseDtoRequest) throws UserNotFoundException, CrimeNotFoundException;

    //update case
    CaseDtoResponse update(Long id, CaseDtoRequest caseDtoRequest) throws CaseNotFoundException, CrimeNotFoundException, UnauthorizedException;

    //delete case
    void delete(Long id) throws CaseNotFoundException;

    //find case by consumer
    List<CaseDtoResponse> findByConsumer() throws UserNotFoundException;
}
