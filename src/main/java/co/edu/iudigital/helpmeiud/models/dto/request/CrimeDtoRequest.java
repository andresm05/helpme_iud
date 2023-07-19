package co.edu.iudigital.helpmeiud.models.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CrimeDtoRequest {

    @Column(unique = true)
    @NotBlank(message = "Name is required")
    String name;

    String description;

    @JsonProperty("user_id")
    Long userId;
}
