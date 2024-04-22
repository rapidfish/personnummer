package se.osbe.id;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import se.osbe.id.enums.LocationType;
import se.osbe.id.enums.PnrZodiacType;
import se.osbe.id.helper.PersonnummerHelper;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Random;

import static java.time.LocalDate.now;
import static java.time.temporal.ChronoField.YEAR;
import static org.junit.Assert.*;
import static se.osbe.id.helper.PersonnummerHelper.*;

public class PnrHelperTest {

    @Test
    public void testZodiacSignCapricornus() {
        assertEquals(PnrZodiacType.CAPRICORNUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120101-0010", true).get()).get());
        assertEquals(PnrZodiacType.CAPRICORNUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120115-0010", true).get()).get());
        assertEquals(PnrZodiacType.CAPRICORNUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120119-0010", true).get()).get());
    }

    public void testZodiacSignAquarius() {
        assertEquals(PnrZodiacType.AQUARIUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120120-0010", true).get()).get());
        assertEquals(PnrZodiacType.AQUARIUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120215-0010", true).get()).get());
        assertEquals(PnrZodiacType.AQUARIUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120218-0010", true).get()).get());
    }

    public void testZodiacSignPisces() {
        assertEquals(PnrZodiacType.PISCES, PersonnummerHelper.getZodiacSign(Personnummer.parse("120219-0010", true).get()).get());
        assertEquals(PnrZodiacType.PISCES, PersonnummerHelper.getZodiacSign(Personnummer.parse("120315-0010", true).get()).get());
        assertEquals(PnrZodiacType.PISCES, PersonnummerHelper.getZodiacSign(Personnummer.parse("120320-0010", true).get()).get());
    }

    public void testZodiacSignAries() {
        assertEquals(PnrZodiacType.ARIES, PersonnummerHelper.getZodiacSign(Personnummer.parse("120321-0010", true).get()).get());
        assertEquals(PnrZodiacType.ARIES, PersonnummerHelper.getZodiacSign(Personnummer.parse("120415-0010", true).get()).get());
        assertEquals(PnrZodiacType.ARIES, PersonnummerHelper.getZodiacSign(Personnummer.parse("120419-0010", true).get()).get());
    }

    public void testZodiacSignTaurus() {
        assertEquals(PnrZodiacType.TAURUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120420-0010", true).get()).get());
        assertEquals(PnrZodiacType.TAURUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120515-0010", true).get()).get());
        assertEquals(PnrZodiacType.TAURUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120521-0010", true).get()).get());
    }

    public void testZodiacSignGemini() {
        assertEquals(PnrZodiacType.GEMINI, PersonnummerHelper.getZodiacSign(Personnummer.parse("120522-0010", true).get()).get());
        assertEquals(PnrZodiacType.GEMINI, PersonnummerHelper.getZodiacSign(Personnummer.parse("120615-0010", true).get()).get());
        assertEquals(PnrZodiacType.GEMINI, PersonnummerHelper.getZodiacSign(Personnummer.parse("120621-0010", true).get()).get());
    }

    public void testZodiacSignCancer() {
        assertEquals(PnrZodiacType.CANCER, PersonnummerHelper.getZodiacSign(Personnummer.parse("120622-0010", true).get()).get());
        assertEquals(PnrZodiacType.CANCER, PersonnummerHelper.getZodiacSign(Personnummer.parse("120715-0010", true).get()).get());
        assertEquals(PnrZodiacType.CANCER, PersonnummerHelper.getZodiacSign(Personnummer.parse("120722-0010", true).get()).get());
    }

    public void testZodiacSignLeo() {
        assertEquals(PnrZodiacType.LEO, PersonnummerHelper.getZodiacSign(Personnummer.parse("120723-0010", true).get()).get());
        assertEquals(PnrZodiacType.LEO, PersonnummerHelper.getZodiacSign(Personnummer.parse("120815-0010", true).get()).get());
        assertEquals(PnrZodiacType.LEO, PersonnummerHelper.getZodiacSign(Personnummer.parse("120822-0010", true).get()).get());
    }

    public void testZodiacSignVirgo() {
        assertEquals(PnrZodiacType.VIRGO, PersonnummerHelper.getZodiacSign(Personnummer.parse("120823-0010", true).get()).get());
        assertEquals(PnrZodiacType.VIRGO, PersonnummerHelper.getZodiacSign(Personnummer.parse("120915-0010", true).get()).get());
        assertEquals(PnrZodiacType.VIRGO, PersonnummerHelper.getZodiacSign(Personnummer.parse("120922-0010", true).get()).get());
    }

    public void testZodiacSignLibra() {
        assertEquals(PnrZodiacType.LIBRA, PersonnummerHelper.getZodiacSign(Personnummer.parse("120923-0010", true).get()).get());
        assertEquals(PnrZodiacType.LIBRA, PersonnummerHelper.getZodiacSign(Personnummer.parse("121015-0010", true).get()).get());
        assertEquals(PnrZodiacType.LIBRA, PersonnummerHelper.getZodiacSign(Personnummer.parse("121022-0010", true).get()).get());
    }

    public void testZodiacSignScorpio() {
        assertEquals(PnrZodiacType.SCORPIO, PersonnummerHelper.getZodiacSign(Personnummer.parse("121023-0010", true).get()).get());
        assertEquals(PnrZodiacType.SCORPIO, PersonnummerHelper.getZodiacSign(Personnummer.parse("121115-0010", true).get()).get());
        assertEquals(PnrZodiacType.SCORPIO, PersonnummerHelper.getZodiacSign(Personnummer.parse("121121-0010", true).get()).get());
    }

    public void testZodiacSignSagittarius() {
        assertEquals(PnrZodiacType.SAGITTARIUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("121222-0010", true).get()).get());
        assertEquals(PnrZodiacType.SAGITTARIUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("121225-0010", true).get()).get());
        assertEquals(PnrZodiacType.SAGITTARIUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("121231-0010", true).get()).get());
    }

    //@Test
    public void testAllSsnFromRandomDateUntilToday() {
        // Tests ALL Personnummer from year 1800-- and onwards until today!
        LocalDate now = now();
        final int MIN_YEAR = 1800;
        final int MAX_YEAR = now().getYear();
        int randomYear = MIN_YEAR + new Random().nextInt(MAX_YEAR - MIN_YEAR + 1);
        LocalDate startingDate = LocalDate.of(randomYear, 1, 1);
        while (!startingDate.isAfter(now)) {
            var ssnList = generateAllValidPnrForDate(startingDate);
            ssnList.stream()
                    .forEach(p -> {
                        assertNotNull(p);
                        assertNotNull(p.getChecksum());
                    });
            startingDate = startingDate.plusDays(1);
        }
    }

    //@Test
    public void testAllSsnForARandomDate() {
        int yy = dice(1900, 2014);
        int mm = dice(1, 12);
        int dd = dice(1, 31);
        LocalDate date = null;
        try {
            date = LocalDate.of(yy, mm, dd);
        } catch (Exception e) {
            dd = dice(1, 29);
            try {
                date = LocalDate.of(yy, mm, dd);
            } catch (Exception e1) {
                dd = dice(1, 28);
                date = LocalDate.of(yy, mm, dd);
            }
        }
        var ssnList = generateAllValidPnrForDate(date);
        assertNotNull(("Using random date: " + date + " when fail occurred"), CollectionUtils.isNotEmpty(ssnList));
        assertEquals(("List size is wrong."), 1000, ssnList.size());
    }

    @Test
    public void testSsnForARandomDate() {
        final int MIN_YEAR = 1800;
        final int MAX_YEAR = now().get(YEAR);
        final int MIN_MONTH = 1;
        final int MAX_MONTH = 12;
        final int MIN_DAY = 1;
        final int MAX_DAY = 31;
        int yy = dice(MIN_YEAR, MAX_YEAR);
        int mm = dice(MIN_MONTH, MAX_MONTH);
        int dd = dice(MIN_DAY, MAX_DAY);
        LocalDate date = null;
        try {
            date = LocalDate.of(yy, mm, dd);
        } catch (Exception e) {
            dd = dice(1, 29);
            try {
                date = LocalDate.of(yy, mm, dd);
            } catch (Exception e1) {
                dd = dice(1, 28);
                date = LocalDate.of(yy, mm, dd);
            }
        }
        var ssnList = generateAllValidPnrForDate(date);
        for (Personnummer ssn : ssnList) {
            assertNotNull(("Using random date: " + date + " when fail occurred"), ssn.getChecksum());
        }
    }

    @Test
    public void testAllSsnWithPlusSign() {
        var ssnList = new LinkedList<Personnummer>();
        ssnList.addAll(generateAllValidPnrForDate("" + dice(1900, 1910) + "0101"));
        ssnList.addAll(generateAllValidPnrForDate("" + dice(1800, 1810) + "0101"));

        ssnList.forEach(p -> {
            assertTrue(p.toString13().matches("\\d{8}\\-\\d{4}"));
            assertTrue(p.toString12().matches("\\d{8}\\d{4}"));
            assertTrue(p.toString11().matches("\\d{6}[+]\\d{4}"));
            assertTrue(p.toString10().matches("\\d{6}\\d{4}"));
        });
    }

    @Test
    public void testLocationType1() {
        assertEquals(LocationType.STOCKHOLM, getPlaceOfBirth(Personnummer.parse("19800101-0000", true).get()).get());
        assertEquals(LocationType.STOCKHOLM, getPlaceOfBirth(Personnummer.parse("19800101-1300", true).get()).get());
    }

    @Test
    public void testLocationType2() {
        assertEquals(LocationType.UPPSALA, getPlaceOfBirth(Personnummer.parse("19800101-1400", true).get()).get());
        assertEquals(LocationType.UPPSALA, getPlaceOfBirth(Personnummer.parse("19800101-1500", true).get()).get());
    }

    @Test
    public void testLocationType3() {
        assertEquals(LocationType.SODERMANLAND, getPlaceOfBirth(Personnummer.parse("19800101-1600", true).get()).get());
        assertEquals(LocationType.SODERMANLAND, getPlaceOfBirth(Personnummer.parse("19800101-1800", true).get()).get());
    }

    @Test
    public void testLocationType4() {
        assertEquals(LocationType.OSTERGOTLAND, getPlaceOfBirth(Personnummer.parse("19800101-1900", true).get()).get());
        assertEquals(LocationType.OSTERGOTLAND, getPlaceOfBirth(Personnummer.parse("19800101-2300", true).get()).get());
    }

    @Test
    public void testLocationType5() {
        assertEquals(LocationType.JONKOPING, getPlaceOfBirth(Personnummer.parse("19800101-2400", true).get()).get());
        assertEquals(LocationType.JONKOPING, getPlaceOfBirth(Personnummer.parse("19800101-2600", true).get()).get());
    }

    @Test
    public void testLocationType6() {
        assertEquals(LocationType.KRONOBERG, getPlaceOfBirth(Personnummer.parse("19800101-2700", true).get()).get());
        assertEquals(LocationType.KRONOBERG, getPlaceOfBirth(Personnummer.parse("19800101-2800", true).get()).get());
    }

    @Test
    public void testLocationType7() {
        assertEquals(LocationType.KALMAR, getPlaceOfBirth(Personnummer.parse("19800101-2900", true).get()).get());
        assertEquals(LocationType.KALMAR, getPlaceOfBirth(Personnummer.parse("19800101-3100", true).get()).get());
    }

    @Test
    public void testLocationType8() {
        assertEquals(LocationType.GOTLAND, getPlaceOfBirth(Personnummer.parse("19800101-3200", true).get()).get());
    }

    @Test
    public void testLocationType9() {
        assertEquals(LocationType.BLEKINGE, getPlaceOfBirth(Personnummer.parse("19800101-3300", true).get()).get());
        assertEquals(LocationType.BLEKINGE, getPlaceOfBirth(Personnummer.parse("19800101-3400", true).get()).get());
    }

    @Test
    public void testLocationType10() {
        assertEquals(LocationType.KRISTIANSTAD, getPlaceOfBirth(Personnummer.parse("19800101-3500", true).get()).get());
        assertEquals(LocationType.KRISTIANSTAD, getPlaceOfBirth(Personnummer.parse("19800101-3800", true).get()).get());
    }

    @Test
    public void testLocationType11() {
        assertEquals(LocationType.MALMOHUS, getPlaceOfBirth(Personnummer.parse("19800101-3900", true).get()).get());
        assertEquals(LocationType.MALMOHUS, getPlaceOfBirth(Personnummer.parse("19800101-4500", true).get()).get());
    }

    @Test
    public void testLocationType12() {
        assertEquals(LocationType.HALLAND, getPlaceOfBirth(Personnummer.parse("19800101-4600", true).get()).get());
        assertEquals(LocationType.HALLAND, getPlaceOfBirth(Personnummer.parse("19800101-4700", true).get()).get());
    }

    @Test
    public void testLocationType13() {
        assertEquals(LocationType.GOTEBORG_BOHUS, getPlaceOfBirth(Personnummer.parse("19800101-4800", true).get()).get());
        assertEquals(LocationType.GOTEBORG_BOHUS, getPlaceOfBirth(Personnummer.parse("19800101-5400", true).get()).get());
    }

    @Test
    public void testLocationType14() {
        assertEquals(LocationType.ALVSBORG, getPlaceOfBirth(Personnummer.parse("19800101-5500", true).get()).get());
        assertEquals(LocationType.ALVSBORG, getPlaceOfBirth(Personnummer.parse("19800101-5800", true).get()).get());
    }

    @Test
    public void testLocationType15() {
        assertEquals(LocationType.SKARABORG, getPlaceOfBirth(Personnummer.parse("19800101-5900", true).get()).get());
        assertEquals(LocationType.SKARABORG, getPlaceOfBirth(Personnummer.parse("19800101-6100", true).get()).get());
    }

    @Test
    public void testLocationType16() {
        assertEquals(LocationType.VARMLAND, getPlaceOfBirth(Personnummer.parse("19800101-6200", true).get()).get());
        assertEquals(LocationType.VARMLAND, getPlaceOfBirth(Personnummer.parse("19800101-6400", true).get()).get());
    }

    @Test
    public void testLocationType17() {
        assertEquals(LocationType.UTLANDSFODD1, getPlaceOfBirth(Personnummer.parse("19800101-6500", true).get()).get());
    }

    @Test
    public void testLocationType18() {
        assertEquals(LocationType.OREBRO, getPlaceOfBirth(Personnummer.parse("19800101-6600", true).get()).get());
        assertEquals(LocationType.OREBRO, getPlaceOfBirth(Personnummer.parse("19800101-6800", true).get()).get());
    }

    @Test
    public void testLocationType19() {
        assertEquals(LocationType.VASTMANLAND, getPlaceOfBirth(Personnummer.parse("19800101-6900", true).get()).get());
        assertEquals(LocationType.VASTMANLAND, getPlaceOfBirth(Personnummer.parse("19800101-7000", true).get()).get());
    }

    @Test
    public void testLocationType20() {
        assertEquals(LocationType.KOPPARBERG, getPlaceOfBirth(Personnummer.parse("19800101-7100", true).get()).get());
        assertEquals(LocationType.KOPPARBERG, getPlaceOfBirth(Personnummer.parse("19800101-7300", true).get()).get());
    }

    @Test
    public void testLocationType21() {
        assertEquals(LocationType.UNUSED, getPlaceOfBirth(Personnummer.parse("19800101-7400", true).get()).get());
    }

    @Test
    public void testLocationType22() {
        assertEquals(LocationType.GAVLEBORG, getPlaceOfBirth(Personnummer.parse("19800101-7500", true).get()).get());
        assertEquals(LocationType.GAVLEBORG, getPlaceOfBirth(Personnummer.parse("19800101-7700", true).get()).get());
    }

    @Test
    public void testLocationType23() {
        assertEquals(LocationType.VASTERNORRLAND, getPlaceOfBirth(Personnummer.parse("19800101-7800", true).get()).get());
        assertEquals(LocationType.VASTERNORRLAND, getPlaceOfBirth(Personnummer.parse("19800101-8100", true).get()).get());
    }

    @Test
    public void testLocationType24() {
        assertEquals(LocationType.JAMTLAND, getPlaceOfBirth(Personnummer.parse("19800101-8200", true).get()).get());
        assertEquals(LocationType.JAMTLAND, getPlaceOfBirth(Personnummer.parse("19800101-8400", true).get()).get());
    }

    @Test
    public void testLocationType25() {
        assertEquals(LocationType.VASTERBOTTEN, getPlaceOfBirth(Personnummer.parse("19800101-8500", true).get()).get());
        assertEquals(LocationType.VASTERBOTTEN, getPlaceOfBirth(Personnummer.parse("19800101-8800", true).get()).get());
    }

    @Test
    public void testLocationType26() {
        assertEquals(LocationType.NORRBOTTEN, getPlaceOfBirth(Personnummer.parse("19800101-8900", true).get()).get());
        assertEquals(LocationType.NORRBOTTEN, getPlaceOfBirth(Personnummer.parse("19800101-9200", true).get()).get());
    }

    @Test
    public void testLocationType27() {
        assertEquals(LocationType.UTLANDSFODD2, getPlaceOfBirth(Personnummer.parse("19800101-9300", true).get()).get());
        assertEquals(LocationType.UTLANDSFODD2, getPlaceOfBirth(Personnummer.parse("19800101-9900", true).get()).get());
    }
}
