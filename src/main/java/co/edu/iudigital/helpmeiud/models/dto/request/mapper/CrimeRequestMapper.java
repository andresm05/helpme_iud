package co.edu.iudigital.helpmeiud.models.dto.request.mapper;

import co.edu.iudigital.helpmeiud.models.dto.request.CrimeDtoRequest;
import co.edu.iudigital.helpmeiud.models.entities.Crime;

public class CrimeRequestMapper {

    private CrimeDtoRequest crimeDtoRequest;

    private CrimeRequestMapper() {
    }

    public static CrimeRequestMapper builder() {
        return new CrimeRequestMapper();
    }

    public CrimeRequestMapper setCrimeDtoRequest(CrimeDtoRequest crimeDtoRequest) {
        this.crimeDtoRequest = crimeDtoRequest;
        return this;
    }

    public Crime dtoToEntity() {

        // Consumer userFound = consumer
        // .orElseThrow(() -> new RuntimeException("User not found"));

        return new Crime(
                crimeDtoRequest.getName(),
                crimeDtoRequest.getDescription());
    }
}
