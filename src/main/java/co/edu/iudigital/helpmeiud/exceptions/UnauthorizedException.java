package co.edu.iudigital.helpmeiud.exceptions;

public class UnauthorizedException extends RestException {
    
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(ErrorMessage error) {
        super(error);
    }
}
