package se.osbe.id.helper;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import se.osbe.id.enums.LocationType;
import se.osbe.id.enums.PnrTungshuAnimalType;
import se.osbe.id.enums.PnrZodiacType;
import se.osbe.id.Personnummer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.requireNonNull;
import static se.osbe.id.enums.PnrTungshuAnimalType.*;

public class PersonnummerHelper {

    private static final int CENTURY_MULTIPLIER = 100;

    public static Optional<PnrZodiacType> getZodiacSign(Personnummer pnr) {
        return getZodiacSign(pnr.getBirthDate());
    }

    public static Optional<PnrZodiacType> getZodiacSign(LocalDate birthDate) {
        PnrZodiacType[] zodiacSign = PnrZodiacType.values();
        for (PnrZodiacType sign : zodiacSign) {
            int startMonth = Integer.parseInt(sign.getStartDate().split("-")[0]);
            int startDay = Integer.parseInt(sign.getStartDate().split("-")[1]);
            int endMonth = Integer.parseInt(sign.getEndDate().split("-")[0]);
            int endDay = Integer.parseInt(sign.getEndDate().split("-")[1]);
            LocalDate start = new LocalDate(birthDate.getYear(), startMonth, startDay);
            LocalDate end = new LocalDate(birthDate.getYear(), endMonth, endDay);
            if ((birthDate.isAfter(start) || birthDate.equals(start))
                    && (birthDate.isBefore(end) || birthDate.isEqual(end))) {
                return Optional.of(sign);
            }
        }
        return Optional.empty();
    }

    public static PnrTungshuAnimalType getTypeForYear(DateTime date) {
        requireNonNull(date);
        final int DIVIDER = 12;
        final int YEAR_OF_THE_RAT = 2020; // the year of the rat is the first animal of a twelve-year cycle in the Tungshu Zodiac
        int yearCandidate = date.getYear();
        int distance = Math.abs(yearCandidate - YEAR_OF_THE_RAT);
        int mod = distance % DIVIDER;
        if (yearCandidate == YEAR_OF_THE_RAT || mod == 0) {
            return RAT; // offset is destined to be 0, thus the result is the year of the rat.
        }
        boolean isBefore2020 = yearCandidate < YEAR_OF_THE_RAT;
        int offset = isBefore2020 ? DIVIDER - mod : mod;
        switch (offset) {
            case 0:
                return RAT;
            case 1:
                return OX;
            case 2:
                return TIGER;
            case 3:
                return RABBIT;
            case 4:
                return DRAGON;
            case 5:
                return SNAKE;
            case 6:
                return HORSE;
            case 7:
                return GOAT;
            case 8:
                return MONKEY;
            case 9:
                return ROOSTER;
            case 10:
                return DOG;
            case 11:
                return PIG;
            default:
                throw new IllegalArgumentException("No match for animal type and year: " + yearCandidate);
        }
    }

    /**
     * Get the location where the person was born. This can only be done for
     * Personnummer with a date prior to year 1990.
     *
     * @param pnr swedish pnr (personnummer)
     * @return the region of Sweden (län) as an enum where the person was born
     * or null if input was null.
     */
    public static Optional<LocationType> getPlaceOfBirth(Personnummer pnr) {
        if (pnr == null || pnr.getBirthDate().isAfter(new LocalDate(1990, 1, 1))) {
            return Optional.empty();
        }
        int loc = Integer.parseInt(pnr.getLastFour().substring(0, 2));
        for (LocationType location : LocationType.values()) {
            if (loc >= location.getRangeLow() && loc <= location.getRangeHigh()) {
                return Optional.of(location);
            }
        }
        return Optional.empty();
    }

    /**
     * Generates all valid Personnummer for a date given as parameter.
     * <p>
     * Dates can be on the form 'yymmdd' or 'yyyymmdd' with any kind of
     * non-digit delimiters. e.g. 1912-12-31 or 2013/01/12 or 120523
     *
     * @param date Generate all valid Personnummer for this date
     * @return all valid Personnummer numbers for the date given as parameter.
     */
    public static List<Personnummer> generateAllValidPnrForDate(String date) {
        if (StringUtils.isBlank(date)) {
            return Collections.emptyList();
        }
        return generateAllValidPnrForDate(parseToDate(date).get());
    }

