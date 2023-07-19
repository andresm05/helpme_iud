package co.edu.iudigital.helpmeiud.models.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import co.edu.iudigital.helpmeiud.models.entities.Role;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsumerDtoRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "username is required")
    @Column(unique = true)
    @Email(message = "username must be a valid email")
    @Size(min = 3, max = 100, message = "username must be between 3 and 50 characters")
    String username;

    @NotBlank(message = "name is required")
    @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters")
    String name;

    String password;

    @JsonProperty("born_date")
    LocalDate bornDate;

    @JsonProperty("social_media")
    String socialMedia;

    Boolean enabled;

    String image;

    List<Role> roles;
}
