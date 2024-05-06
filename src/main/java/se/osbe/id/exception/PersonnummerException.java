package se.osbe.id.exception;

public class PersonnummerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PersonnummerException() {
        super("Personnummer internal error");
    }

    public PersonnummerException(String description) {
        super(description);
    }
}
