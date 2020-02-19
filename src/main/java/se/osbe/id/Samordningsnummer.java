package se.osbe.id;

import se.osbe.id.Id;
import se.osbe.id.enums.IDType;

public class Samordningsnummer implements Id {

    @Override
    public IDType getType() {
        return IDType.ORGANISATIONSNUMMER;
    }

}
