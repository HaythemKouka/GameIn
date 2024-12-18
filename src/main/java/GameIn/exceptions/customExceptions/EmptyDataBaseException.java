package GameIn.exceptions.customExceptions;

import GameIn.exceptions.MessageException;

public class EmptyDataBaseException extends RuntimeException{
    public EmptyDataBaseException() {
        super(MessageException.EMPTY_DATABASE);
    }

    public EmptyDataBaseException(String message) {
        super(message);
    }
}
