package se.osbe.id;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SamordningsnummerBasicTest {

    private List<Personnummer> _samOKList;
    private List<Personnummer> _samNOKList;

    @Before
    public void setup() {
        _samOKList = Stream.of("121272-1219").map(Personnummer::parse).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());

        _samNOKList = Stream.of("121212-1212").map(Personnummer::parse).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }


    @Test
    public void testInvalidSamordningsnummerNorPersonnummerNOK() {
        Assert.assertFalse(Personnummer.parse("1122334566").isPresent());
    }

    @Test
    public void testSamordningsnummerToStringOK() {
        _samOKList.stream().forEach(s -> {
            Assert.assertTrue(Integer.parseInt(s.toString().substring(4, 5)) > 3);
            Assert.assertTrue(Integer.parseInt(s.toString10().substring(4, 5)) > 3);
            Assert.assertTrue(Integer.parseInt(s.toString11().substring(4, 5)) > 3);
            Assert.assertTrue(Integer.parseInt(s.toString12().substring(6, 7)) > 3);
            Assert.assertTrue(Integer.parseInt(s.toString13().substring(6, 7)) > 3);
        });
    }

    @Test
    public void testSamordningsnummerToStringNOK() {
        // Not samordningsnummer, still valid as Personnummer though!
        _samNOKList.stream().forEach(s -> {
            Assert.assertTrue(Integer.parseInt(s.toString().substring(4, 5)) <= 3);
            Assert.assertTrue(Integer.parseInt(s.toString10().substring(4, 5)) <= 3);
            Assert.assertTrue(Integer.parseInt(s.toString11().substring(4, 5)) <= 3);
            Assert.assertTrue(Integer.parseInt(s.toString12().substring(6, 7)) <= 3);
            Assert.assertTrue(Integer.parseInt(s.toString13().substring(6, 7)) <= 3);
        });
    }
}