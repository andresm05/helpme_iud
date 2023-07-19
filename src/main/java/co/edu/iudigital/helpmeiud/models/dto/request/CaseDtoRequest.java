package co.edu.iudigital.helpmeiud.models.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CaseDtoRequest {

    @JsonProperty("full_date")
    LocalDateTime fullDate;

    @NotNull(message = "latitude is required")
    Float latitude;

    @NotNull(message = "longitude is required")
    Float longitude;

    @NotNull(message = "altitude is required")
    Float altitude;

    String description;

    @JsonProperty("is_viewed")
    Boolean isViewed;

    @JsonProperty("url_map")
    String urlMap;

    @JsonProperty("rmi_url")
    String rmiUrl;

    @JsonProperty("crime_id")
    Long crimeId;
}
