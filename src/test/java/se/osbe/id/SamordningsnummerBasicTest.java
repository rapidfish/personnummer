package se.osbe.id;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.util.stream.Stream.of;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SamordningsnummerBasicTest {

    private List<Personnummer> _samOKList;
    private List<Personnummer> _samNOKList;

    @Before
    public void setup() {
        _samOKList = of("121272-1219").map(Personnummer::parse).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        _samNOKList = of("121212-1212").map(Personnummer::parse).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

    @Test
    public void testInvalidSamordningsnummerNorPersonnummerNOK() {
        assertFalse(Personnummer.parse("1122334566").isPresent());
    }

    @Test
    public void testSamordningsnummerToStringOK() {
        _samOKList.stream().forEach(s -> {
            assertTrue(parseInt(s.toString().substring(4, 5)) > 3);
            assertTrue(parseInt(s.toString10().substring(4, 5)) > 3);
            assertTrue(parseInt(s.toString11().substring(4, 5)) > 3);
            assertTrue(parseInt(s.toString12().substring(6, 7)) > 3);
            assertTrue(parseInt(s.toString13().substring(6, 7)) > 3);
        });
    }

    @Test
    public void testSamordningsnummerToStringNOK() {
        // Not samordningsnummer, still valid as Personnummer though!
        _samNOKList.stream().forEach(s -> {
            assertTrue(parseInt(s.toString().substring(4, 5)) <= 3);
            assertTrue(parseInt(s.toString10().substring(4, 5)) <= 3);
            assertTrue(parseInt(s.toString11().substring(4, 5)) <= 3);
            assertTrue(parseInt(s.toString12().substring(6, 7)) <= 3);
            assertTrue(parseInt(s.toString13().substring(6, 7)) <= 3);
        });
    }
}