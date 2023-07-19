package co.edu.iudigital.helpmeiud.services.iface;

import java.util.List;

import co.edu.iudigital.helpmeiud.models.dto.response.CrimeDtoResponse;
import co.edu.iudigital.helpmeiud.exceptions.RestException;
import co.edu.iudigital.helpmeiud.models.dto.request.CrimeDtoRequest;

public interface ICrimeService {

    // find all crimes
    List<CrimeDtoResponse> getAll();

    // find crime by id
    CrimeDtoResponse findById(Long id) throws RestException;

    // create crime
    CrimeDtoResponse create(CrimeDtoRequest crimeDtoRequest) throws RestException;

    // delete crime
    void delete(Long id) throws RestException;

    // update crime
    CrimeDtoResponse update(Long id, CrimeDtoRequest crimeDtoRequest) throws RestException;

}
