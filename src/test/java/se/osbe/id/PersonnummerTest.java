package se.osbe.id;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.osbe.id.enums.GenderType;
import se.osbe.id.enums.PnrZodiacType;
import se.osbe.id.exception.PersonnummerException;
import se.osbe.id.helper.PersonnummerBuilder;
import se.osbe.id.helper.PersonnummerHelper;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDate.now;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static java.util.Optional.empty;
import static org.junit.Assert.*;

public class PersonnummerTest {
    private List<String> _pnrOKList;
    private List<String> _pnrNOKList;

    private String _ERA_NOW;
    private String _YEAR_NOW;
    private String _ERA_AND_YEAR_NOW;
    private String _HUNDRED_YEARS_AGO;
    private String _ONE_YEAR_FROM_NOW;

    private static int HUNDRED_YEARS = 100;
    private static int ONE_YEAR = 1;
    private static int IDX_0 = 0;
    private static int IDX_2 = 2;

    @Before
    public void before() {
        _ERA_NOW = String.valueOf(now().get(ChronoField.YEAR)).substring(IDX_0, IDX_2); // Era and Century
        _YEAR_NOW = String.valueOf(now().get(ChronoField.YEAR)).substring(IDX_2); // Year using two digits
        _ERA_AND_YEAR_NOW = _ERA_NOW + _YEAR_NOW;
        _HUNDRED_YEARS_AGO = "" + now().minusYears(HUNDRED_YEARS).get(YEAR_OF_ERA);
        _ONE_YEAR_FROM_NOW = "" + (now().plusYears(ONE_YEAR).get(YEAR_OF_ERA));

        // OK - Valid permutaions of personnummer input strings
        _pnrOKList = Arrays.asList(
                "1212121212",
                "121212-1212",
                "121212+1212",
                "191212121212",
                "19121212-1212",
                "4604300014",
                "460430-0014",
                "460430+0014",
                "194604300014",
                "19460430-0014",
                "18760112-0301",
                "19801010-0009",
                "19801110-0008",
                "19801210-0007",
                "040229-0308"
        );

        // NOK - Invalid personnummer input strings
        _pnrNOKList = Arrays.asList(
                "1801170000", // 1800, last4 digits all zero
                "180117-0000", // 1800, last4 digits all zero
                "180117+0000", // 1800, last4 digits all zero
                "18180117-0000", // 1800, last4 digits all zero
                "191801170000", // 1900, last4 digits all zero
                "19180117-0000", // 1900, last4 digits all zero
                "201801170000", // 2000, last4 digits all zero
                "20180117-0000", // 2000, last4 digits all zero
                "abc", // no digits at all
                "123", // too short
                "x1212121212", // char before valid digits
                "161212121212", // 16 as era is used to indicate 'Organisationsnummer' (not Pnr)
                "171212121212", // 17 as era is not valid
                "211212121212", // 21 as era is in the future
                "050229-0307", // As 2005 is not a leap year, the date 29/2 is invalid
                "1212121213", // The checksum '3' is invalid
                "121212-1213", // Wrong checksum
                "121212+1213", // Wrong checksum
                "191212121213", // Wrong checksum
                "19121212-1213", // Wrong checksum
                "201212121213", // Wrong checksum
                "20121212-1213", // Wrong checksum
                "21991212-1218", // In the future
                "219912121218", // In the future
                "21991212-1219" // In the future, wrong checksum
        ); // last4 digits all zero
    }

    /*
     * Basic parse validations
     */

    @Test
    public void testSsnParseListOK() {
        // OK test
        for (String okPnr : _pnrOKList) {
            var pnrOpt = Personnummer.parse(okPnr);
            assertTrue("Personnummer '" + okPnr + "' should be correct, but its not!", pnrOpt.isPresent());
            int len = okPnr.length();
            assertEquals("Calculated checksum is wrong compared to original", okPnr.substring(len - 1),
                    ("" + pnrOpt.get().getChecksum()));
        }
    }

