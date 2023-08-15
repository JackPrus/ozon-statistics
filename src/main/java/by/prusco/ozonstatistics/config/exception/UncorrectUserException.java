package by.prusco.ozonstatistics.config.exception;

import java.io.Serial;

public class UncorrectUserException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 13487756471435607L;
    public UncorrectUserException(String message) { super(message); }
}
