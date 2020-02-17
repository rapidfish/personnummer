package se.osbe.id.sam;

import se.osbe.id.SweId;
import se.osbe.id.enums.IDType;

public class Samordningsnummer implements SweId {

    @Override
    public IDType getType() {
        return IDType.ORGANISATIONSNUMMER;
    }

}
