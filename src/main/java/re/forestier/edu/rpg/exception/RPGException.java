package re.forestier.edu.rpg.exception;

public class RPGException extends RuntimeException {
    public RPGException(String message) {
        super(message);
    }

    public RPGException(String message, Throwable cause) {
        super(message, cause);
    }
}
