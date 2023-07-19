package co.edu.iudigital.helpmeiud.exceptions;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ErrorMessage implements Serializable{
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
