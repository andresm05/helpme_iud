package co.edu.iudigital.helpmeiud.exceptions;

public  class NotEnabledUserException extends RestException {
    
    public NotEnabledUserException(String message) {
        super(message);
    }

    public NotEnabledUserException(ErrorMessage error) {
        super(error);
    }
}