    @Test
    public void testPnrParseHundredPlusYearsOldOK() {
        assertTrue(Personnummer.parse(_HUNDRED_YEARS_AGO + "0115-0459").get().isHundredYears());
        assertTrue(Personnummer.parse(_HUNDRED_YEARS_AGO + "0115-0450", true).get().isHundredYears());
        assertEquals(1924, Personnummer.parse(_HUNDRED_YEARS_AGO + "0115-0459").get().getBirthDate().get(YEAR_OF_ERA));
        assertEquals(1924, Personnummer.parse(_HUNDRED_YEARS_AGO + "0115-0450", true).get().getBirthDate().get(YEAR_OF_ERA));
        assertTrue(Personnummer.parse(_YEAR_NOW + "0115+0459").get().isHundredYears());
        assertTrue(Personnummer.parse(_YEAR_NOW + "0115+0450", true).get().isHundredYears());
        assertEquals(empty(), Personnummer.parse(_HUNDRED_YEARS_AGO + "0115+0459")); // both era and '+' is not valid

        assertTrue(Personnummer.parse("191212121212").get().isHundredYears());
        assertTrue(Personnummer.parse("19121212-1212", true).get().isHundredYears());
        assertTrue(Personnummer.parse("121212+1212", true).get().isHundredYears());
        assertEquals("121212+1212", Personnummer.parse("191212121212").get().toString());
        assertEquals("121212+1212", Personnummer.parse("191212121212").get().toString11());
    }

    @Test
    public void testPnrParseOneYearInFutureOK() {
        assertEquals(empty(), Personnummer.parse(_ONE_YEAR_FROM_NOW.substring(IDX_2) + "0115-0459"));
        assertFalse(Personnummer.parse(_ONE_YEAR_FROM_NOW.substring(IDX_2) + "0115-0450", true).get().isHundredYears());
        assertEquals((Integer.parseInt(_HUNDRED_YEARS_AGO) + 1), Personnummer.parse(_ONE_YEAR_FROM_NOW.substring(IDX_2) + "0115-0450", true).get().getBirthDate().get(YEAR_OF_ERA));
        assertEquals(_ONE_YEAR_FROM_NOW, String.valueOf(Personnummer.parse(_ONE_YEAR_FROM_NOW + "0115-0450", true).get().getBirthDate().get(YEAR_OF_ERA)));
    }

    @Test
    public void testPnrParseEmptyNOK() {
        // NOK test
        assertFalse(Personnummer.parse(null).isPresent());
        assertFalse(Personnummer.parse(null, false).isPresent());
        assertFalse(Personnummer.parse(null, true).isPresent());
        assertFalse(Personnummer.parse("").isPresent());
        assertFalse(Personnummer.parse("", false).isPresent());
        assertFalse(Personnummer.parse("", true).isPresent());
    }

    @Test
    public void testPnrWithEraAndPlusNOK() {
        // illegal combination, can not have both era AND a plus sign at the
        // same time
        assertFalse(Personnummer.parse("19010101+0106").isPresent());
    }

    @Test
    public void testPnrNoChecksumAndNotForgivingNOK() {
        assertFalse(Personnummer.parse("010101010", false).isPresent());
        assertFalse(Personnummer.parse("19010101010", false).isPresent());
    }

