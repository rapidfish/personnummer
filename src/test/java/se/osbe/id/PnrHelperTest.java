package se.osbe.id;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.junit.Assert;
import org.junit.Test;
import se.osbe.id.enums.LocationType;
import se.osbe.id.enums.PnrZodiacType;
import se.osbe.id.helper.PersonnummerHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class PnrHelperTest {

    @Test
    public void testZodiacSignCapricornus() {
        Assert.assertEquals(PnrZodiacType.CAPRICORNUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120101-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.CAPRICORNUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120115-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.CAPRICORNUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120119-0010", true).get()).get());
    }

    public void testZodiacSignAquarius() {
        Assert.assertEquals(PnrZodiacType.AQUARIUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120120-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.AQUARIUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120215-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.AQUARIUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120218-0010", true).get()).get());
    }

    public void testZodiacSignPisces() {
        Assert.assertEquals(PnrZodiacType.PISCES, PersonnummerHelper.getZodiacSign(Personnummer.parse("120219-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.PISCES, PersonnummerHelper.getZodiacSign(Personnummer.parse("120315-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.PISCES, PersonnummerHelper.getZodiacSign(Personnummer.parse("120320-0010", true).get()).get());
    }

    public void testZodiacSignAries() {
        Assert.assertEquals(PnrZodiacType.ARIES, PersonnummerHelper.getZodiacSign(Personnummer.parse("120321-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.ARIES, PersonnummerHelper.getZodiacSign(Personnummer.parse("120415-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.ARIES, PersonnummerHelper.getZodiacSign(Personnummer.parse("120419-0010", true).get()).get());
    }

    public void testZodiacSignTaurus() {
        Assert.assertEquals(PnrZodiacType.TAURUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120420-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.TAURUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120515-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.TAURUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("120521-0010", true).get()).get());
    }

    public void testZodiacSignGemini() {
        Assert.assertEquals(PnrZodiacType.GEMINI, PersonnummerHelper.getZodiacSign(Personnummer.parse("120522-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.GEMINI, PersonnummerHelper.getZodiacSign(Personnummer.parse("120615-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.GEMINI, PersonnummerHelper.getZodiacSign(Personnummer.parse("120621-0010", true).get()).get());
    }

    public void testZodiacSignCancer() {
        Assert.assertEquals(PnrZodiacType.CANCER, PersonnummerHelper.getZodiacSign(Personnummer.parse("120622-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.CANCER, PersonnummerHelper.getZodiacSign(Personnummer.parse("120715-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.CANCER, PersonnummerHelper.getZodiacSign(Personnummer.parse("120722-0010", true).get()).get());
    }

    public void testZodiacSignLeo() {
        Assert.assertEquals(PnrZodiacType.LEO, PersonnummerHelper.getZodiacSign(Personnummer.parse("120723-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.LEO, PersonnummerHelper.getZodiacSign(Personnummer.parse("120815-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.LEO, PersonnummerHelper.getZodiacSign(Personnummer.parse("120822-0010", true).get()).get());
    }

    public void testZodiacSignVirgo() {
        Assert.assertEquals(PnrZodiacType.VIRGO, PersonnummerHelper.getZodiacSign(Personnummer.parse("120823-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.VIRGO, PersonnummerHelper.getZodiacSign(Personnummer.parse("120915-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.VIRGO, PersonnummerHelper.getZodiacSign(Personnummer.parse("120922-0010", true).get()).get());
    }

    public void testZodiacSignLibra() {
        Assert.assertEquals(PnrZodiacType.LIBRA, PersonnummerHelper.getZodiacSign(Personnummer.parse("120923-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.LIBRA, PersonnummerHelper.getZodiacSign(Personnummer.parse("121015-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.LIBRA, PersonnummerHelper.getZodiacSign(Personnummer.parse("121022-0010", true).get()).get());
    }

    public void testZodiacSignScorpio() {
        Assert.assertEquals(PnrZodiacType.SCORPIO, PersonnummerHelper.getZodiacSign(Personnummer.parse("121023-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.SCORPIO, PersonnummerHelper.getZodiacSign(Personnummer.parse("121115-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.SCORPIO, PersonnummerHelper.getZodiacSign(Personnummer.parse("121121-0010", true).get()).get());
    }

    public void testZodiacSignSagittarius() {
        Assert.assertEquals(PnrZodiacType.SAGITTARIUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("121222-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.SAGITTARIUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("121225-0010", true).get()).get());
        Assert.assertEquals(PnrZodiacType.SAGITTARIUS, PersonnummerHelper.getZodiacSign(Personnummer.parse("121231-0010", true).get()).get());
   }

    //@Test
    public void testAllSsnFromRandomDateUntilToday() {
        // Tests ALL Personnummer from year 1800-- and onwards until today!
        LocalDate now = LocalDate.now();
        int nowAsInt = Integer.parseInt(now.toString("yyyy"));
        // int randomYear = PersonnummerHelper.dice(1800, nowAsInt);  // Randomize a year between 1800 -- now
        int randomYear = 1800;
        LocalDate startingDate = new LocalDate(randomYear, 1, 1);
        while (!startingDate.isAfter(now)) {
            List<Personnummer> ssnList = PersonnummerHelper.generateAllValidPnrForDate(startingDate.toString("yyyy-MM-dd"));
            ssnList.stream()
                    .forEach(p -> {
                        Assert.assertNotNull(p);
                        Assert.assertNotNull(p.getChecksum());
                    });
            startingDate = startingDate.plusDays(1);
        }
    }

    //@Test
    public void testAllSsnForARandomDate() {
        int yy = PersonnummerHelper.dice(1900, 2014);
        int mm = PersonnummerHelper.dice(1, 12);
        int dd = PersonnummerHelper.dice(1, 31);
        LocalDate date = null;
        try {
            date = new LocalDate(yy, mm, dd);
        } catch (IllegalFieldValueException e) {
            dd = PersonnummerHelper.dice(1, 29);
            try {
                date = new LocalDate(yy, mm, dd);
            } catch (IllegalFieldValueException e1) {
                dd = PersonnummerHelper.dice(1, 28);
                date = new LocalDate(yy, mm, dd);
            }
        }
        List<Personnummer> ssnList = PersonnummerHelper.generateAllValidPnrForDate(date.toString(new DateTimeFormatterBuilder().appendYear(4, 4).appendMonthOfYear(2).appendDayOfMonth(2).toFormatter()));
        Assert.assertNotNull(("Using random date: " + date + " when fail occurred"), CollectionUtils.isNotEmpty(ssnList));
        Assert.assertEquals(("List size is wrong."), 1000, ssnList.size());
    }

    @Test
    public void testSsnForARandomDate() {
        int yy = PersonnummerHelper.dice(1900, 2014);
        int mm = PersonnummerHelper.dice(1, 12);
        int dd = PersonnummerHelper.dice(1, 31);
        LocalDate date = null;
        try {
            date = new LocalDate(yy, mm, dd);
        } catch (IllegalFieldValueException e) {
            dd = PersonnummerHelper.dice(1, 29);
            try {
                date = new LocalDate(yy, mm, dd);
            } catch (IllegalFieldValueException e1) {
                dd = PersonnummerHelper.dice(1, 28);
                date = new LocalDate(yy, mm, dd);
            }
        }
        List<Personnummer> ssnList = PersonnummerHelper.generateAllValidPnrForDate(date.toString(new DateTimeFormatterBuilder().appendYear(4, 4).appendMonthOfYear(2).appendDayOfMonth(2).toFormatter()));
        for (Personnummer ssn : ssnList) {
            Assert.assertNotNull(("Using random date: " + date + " when fail occurred"), ssn.getChecksum());
        }
    }

    @Test
    public void testAllSsnWithPlusSign() {
        List<Personnummer> ssnList = new LinkedList<Personnummer>();
        ssnList.addAll(PersonnummerHelper.generateAllValidPnrForDate("" + PersonnummerHelper.dice(1900, 1910) + "0101"));
        ssnList.addAll(PersonnummerHelper.generateAllValidPnrForDate("" + PersonnummerHelper.dice(1800, 1810) + "0101"));
        ssnList.forEach(p -> {
            Assert.assertTrue(p.toString13().matches("\\d{8}\\-\\d{4}"));
            Assert.assertTrue(p.toString12().matches("\\d{8}\\d{4}"));
            Assert.assertTrue(p.toString11().matches("\\d{6}\\+\\d{4}"));
            Assert.assertTrue(p.toString10().matches("\\d{6}\\d{4}"));
        });
    }

    @Test
    public void testLocation1() {
        Assert.assertEquals(LocationType.STOCKHOLM, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-0000", true).get()).get());
        Assert.assertEquals(LocationType.STOCKHOLM, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-1300", true).get()).get());
    }

    @Test
    public void testLocation2() {
        Assert.assertEquals(LocationType.UPPSALA, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-1400", true).get()).get());
        Assert.assertEquals(LocationType.UPPSALA, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-1500", true).get()).get());
    }

    @Test
    public void testLocation3() {
        Assert.assertEquals(LocationType.SODERMANLAND, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-1600", true).get()).get());
        Assert.assertEquals(LocationType.SODERMANLAND, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-1800", true).get()).get());
    }

    @Test
    public void testLocation4() {
        Assert.assertEquals(LocationType.OSTERGOTLAND, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-1900", true).get()).get());
        Assert.assertEquals(LocationType.OSTERGOTLAND, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-2300", true).get()).get());
    }

    @Test
    public void testLocation5() {
        Assert.assertEquals(LocationType.JONKOPING, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-2400", true).get()).get());
        Assert.assertEquals(LocationType.JONKOPING, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-2600", true).get()).get());
    }

    @Test
    public void testLocation6() {
        Assert.assertEquals(LocationType.KRONOBERG, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-2700", true).get()).get());
        Assert.assertEquals(LocationType.KRONOBERG, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-2800", true).get()).get());
    }

    @Test
    public void testLocation7() {
        Assert.assertEquals(LocationType.KALMAR, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-2900", true).get()).get());
        Assert.assertEquals(LocationType.KALMAR, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-3100", true).get()).get());
    }

    @Test
    public void testLocation8() {
        Assert.assertEquals(LocationType.GOTLAND, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-3200", true).get()).get());
    }

    @Test
    public void testLocation9() {
        Assert.assertEquals(LocationType.BLEKINGE, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-3300", true).get()).get());
        Assert.assertEquals(LocationType.BLEKINGE, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-3400", true).get()).get());
    }

    @Test
    public void testLocation10() {
        Assert.assertEquals(LocationType.KRISTIANSTAD, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-3500", true).get()).get());
        Assert.assertEquals(LocationType.KRISTIANSTAD, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-3800", true).get()).get());
    }

    @Test
    public void testLocation11() {
        Assert.assertEquals(LocationType.MALMOHUS, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-3900", true).get()).get());
        Assert.assertEquals(LocationType.MALMOHUS, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-4500", true).get()).get());
    }

    @Test
    public void testLocation12() {
        Assert.assertEquals(LocationType.HALLAND, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-4600", true).get()).get());
        Assert.assertEquals(LocationType.HALLAND, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-4700", true).get()).get());
    }

    @Test
    public void testLocation13() {
        Assert.assertEquals(LocationType.GOTEBORG_BOHUS, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-4800", true).get()).get());
        Assert.assertEquals(LocationType.GOTEBORG_BOHUS, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-5400", true).get()).get());
    }

    @Test
    public void testLocation14() {
        Assert.assertEquals(LocationType.ALVSBORG, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-5500", true).get()).get());
        Assert.assertEquals(LocationType.ALVSBORG, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-5800", true).get()).get());
    }

    @Test
    public void testLocation15() {
        Assert.assertEquals(LocationType.SKARABORG, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-5900", true).get()).get());
        Assert.assertEquals(LocationType.SKARABORG, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-6100", true).get()).get());
    }

    @Test
    public void testLocation16() {
        Assert.assertEquals(LocationType.VARMLAND, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-6200", true).get()).get());
        Assert.assertEquals(LocationType.VARMLAND, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-6400", true).get()).get());
    }

    @Test
    public void testLocation17() {
        Assert.assertEquals(LocationType.UTLANDSFODD1, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-6500", true).get()).get());
    }

    @Test
    public void testLocation18() {
        Assert.assertEquals(LocationType.OREBRO, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-6600", true).get()).get());
        Assert.assertEquals(LocationType.OREBRO, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-6800", true).get()).get());
    }

    @Test
    public void testLocation19() {
        Assert.assertEquals(LocationType.VASTMANLAND, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-6900", true).get()).get());
        Assert.assertEquals(LocationType.VASTMANLAND, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-7000", true).get()).get());
    }

    @Test
    public void testLocation20() {
        Assert.assertEquals(LocationType.KOPPARBERG, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-7100", true).get()).get());
        Assert.assertEquals(LocationType.KOPPARBERG, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-7300", true).get()).get());
    }

    @Test
    public void testLocation21() {
        Assert.assertEquals(LocationType.UNUSED, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-7400", true).get()).get());
    }

    @Test
    public void testLocation22() {
        Assert.assertEquals(LocationType.GAVLEBORG, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-7500", true).get()).get());
        Assert.assertEquals(LocationType.GAVLEBORG, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-7700", true).get()).get());
    }

    @Test
    public void testLocation23() {
        Assert.assertEquals(LocationType.VASTERNORRLAND, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-7800", true).get()).get());
        Assert.assertEquals(LocationType.VASTERNORRLAND, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-8100", true).get()).get());
    }

    @Test
    public void testLocation24() {
        Assert.assertEquals(LocationType.JAMTLAND, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-8200", true).get()).get());
        Assert.assertEquals(LocationType.JAMTLAND, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-8400", true).get()).get());
    }

    @Test
    public void testLocation25() {
        Assert.assertEquals(LocationType.VASTERBOTTEN, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-8500", true).get()).get());
        Assert.assertEquals(LocationType.VASTERBOTTEN, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-8800", true).get()).get());
    }

    @Test
    public void testLocation26() {
        Assert.assertEquals(LocationType.NORRBOTTEN, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-8900", true).get()).get());
        Assert.assertEquals(LocationType.NORRBOTTEN, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-9200", true).get()).get());
    }

    @Test
    public void testLocation27() {
        Assert.assertEquals(LocationType.UTLANDSFODD2, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-9300", true).get()).get());
        Assert.assertEquals(LocationType.UTLANDSFODD2, PersonnummerHelper.getPlaceOfBirth(Personnummer.parse("19800101-9900", true).get()).get());
    }
}
