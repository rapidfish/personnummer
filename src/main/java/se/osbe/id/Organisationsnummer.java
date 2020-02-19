package se.osbe.id;

import se.osbe.id.enums.IDType;

public class Organisationsnummer implements Id {

    @Override
    public IDType getType() {
        return IDType.ORGANISATIONSNUMMER;
    }

}
