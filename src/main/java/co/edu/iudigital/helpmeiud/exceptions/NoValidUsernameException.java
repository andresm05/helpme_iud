package co.edu.iudigital.helpmeiud.exceptions;

public class NoValidUsernameException extends RestException {
    
    public NoValidUsernameException(String message) {
        super(message);
    }

    public NoValidUsernameException(ErrorMessage error) {
        super(error);
    }
}
