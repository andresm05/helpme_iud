package co.edu.iudigital.helpmeiud.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.UniqueConstraint;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Consumer implements Serializable {

    public Consumer() {
    }

    public Consumer (String username, String name, String password, LocalDate bornDate, String socialMedia,
            Boolean enabled, String image, List<Role> roles) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.bornDate = bornDate;
        this.socialMedia = socialMedia;
        this.enabled = enabled;
        this.image = image;
        this.roles = roles;
    }

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

    @Column(name = "born_date")
    LocalDate bornDate;

    @Column(name = "social_media")
    String socialMedia;

    @Column
    Boolean enabled;

    @Column
    String image;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", 
    joinColumns = @JoinColumn(name = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "role_id"), 
    uniqueConstraints = {
            @UniqueConstraint(columnNames = { "user_id", "role_id" }) })
    List<Role> roles;

    //set enabled true by default
    @PrePersist
    public void prePersist() {
        this.enabled = true;
    }
}
