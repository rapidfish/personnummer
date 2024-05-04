package se.osbe.id;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.osbe.id.enums.IDType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PersonnummerSamordningsnummerTest {

    private static final int SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE = 60;
    private List<String> _samordningsnummerOKList;
    private List<Personnummer> _samordningsnummerObjList;

    @Before
    public void personnummerAdvancedTestSetup() {
        _samordningsnummerOKList = Arrays.asList(
                "121272-1219",
                "121262-1211",
                "19201231-1211"
        );
        _samordningsnummerObjList = _samordningsnummerOKList.stream()
                .map(Personnummer::parse)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Test
    public void testSamordningsnummerToStringOK() {
        _samordningsnummerObjList.forEach(s -> {
            assertTrue(parseInt(s.toString().substring(4, 6)) >= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
            assertTrue(parseInt(s.toString10().substring(4, 6)) >= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
            assertTrue(parseInt(s.toString11().substring(4, 6)) >= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
            assertTrue(parseInt(s.toString12().substring(6, 8)) >= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
            assertTrue(parseInt(s.toString13().substring(6, 8)) >= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
        });
    }

    @Test
    public void testIsSamordningsnummerOK() {
        _samordningsnummerObjList.forEach(s -> {
            assertFalse(s.isPersonnummer());
            assertTrue(s.isSamordningsnummer());
            assertTrue(s.getIDType() == IDType.SAMORDNINGSNUMMER);
            assertFalse(s.getIDType() == IDType.PERSONNUMMER);
            assertFalse(s.getIDType() == IDType.ORGANISATIONSNUMMER);
            assertFalse(s.getIDType() == IDType.UNDEFINDED);
        });
    }

    @Test
    public void testParsePersonnummerOK() {
        Assert.assertTrue(Personnummer.parse("1212611212").isPresent());
        Assert.assertTrue(Personnummer.parse("121261-1212").isPresent());
        Assert.assertTrue(Personnummer.parse("191212611212").isPresent());
        Assert.assertTrue(Personnummer.parse("19121261-1212").isPresent());
    }

    @Test
    public void testParsePersonnummerUsingForgivingFlagOK() {
        final String SAM1 = "1212611211";
        Assert.assertFalse(Personnummer.parse(SAM1).isPresent());
        Personnummer sam1 = Personnummer.parse(SAM1, true).get();
        Assert.assertTrue(Personnummer.parse(SAM1, true).isPresent());
        Assert.assertFalse(sam1.isPersonnummer());
        Assert.assertTrue(sam1.isSamordningsnummer());
        Assert.assertEquals(Integer.valueOf(2), sam1.getChecksum());
        Assert.assertSame(IDType.SAMORDNINGSNUMMER, sam1.getIDType());

        final String SAM2 = "121261-1211";
        Assert.assertFalse(Personnummer.parse(SAM2).isPresent());
        Personnummer sam2 = Personnummer.parse(SAM2, true).get();
        Assert.assertTrue(Personnummer.parse(SAM2, true).isPresent());
        Assert.assertFalse(sam2.isPersonnummer());
        Assert.assertTrue(sam2.isSamordningsnummer());
        Assert.assertEquals(Integer.valueOf(2), sam2.getChecksum());
        Assert.assertSame(IDType.SAMORDNINGSNUMMER, sam2.getIDType());

        final String SAM3 = "191212611211";
        Assert.assertFalse(Personnummer.parse(SAM3).isPresent());
        Personnummer sam3 = Personnummer.parse(SAM3, true).get();
        Assert.assertTrue(Personnummer.parse(SAM3, true).isPresent());
        Assert.assertFalse(sam3.isPersonnummer());
        Assert.assertTrue(sam3.isSamordningsnummer());
        Assert.assertEquals(Integer.valueOf(2), sam3.getChecksum());
        Assert.assertSame(IDType.SAMORDNINGSNUMMER, sam3.getIDType());

        final String SAM4 = "19121261-1211";
        Personnummer sam4 = Personnummer.parse(SAM4, true).get();
        Assert.assertFalse(Personnummer.parse(SAM4).isPresent());
        Assert.assertTrue(Personnummer.parse(SAM4, true).isPresent());
        Assert.assertFalse(sam4.isPersonnummer());
        Assert.assertTrue(sam4.isSamordningsnummer());
        Assert.assertEquals(Integer.valueOf(2), sam4.getChecksum());
        Assert.assertSame(IDType.SAMORDNINGSNUMMER, sam4.getIDType());
    }
}