    /**
     * Generates all valid Personnummer for a date given as parameter.
     *
     * @param date Generate all valid Personnummer for this date
     * @return all valid Personnummer for the date given as parameter.
     */
    public static List<Personnummer> generateAllValidPnrForDate(LocalDate date) {
        if (date == null) {
            return Collections.emptyList();
        }
        return IntStream.rangeClosed(0, 999).mapToObj(triplet -> {
            return Personnummer.parse(
                    new StringBuilder()
                            .append(date.toString().replaceAll("\\D", ""))
                            .append(triplet < 100 ? "0" : "")
                            .append(triplet < 10 ? "0" : "")
                            .append(triplet).toString(), true)
                    .get();
        })
                .collect(Collectors.toList());
    }

    public static Optional<LocalDate> parseToDate(String date) {
        if (StringUtils.isBlank(date)) {
            return Optional.empty();
        }
        String dateStr = date.trim().replaceAll("\\D", "");
        final Pattern pattern = Pattern.compile("^(?<era>\\d{2})?(?<year>\\d{2})(?<month>\\d{2})(?<day>\\d{2})$");
        Matcher matcher = pattern.matcher(dateStr);
        if (matcher.find()) {
            String eraStr = matcher.group("era");
            String yearStr = matcher.group("year");
            String monthStr = matcher.group("month");
            String dayStr = matcher.group("day");

            LocalDate now = LocalDate.now();
            int thisEra = now.getCenturyOfEra() * CENTURY_MULTIPLIER;
            int thisYear = now.getYearOfCentury();
            int thisMonth = now.getMonthOfYear();
            int thisDay = now.getDayOfMonth();

            boolean isEraPresent = StringUtils.isNotBlank(eraStr);
            int extractedEra = isEraPresent ? Integer.parseInt(eraStr) * CENTURY_MULTIPLIER : -1;
            int extractedYear = Integer.parseInt(yearStr);
            int extractedMonth = Integer.parseInt(monthStr);
            int extractedDay = Integer.parseInt(dayStr);

            if (!isEraPresent) {
                if (extractedYear > thisYear) {
                    extractedEra = thisEra - CENTURY_MULTIPLIER;
                } else if (extractedYear == thisYear) {
                    if (extractedMonth > thisMonth) {
                        extractedEra = thisEra - CENTURY_MULTIPLIER;
                    } else if (extractedMonth == thisMonth && extractedDay > thisDay) {
                        extractedEra = thisEra - CENTURY_MULTIPLIER;
                    } else {
                        extractedEra = thisEra;
                    }
                } else {
                    extractedEra = thisEra;
                }
            }
            return Optional.of(new LocalDate((extractedEra + extractedYear), extractedMonth, extractedDay));
        }
        return Optional.empty();
    }

    public static int dice(int low, int high) {
        boolean reverse = (low > high);
        int range = reverse ? (low - high) : (high - low);
        int nextVal = new Random(System.nanoTime()).nextInt(range + 1);
        return (nextVal + (reverse ? high : low));
    }

    /**
     * Finds any Personnummer, based on a regex pattern, within the list given
     * as argument
     *
     * @param regex   the regular expression pattern to match against the list in
     *                the second argument.
     * @param pnrList a list with Personnummer obj to be searched
     * @return list with Personnummer obj that match the regex pattern given as
     * first arg.
     */
    public static List<Personnummer> findPersonnummerInList(String regex, List<Personnummer> pnrList) {
        return findPersonnummerInList(regex, 12, pnrList);
    }

    /**
     * Finds any Personnummer, based on a regex pattern, within the list given
     * as argument
     *
     * @param regex     the regular expression pattern to match against the list in
     *                  the second argument.
     * @param pnrLength use this length of each Personnummer in list when matching
     *                  pattern
     * @param pnrList   a list with Personnummer obj to be searched
     * @return list with Personnummer obj that match the regex pattern given as
     * first arg.
     */
    public static List<Personnummer> findPersonnummerInList(String regex, int pnrLength, List<Personnummer> pnrList) {
        Pattern pattern = Pattern.compile(regex);
        return pnrList.stream().filter(p ->
                (pnrLength == 13 && pattern.matcher(p.toString13()).find()) ||
                        (pnrLength == 12 && pattern.matcher(p.toString12()).find()) ||
                        (pnrLength == 11 && pattern.matcher(p.toString11()).find()) ||
                        (pnrLength == 10 && pattern.matcher(p.toString10()).find())
        ).collect(Collectors.toList());
    }

    public String getNameOfWeekdayForBirth(Personnummer pnr) {
        return getNameOfWeekdayForDate(pnr.getBirthDate());
    }

    public String getNameOfWeekdayForDate(LocalDate date) {
        return DateTimeFormat.forPattern("EEEE").print(date);
    }
}