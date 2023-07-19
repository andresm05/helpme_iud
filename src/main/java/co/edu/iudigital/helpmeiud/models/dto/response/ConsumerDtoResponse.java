package co.edu.iudigital.helpmeiud.models.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;

@Getter
@Setter
@Builder // builder pattern for object creation
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ConsumerDtoResponse {
    
    Long id;

    String username;

    String name;

    String image;

    LocalDate bornDate;
}
