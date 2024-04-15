package se.osbe.id.enums;

public enum IDType {
    PERSONNUMMER("Personnummer"),

    SAMORDNINGSNUMMER("Personnummer"),

    ORGANISATIONSNUMMER("Organisationsnummer")
    ;

    private final String _description;

    IDType(final String description) {
        _description = description;
    }

    public String getDescription() {
        return _description;
    }

}