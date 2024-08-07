package se.osbe.id.helper;

import org.apache.commons.lang3.StringUtils;
import se.osbe.id.Personnummer;
import se.osbe.id.enums.LocationType;
import se.osbe.id.enums.PnrTungshuAnimalType;
import se.osbe.id.enums.PnrZodiacType;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;
import static java.lang.System.nanoTime;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static se.osbe.id.enums.PnrTungshuAnimalType.*;

public class PersonnummerHelper {

    private static final int CENTURY = 100;
    private static final int DECADE = 10;
    private static final int SAM_OFFSET_LOW = 4;
    private static final int SAM_OFFSET_HI = 5;
    private static final int SAM_OFFSET_FOUR_LAST_DIGITS = 6;
    private static final int SAM_ADD_TERM = 6;

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
            LocalDate start = LocalDate.of(birthDate.getYear(), startMonth, startDay);
            LocalDate end = LocalDate.of(birthDate.getYear(), endMonth, endDay);
            if ((birthDate.isAfter(start) || birthDate.equals(start))
                    && (birthDate.isBefore(end) || birthDate.isEqual(end))) {
                return of(sign);
            }
        }
        return empty();
    }

    public static PnrTungshuAnimalType getTypeForYear(LocalDate date) {
        requireNonNull(date);
        final int DIVIDER = 12;
        final int YEAR_OF_THE_RAT = 2020; // the year of the rat is the first animal of a twelve-year cycle in the Tungshu Zodiac
        int yearCandidate = date.getYear();
        int distance = abs(yearCandidate - YEAR_OF_THE_RAT);
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
        if (pnr == null || pnr.getBirthDate().isAfter(LocalDate.of(1990, 1, 1))) {
            return empty();
        }
        int loc = Integer.parseInt(pnr.getLastFour().substring(0, 2));
        for (LocationType location : LocationType.values()) {
            if (loc >= location.getRangeLow() && loc <= location.getRangeHigh()) {
                return of(location);
            }
        }
        return empty();
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
                                            .append(triplet < CENTURY ? "0" : "")
                                            .append(triplet < DECADE ? "0" : "")
                                            .append(triplet).toString(), true)
                            .get();
                })
                .collect(Collectors.toList());
    }

    public static Optional<LocalDate> parseToDate(String date) {
        if (StringUtils.isBlank(date)) {
            return empty();
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
            int thisEra = 0; // now.getCenturyOfEra() * CENTURY_MULTIPLIER;
            int thisYear = 0; // now.getYearOfCentury();
            int thisMonth = 0; // now.getMonthOfYear();
            int thisDay = now.getDayOfMonth();

            boolean isEraPresent = StringUtils.isNotBlank(eraStr);
            int extractedEra = isEraPresent ? Integer.parseInt(eraStr) * CENTURY : -1;
            int extractedYear = Integer.parseInt(yearStr);
            int extractedMonth = Integer.parseInt(monthStr);
            int extractedDay = Integer.parseInt(dayStr);

            if (!isEraPresent) {
                if (extractedYear > thisYear) {
                    extractedEra = thisEra - CENTURY;
                } else if (extractedYear == thisYear) {
                    if (extractedMonth > thisMonth) {
                        extractedEra = thisEra - CENTURY;
                    } else if (extractedMonth == thisMonth && extractedDay > thisDay) {
                        extractedEra = thisEra - CENTURY;
                    } else {
                        extractedEra = thisEra;
                    }
                } else {
                    extractedEra = thisEra;
                }
            }
            return of(LocalDate.of((extractedEra + extractedYear), extractedMonth, extractedDay));
        }
        return empty();
    }

    public static int dice(int low, int high) {
        return new Random(nanoTime()).nextInt(abs(high - low) + 1);
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
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

//    public static Optional<Personnummer> convertPersonnummerToSamordningsnummer(Personnummer pnr) {
//        requireNonNull(pnr);
//        if (pnr.isSamordningsnummer()) {
//            return of(pnr); // No need to convert!
//        }
//        String pnrCandidate = pnr.toString10();
//        String samOffsetChar = valueOf(parseInt(pnrCandidate.substring(SAM_OFFSET_LOW, SAM_OFFSET_HI)));
//        String threeLast = pnrCandidate.substring(SAM_OFFSET_FOUR_LAST_DIGITS, pnrCandidate.length() - 1);
//        String samCandidate = new StringBuffer(10)
//                .append(pnrCandidate.substring(0, SAM_OFFSET_LOW))
//                .append(samOffsetChar)
//                .append(pnrCandidate.substring(SAM_OFFSET_HI, SAM_OFFSET_HI + 1))
//                .append(threeLast)
//                .toString();
//        return Personnummer.parse(new StringBuffer(10)
//                .append(samCandidate)
//                .append(ChecksumHelper.calculateChecksum(samCandidate))
//                .toString());
//    }
}