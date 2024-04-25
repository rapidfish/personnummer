package se.osbe.id.exception;

public class OrganisationsnummerException extends Exception {
    private static final long serialVersionUID = 1L;

    public OrganisationsnummerException() {
        super("Organisationsnummer internal error");
    }

    public OrganisationsnummerException(String description) {
        super(description);
    }
}
