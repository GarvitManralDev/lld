package exceptions;

// Invalid floor, no available elevator, or an out-of-service target
public class ElevatorException extends RuntimeException {
    public ElevatorException(String message) {
        super(message);
    }
}
