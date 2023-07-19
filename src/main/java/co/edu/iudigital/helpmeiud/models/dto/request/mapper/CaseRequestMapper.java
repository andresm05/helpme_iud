package co.edu.iudigital.helpmeiud.models.dto.request.mapper;

import co.edu.iudigital.helpmeiud.models.dto.request.CaseDtoRequest;
import co.edu.iudigital.helpmeiud.models.entities.Case;

public class CaseRequestMapper {

    private CaseDtoRequest c;

    private CaseRequestMapper() {
    }

    public static CaseRequestMapper builder() {
        return new CaseRequestMapper();
    }

    public CaseRequestMapper setCaseDtoRequest(CaseDtoRequest c) {
        this.c = c;
        return this;
    }

    public Case doToEntity() {
        return new Case(
                c.getFullDate(),
                c.getLatitude(),
                c.getLongitude(),
                c.getAltitude(),
                c.getDescription(),
                c.getIsViewed(),
                c.getUrlMap(),
                c.getRmiUrl());
    }

}
