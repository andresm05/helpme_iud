package co.edu.iudigital.helpmeiud.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "cases")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Case implements Serializable {

    public Case() {

    }

    public Case(LocalDateTime fullDate,
            Float latitude, Float longitude, Float altitude,
            String description, Boolean isViewed, String urlMap,
            String rmiUrl) {
        this.fullDate = fullDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.description = description;
        this.isViewed = isViewed;
        this.urlMap = urlMap;
        this.rmiUrl = rmiUrl;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "full_date")
    LocalDateTime fullDate;

    @NotNull(message = "latitude is required")
    Float latitude;

    @NotNull(message = "longitude is required")
    Float longitude;

    @NotNull(message = "altitude is required")
    Float altitude;

    String description;

    @Column(name = "is_viewed")
    Boolean isViewed;

    @Column(name = "url_map")
    String urlMap;

    @Column(name = "rmi_url")
    String rmiUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Consumer consumer;

    @ManyToOne
    @JoinColumn(name = "crime_id")
    Crime crime;

    // generate date by default
    @PrePersist
    public void prePersist() {
        if (fullDate == null) {

            fullDate = LocalDateTime.now();
        }
        if (isViewed == null) {
            isViewed = true;

        }

    }

}
