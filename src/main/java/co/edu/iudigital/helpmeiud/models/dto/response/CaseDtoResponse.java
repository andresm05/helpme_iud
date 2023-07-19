package co.edu.iudigital.helpmeiud.models.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Getter
@Setter
@Builder // builder pattern for object creation
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CaseDtoResponse {

    Long id;

    LocalDateTime fullDate;

    Float latitude;

    Float longitude;

    Float altitude;

    String description;

    Boolean isViewed;

    String crime_name;

    String username;
}
