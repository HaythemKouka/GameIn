package GameIn.exceptions.customExceptions;

public class DuplicateUserEmailException extends RuntimeException{
    public DuplicateUserEmailException() {
        super("Duplicated Email");
    }

    public DuplicateUserEmailException(String message) {
        super(message);
    }
}
