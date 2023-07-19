package co.edu.iudigital.helpmeiud.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestException extends Exception {
    
    private ErrorMessage error;

    public RestException(ErrorMessage error) {
        super(error.getMessage());
        this.error = error;
    }
    
    public RestException(String message) {
        super(message);
    }

}
