package se.osbe.id;

import org.junit.Before;
import org.junit.Test;
import se.osbe.id.enums.IDType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.util.stream.Stream.of;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SamordningsnummerBasicTest {

    private static final int SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE = 60;
    private List<Personnummer> _samOKList;
    private List<Personnummer> _samNOKList;

    public SamordningsnummerBasicTest() {
        _samOKList = of(
                "121272-1219",
                "121262-1211"
        ).map(Personnummer::parse).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        _samNOKList = of("121212-1212").map(Personnummer::parse).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

    @Test
    public void testInvalidSamordningsnummerNorPersonnummerNOK() {
        assertFalse(Personnummer.parse("1122334566").isPresent());
    }

    @Test
    public void testSamordningsnummerToStringOK() {

        _samOKList.forEach(s -> {
            assertTrue(parseInt(s.toString().substring(4, 6)) >= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
            assertTrue(parseInt(s.toString10().substring(4, 6)) >= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
            assertTrue(parseInt(s.toString11().substring(4, 6)) >= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
            assertTrue(parseInt(s.toString12().substring(6, 8)) >= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
            assertTrue(parseInt(s.toString13().substring(6, 8)) >= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
        });
    }

    @Test
    public void testSamordningsnummerToStringNOK() {
        // Not samordningsnummer, still valid as Personnummer though!
        _samNOKList.stream().forEach(s -> {
            assertTrue(parseInt(s.toString().substring(4, 6)) <= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
            assertTrue(parseInt(s.toString10().substring(4, 6)) <= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
            assertTrue(parseInt(s.toString11().substring(4, 6)) <= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
            assertTrue(parseInt(s.toString12().substring(6, 8)) <= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
            assertTrue(parseInt(s.toString13().substring(6, 8)) <= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
        });
    }

    @Test
    public void testIsSamordningsnummerOK() {
        _samOKList.forEach(s -> {
            assertTrue(s.isSamordningsnummer());
            assertTrue(s.getIDType() == IDType.SAMORDNINGSNUMMER);
        });
    }


    @Test
    public void testIsSamordningsnummerNOK() {
        _samNOKList.forEach(s -> {
            assertFalse(s.isSamordningsnummer());
            assertFalse(s.getIDType() == IDType.SAMORDNINGSNUMMER);
        });
    }
}