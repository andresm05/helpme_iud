package co.edu.iudigital.helpmeiud.exceptions;

public class EntityNotFoundException extends RestException{
    
    public EntityNotFoundException(String entity, String message) {
        super(entity + ": "+message);
    }

    public EntityNotFoundException(ErrorMessage error) {
        super(error);
    }
}
