package exceptions;

public class invalidMoveException extends RuntimeException{
    public invalidMoveException(String message){
        super(message);
    }
}
