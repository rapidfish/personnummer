package se.osbe.id.exception;

public class IdentifiableException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IdentifiableException() {
        super("Identifiable internal error");
    }

    public IdentifiableException(String description) {
        super(description);
    }
}