    @Test
    public void testPnrMonthNOK() {
        // Month = 20
        assertFalse(Personnummer.parse("012003-0012").isPresent());
        assertFalse(Personnummer.parse("012003-0012", false).isPresent());
        assertFalse(Personnummer.parse("012003-0012", true).isPresent());

        assertFalse(Personnummer.parse("012003+0012").isPresent());
        assertFalse(Personnummer.parse("012003+0012", false).isPresent());
        assertFalse(Personnummer.parse("012003+0012", true).isPresent());

        assertFalse(Personnummer.parse("19012003-0012").isPresent());
        assertFalse(Personnummer.parse("19012003-0012", false).isPresent());
        assertFalse(Personnummer.parse("19012003-0012", true).isPresent());

        assertFalse(Personnummer.parse("20012003-0012").isPresent());
        assertFalse(Personnummer.parse("20012003-0012", false).isPresent());
        assertFalse(Personnummer.parse("20012003-0012", true).isPresent());

        // Month = 99
        assertFalse(Personnummer.parse("019903-0012").isPresent());
        assertFalse(Personnummer.parse("019903-0012", false).isPresent());
        assertFalse(Personnummer.parse("019903-0012", true).isPresent());

        assertFalse(Personnummer.parse("019903+0012").isPresent());
        assertFalse(Personnummer.parse("019903+0012", false).isPresent());
        assertFalse(Personnummer.parse("019903+0012", true).isPresent());

        assertFalse(Personnummer.parse("19019903-0012").isPresent());
        assertFalse(Personnummer.parse("19019903-0012", false).isPresent());
        assertFalse(Personnummer.parse("19019903-0012", true).isPresent());

        assertFalse(Personnummer.parse("20019903-0012").isPresent());
        assertFalse(Personnummer.parse("20019903-0012", false).isPresent());
        assertFalse(Personnummer.parse("20019903-0012", true).isPresent());
    }

    @Test
    public void testPnrParseNOK() {
        _pnrNOKList.stream()
                .forEach(p -> assertFalse("False candidates for Pnr '" + p + "' should not be valid, but was!",
                        Personnummer.parse(p).isPresent()));
    }

    @Test
    public void testFutureDateOK() {
        var pOpt = Personnummer.parse("2112121212", true);
        assertTrue("Date of birth can not be in the future", pOpt.isPresent());
    }

    @Test
    public void testFutureDateNOK() {
        var pOpt = Personnummer.parse("211212121212"); // year 2112
        assertFalse("Date of birth can not be in the future, unless forgiving flag is used as input argument " +
                "when parsing", pOpt.isPresent());
    }

    /*
     * Equals tests
     */

    @Test
    public void testSsn10Equals() {
        _pnrOKList.stream().filter(p -> p.length() == 10).forEach(p -> {
            assertEquals(p, Personnummer.parse(p).get().toString10());
            assertEquals(10, Personnummer.parse(p).get().toString10().length());
        });
    }

    @Test
    public void testSsn11Equals() {
        _pnrOKList.stream().filter(p -> p.length() == 11).forEach(p -> {
            assertEquals(p, Personnummer.parse(p).get().toString11());
            assertEquals(11, Personnummer.parse(p).get().toString11().length());
        });
    }

    @Test
    public void testSsn12Equals() {
        _pnrOKList.stream().filter(p -> p.length() == 12).forEach(p -> {
            assertEquals(p, Personnummer.parse(p).get().toString12());
            assertEquals(12, Personnummer.parse(p).get().toString12().length());
        });
    }

    @Test
    public void testSsn13Equals() {
        _pnrOKList.stream().filter(p -> p.length() == 13).forEach(p -> {
            assertEquals(p, Personnummer.parse(p).get().toString13());
            assertEquals(13, Personnummer.parse(p).get().toString13().length());
        });
    }

    /*
     * Test Helper class features
     */

    @Test
    public void testZodiacSign() {
        String[][] pnrArr = {
                // start // middle // end
                {"CAPRICORNUS", "120101-0000", "120115-0000", "120119-0000"},
                {"AQUARIUS", "120120-0000", "120215-0000", "120218-0000"},
                {"PISCES", "120219-0000", "120315-0000", "120320-0000"},

                {"ARIES", "120321-0000", "120415-0000", "120419-0000"},
                {"TAURUS", "120420-0000", "120515-0000", "120521-0000"},
                {"GEMINI", "120522-0000", "120615-0000", "120621-0000"},

                {"CANCER", "120622-0000", "120715-0000", "120722-0000"},
                {"LEO", "120723-0000", "120815-0000", "120822-0000"},
                {"VIRGO", "120823-0000", "120915-0000", "120922-0000"},

                {"LIBRA", "120923-0000", "121015-0000", "121022-0000"},
                {"SCORPIO", "121023-0000", "121115-0000", "121121-0000"},
                {"SAGITTARIUS", "121222-0000", "121225-0000", "121231-0000"}};
        for (int x = 0; x < pnrArr.length; x++) {
            PnrZodiacType zodiacSign = PnrZodiacType.valueOf(PnrZodiacType.class, pnrArr[x][0]);
            for (int y = 1; y < 3; y++) {
                var tempPnr = Personnummer.parse(pnrArr[x][y], true);
                if (tempPnr.isPresent()) {
                    assertEquals(zodiacSign, PersonnummerHelper.getZodiacSign(Personnummer.parse(pnrArr[x][y], true).get()).get());
                }
            }
        }
    }

