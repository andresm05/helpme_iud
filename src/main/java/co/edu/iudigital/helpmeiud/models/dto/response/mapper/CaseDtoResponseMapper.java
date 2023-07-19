package co.edu.iudigital.helpmeiud.models.dto.response.mapper;

import co.edu.iudigital.helpmeiud.models.dto.response.CaseDtoResponse;
import co.edu.iudigital.helpmeiud.models.entities.Case;

public class CaseDtoResponseMapper {
    
    private Case c;

    private CaseDtoResponseMapper() {
    }

    public static CaseDtoResponseMapper builder() {
        return new CaseDtoResponseMapper();
    }

    public CaseDtoResponseMapper setCase(Case c) {
        this.c = c;
        return this;
    }

    public CaseDtoResponse build() {
        return CaseDtoResponse.builder()
                .id(this.c.getId())
                .fullDate(this.c.getFullDate())
                .latitude(this.c.getLatitude())
                .longitude(this.c.getLongitude())
                .altitude(this.c.getAltitude())
                .description(this.c.getDescription())
                .isViewed(this.c.getIsViewed())
                .crime_name(this.c.getCrime().getName())
                .username(this.c.getConsumer().getUsername())
                .build();
    }

}
