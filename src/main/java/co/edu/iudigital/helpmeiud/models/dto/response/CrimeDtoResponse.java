package co.edu.iudigital.helpmeiud.models.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import lombok.Builder;

@Getter
@Setter
@Builder // builder pattern for object creation
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CrimeDtoResponse {

    Long id;

    String name;

    String description;
}
