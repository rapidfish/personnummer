package se.osbe.id.org;

import se.osbe.id.SweId;
import se.osbe.id.enums.IDType;

public class Organisationsnummer implements SweId {

    @Override
    public IDType getType() {
        return IDType.ORGANISATIONSNUMMER;
    }

}