    @Test
    public void testGenderOK() {
        for (String okPnr : _pnrOKList) {
            Personnummer pnr = Personnummer.parse(okPnr).get();
            boolean isEven = (Integer.parseInt(pnr.getLastFour().substring(2, 3)) % 2 == 0);
            assertEquals((isEven ? "woman" : "man"), pnr.getGender("woman", "man"));
            assertTrue(pnr.getGender() == GenderType.MALE || pnr.getGender() == GenderType.FEMALE);
        }
    }

    @Test
    public void testPnrToString() {
        for (String okPnr : _pnrOKList) {
            Personnummer pnr = Personnummer.parse(okPnr).get();
            assertTrue(pnr.getAgeNow() >= 0);
            assertEquals(11, pnr.toString().length());
            assertEquals(10, pnr.toString10().length());
            assertEquals(11, pnr.toString11().length());
            assertEquals(12, pnr.toString12().length());
            assertEquals(13, pnr.toString13().length());
            assertTrue(pnr.toString11().contains("-") || pnr.toString11().contains("+"));
        }
    }

    @Test
    public void testSsnLast4Digits() {
        for (String okPnr : _pnrOKList) {
            Personnummer pnr = Personnummer.parse(okPnr).get();
            assertEquals(4, pnr.getLastFour().length());
            int last4 = Integer.parseInt(pnr.getLastFour());
            assertTrue(last4 >= 0 && last4 <= 9999);
        }
    }

    @Test
    public void testSsnAge() {
        _pnrOKList.stream()
                .forEach(p -> assertTrue(Personnummer.parse(p).get().getAgeNow() >= 0));
    }

    @Test
    public void testGetDaysFromBirth() {
        try {
            int rand = PersonnummerHelper.dice(1, 130);
            LocalDate earlier = now().minusDays(rand);
            new PersonnummerBuilder().setStartAndEndDate(earlier, earlier).build().forEach(p -> assertEquals(rand, p.getDaysSinceBirth()));
        } catch (PersonnummerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSsnPlusSign() {
        Personnummer pnr1 = Personnummer.parse("121212+1212").get();
        assertTrue(pnr1.isHundredYears());
        assertTrue(pnr1.toString().contains("+"));
        assertTrue(pnr1.toString11().contains("+"));

        Personnummer pnr2 = Personnummer.parse("19121212-1212").get();
        assertTrue(pnr2.isHundredYears());
        assertTrue(pnr2.toString().contains("+"));
        assertTrue(pnr2.toString11().contains("+"));

        Personnummer pnr3 = Personnummer.parse("191212121212").get();
        assertTrue(pnr3.isHundredYears());
        assertTrue(pnr3.toString().contains("+"));
        assertTrue(pnr3.toString11().contains("+"));
    }

    @Test
    public void testSsnBirthDate() throws Exception {
        final String DAY_OF_WEEK = "EEEE";
        var pnrOpt = Personnummer.parse("194604300014");
        Personnummer pnr = pnrOpt.orElseThrow(() -> new Exception("Not a valid Personnummer"));
        _pnrOKList.forEach(p -> Assert.assertNotNull(Personnummer.parse(p).get().getBirthDate()));
    }
}