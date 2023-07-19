package co.edu.iudigital.helpmeiud.models.dto.response.mapper;

import co.edu.iudigital.helpmeiud.models.dto.response.CrimeDtoResponse;
import co.edu.iudigital.helpmeiud.models.entities.Crime;

public class CrimeDtoResponseMapper {

    private Crime crime;

    private CrimeDtoResponseMapper() {
    }

    public static CrimeDtoResponseMapper builder() {
        return new CrimeDtoResponseMapper();
    }

    public CrimeDtoResponseMapper setCrime(Crime crime) {
        this.crime = crime;
        return this;
    }

    public CrimeDtoResponse build() {
        return CrimeDtoResponse.builder()
                .id(this.crime.getId())
                .name(this.crime.getName())
                .description(this.crime.getDescription())
                .build();
    }
}
