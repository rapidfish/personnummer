package se.osbe.id;

import se.osbe.id.enums.IDType;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Marker interface for Swedish Id number (Person-, Organisations- or Samordningsnummer).
 */
public interface Identifiable {

    default IDType getIDType() {
        return IDType.UNDEFINDED;
    };

    Integer getChecksum();

    default String getLastFour() { return EMPTY; };

    boolean isForgiving();

    default boolean isSamordningsnummer() {
        return false;
    };

    default boolean isPersonnummer() {
        return false;
    };

    default boolean isOrganisationsnummer() {
        return false;
    };
}
