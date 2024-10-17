package se.osbe.id;

import org.apache.commons.lang3.StringUtils;
import se.osbe.id.enums.IDType;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.regex.Pattern.compile;
import static org.apache.commons.lang3.StringUtils.*;
import static se.osbe.id.helper.ChecksumHelper.calculateChecksum;

public class Organisationsnummer implements Identifiable {
    /*
        Organisationsnummer

        Siffra 1 2 3 4 5 6 7 8 9 10
        Anger G 0 0 0 0 0-0 0 0 K

        G = Gruppnummer
        0 = Ordningsnummer
        K = Kontrollsiffra
     */

    // private static final String SAMORDNNR_PATTERN = "^(?<juridiskperson>(1[689]|20|99))?(?<gruppnummer>[25789])\\d(?<ordningsnummer3>[2-9])\\d{3}(?<sign>[-])?\\d{3}(?<checksum>\\d)$";
    // G - gruppnummer - Gruppnumret i kombination med den särskilda koden för juridisk form anger den nuvarande klassifi- ceringen för juridiska personer m.fl. som används för taxerings- och avgiftsändamål.
    // O - ordningsnummer - Den tredje siffran är alltid lägst en tvåa för att undvika förväxling med person- nummer.
    // För juridisk person: 16 + företagets tilldelade organisationsnummer (10 siffror)
    // sekelsiffror 18, 19 eller 20 + personnummer eller samordningsnummer (10 siffror).
    // Indikator för fysisk person (dvs personnummer) så är tredje siffran < 2 i samordningsnummer (i de 10 sista siffrorna).

    private static final String ORGANISATIONSNUMMER = "^(?<num1gruppnr>\\d)(?<num2>\\d)(?<num3>\\d)(?<num4to6>\\d{3})(?<sign>[-])?(?<num7to9>\\d{3})(?<num10checksum>\\d)$";
    private static final Pattern PRE_COMPILED_PATTERN = compile(ORGANISATIONSNUMMER);
    private static final String _DELIMITER = "-"; // the delimiter char used in the organisationsnummer
    private static final int _JURIDISKPERSON = 2; // the prefix for "juridisk person"

    // Organisationsnummer, trusted to always be correctly calculated, before being fed as argument to the private constructor!
    private final String _organisationsnummer; // the organisationsnummer given as input
    private final boolean _isForgiving; // if true, a calculated checksum is used and substituted, regardless of the actual checksum digit in the organisationsnummer given as input
    private final boolean _isJuridiskPerson; // gets automatically calculated from the _organisationsnummer in the private constructor

    private Organisationsnummer() {
        // private protected constructor
        this("", false);
    }

    private Organisationsnummer(String orgNr, boolean isForgiving) {
        // private protected constructor
        _organisationsnummer = orgNr;
        _isJuridiskPerson = parseInt(orgNr.substring(2, 3)) >= _JURIDISKPERSON; // (< 2 is a physical person, thus it gets treated exactly like a basic Personnummer would)
        _isForgiving = isForgiving;
    }

    public static Optional<Organisationsnummer> parse(String orgCandidate) {
        return Organisationsnummer.parse(orgCandidate, false);
    }

    public static Optional<Organisationsnummer> parse(String orgCandidate, boolean isForgiving) {
        if (orgCandidate == null || orgCandidate.trim().length() < 9) {
            return empty();
        }
        Matcher matcher = PRE_COMPILED_PATTERN.matcher(orgCandidate.trim());
        if (!matcher.find()) {
            return empty();
        }
        String num1 = matcher.group("num1gruppnr");
        String num2 = matcher.group("num2");
        String num3 = matcher.group("num3"); // Om < 2, så är det en fysisk person. Om > 1, så är det en juridisk person.
        String num4to6 = matcher.group("num4to6");
        String num7to9 = matcher.group("num7to9");
        String num10checksum = matcher.group("num10checksum");
        String orgNr = join(asList(num1, num2, num3, num4to6, num7to9, num10checksum).toArray());
        String calculatedChecksum = calculateChecksum(orgNr);
        boolean isChecksumOk = isForgiving || num10checksum.equals(calculatedChecksum);
        return isChecksumOk
                ? of(new Organisationsnummer(join(asList(num1, num2, num3, num4to6, _DELIMITER, num7to9, calculatedChecksum).toArray()), isForgiving))
                : empty();
    }

    public boolean isForgiving() {
        return _isForgiving;
    }

    @Override
    public boolean isOrganisationsnummer() {
        return isNotEmpty(_organisationsnummer);
    }

    public boolean isJuridiskPerson() {
        return _isJuridiskPerson;
    }

    public Integer getChecksum() {
        return isNotEmpty(_organisationsnummer) ? parseInt(_organisationsnummer.substring(_organisationsnummer.length() - 1)) : null;
    }

    @Override
    public String getLastFour() {
        final int LAST4_INDEX = 6;
        return isNotEmpty(_organisationsnummer) ? toString10().substring(LAST4_INDEX) : EMPTY;
    }

    @Override
    public IDType getIDType() {
        return IDType.ORGANISATIONSNUMMER;
    }

    @Override
    public String toString() {
        return _organisationsnummer;
    }

    public String toString10() {
        return toString().replaceAll(_DELIMITER, EMPTY);
    }

    public String toString11() {
        return _organisationsnummer;
    }

    public String toString12() {
        return "16" + _organisationsnummer.replaceAll(_DELIMITER, EMPTY); // TODO: 16 is the prefix for juridisk person?
    }

    public String toString13() {
        return "16" + _organisationsnummer; // TODO: 16 is the prefix for juridisk person?
    }

    public Optional<Personnummer> toPersonnummer() {
        return _isJuridiskPerson == false ? Personnummer.parse(_organisationsnummer) : Optional.empty();
    }
}
