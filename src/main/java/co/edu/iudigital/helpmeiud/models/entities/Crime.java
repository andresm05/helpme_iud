package co.edu.iudigital.helpmeiud.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "crimes")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE) // set all access level to private
public class Crime implements Serializable {

    public Crime() {
    }

    public Crime(String name, String description) {
        this.name = name;
        this.description = description;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    @NotBlank(message = "name is required")
    String name;

    String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Consumer consumer;

    // SQL SENTENCE FOR CREATE TABLE
    // CREATE TABLE crimes (
    // id BIGINT NOT NULL AUTO_INCREMENT,
    // name VARCHAR(255) NOT NULL,
    // desctiption VARCHAR(255) NOT NULL,
    // user_id BIGINT NOT NULL,
    // PRIMARY KEY (id),
    // UNIQUE (name),
    // FOREIGN KEY (user_id) REFERENCES users (id)
    // );

}
