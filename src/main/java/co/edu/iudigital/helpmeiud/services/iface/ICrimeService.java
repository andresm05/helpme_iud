package co.edu.iudigital.helpmeiud.services.iface;

import java.util.List;

import co.edu.iudigital.helpmeiud.models.dto.response.CrimeDtoResponse;
import co.edu.iudigital.helpmeiud.exceptions.CrimeNotFoundException;
import co.edu.iudigital.helpmeiud.exceptions.NoValidUsernameException;
import co.edu.iudigital.helpmeiud.models.dto.request.CrimeDtoRequest;

public interface ICrimeService {

    // find all crimes
    List<CrimeDtoResponse> getAll();

    // find crime by id
    CrimeDtoResponse findById(Long id) throws CrimeNotFoundException;

    // create crime
    CrimeDtoResponse create(CrimeDtoRequest crimeDtoRequest) throws NoValidUsernameException;

    // delete crime
    void delete(Long id) throws CrimeNotFoundException;

    // update crime
    CrimeDtoResponse update(Long id, CrimeDtoRequest crimeDtoRequest) throws CrimeNotFoundException, NoValidUsernameException;

}
