package se.osbe.id;

import se.osbe.id.enums.IDType;

/**
 * Marker interface for Swedish Id number (Person-, Organisations- or Samordningsnummer).
 */
public interface SweId {

    public IDType getType();

}
