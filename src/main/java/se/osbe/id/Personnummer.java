package se.osbe.id;

import org.joda.time.Days;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import se.osbe.id.enums.GenderType;
import se.osbe.id.enums.IDType;
import se.osbe.id.helper.ChecksumHelper;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;

public class Personnummer implements Comparable<Personnummer>, Identifiable {

    private static final String PNR_PATTERN = "^(?<era>\\d{2})?(?<year>\\d{2})(?<month>0[1-9]|1[012])(?<day>0[1-9]|[12][0-9]|3[01]|[6789]\\d)(?<sign>[\\-+])?(?<last3>\\d{3})(?<checksum>\\d{1})?$";
    private static final Pattern PRE_COMPILED_PNR_PATTERN = Pattern.compile(PNR_PATTERN);

    private static final int LAST4DIGITS = 4;
    private static final int MIN_CENTURY = 18;
    private static final int SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE = 60;

    private LocalDate _pnrDate;
    private int[] _lastDigits = new int[LAST4DIGITS];
    private final boolean _isForgiving;

    private boolean _isSamordningsnummer;

    // Used for optimizing toString
    private String toString10;
    private String toString11;
    private String toString12;
    private String toString13;

    private Personnummer() {
        /*
         * !!! private constructor !!!
         *
         * Use the static parser methods to instantiate objects of this class...
         *
         * e.g. Personnummer.parse("121212-1212") will get you an Optional<Personnummer> object.
         *
         *  Optional<Personnummer> pnrOpt = Personnummer.parse("121212-1212");
         * if(pnrOpt.isPresent()) {
         *     System.out.println("The SSN is valid: " + pnrOpt.get().isValid());
         * }
         */
        this(null, null, false, false);
    }

    private Personnummer(LocalDate pnrDate, String lastDigits, boolean isForgiving, boolean isSamordningsnummer) {
        _pnrDate = pnrDate;
        IntStream.range(0, lastDigits.length()).forEach(i -> {
            _lastDigits[i] = parseInt("" + lastDigits.charAt(i));
        });
        _isForgiving = isForgiving;
        _isSamordningsnummer = isSamordningsnummer;
    }

    private static String calculateChecksum(String year, String month, String day, String last3) {
        char[] pnrNumbersWithoutChecksum = String.join("", year, month, day, last3).toCharArray();
        return ChecksumHelper.calculateChecksum(pnrNumbersWithoutChecksum);
    }

    private static LocalDate resolveBirthDate(String era, int yearInt, int monthInt, int dayInt, boolean plus) {
        int yearCandidate;
        if (era != null) {
            yearCandidate = ((parseInt(era) * 100) + yearInt);
            return new LocalDate(yearCandidate, monthInt, dayInt);
        }
        LocalDate now = LocalDate.now();
        int newEra = now.getCenturyOfEra();
        newEra = (plus) ? (--newEra) : newEra;
        yearCandidate = ((newEra * 100) + yearInt);
        LocalDate birthDate = new LocalDate(yearCandidate, monthInt, dayInt);
        if (birthDate.isAfter(now)) {
            yearCandidate = (((now.getCenturyOfEra() - 1) * 100) + yearInt);
            birthDate = new LocalDate(yearCandidate, monthInt, dayInt);
            if (plus) {
                birthDate = birthDate.minusYears(100);
            }
        }
        return birthDate;
    }

    private String toString(boolean useEra, boolean useSign) {
        int yearOfCentury = _pnrDate.getYearOfCentury();
        int monthOfYear = _pnrDate.getMonthOfYear();
        int dayOfMonth = _isSamordningsnummer ?
                (_pnrDate.getDayOfMonth() + SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE)
                : _pnrDate.getDayOfMonth();
        StringBuilder sb = new StringBuilder();
        sb.append((!useEra && yearOfCentury < 10) ? "0" : "");
        sb.append(useEra ? _pnrDate.getYear() : yearOfCentury);
        sb.append(monthOfYear < 10 ? "0" : "");
        sb.append(monthOfYear);
        sb.append(dayOfMonth < 10 ? "0" : "");
        sb.append(dayOfMonth);
        if (useSign) {
            sb.append(useEra ? "-" : (this.isHundredYears() ? "+" : "-"));
        }
        sb.append(_lastDigits[0]).append(_lastDigits[1]).append(_lastDigits[2]).append(_lastDigits[3]);
        return sb.toString();
    }

    /**
     * Parse Personnummer from a candidate given as argument
     *
     * @param pnrCandidate Personnummer candidate as a string.
     * @return an Optional<Personnummer> containing a Swedish Personnummer, or empty if not valid.
     */
    public static Optional<Personnummer> parse(String pnrCandidate) {
        return Personnummer.parse(pnrCandidate, false);
    }

    /**
     * Parse Personnummer from a candidate given as argument
     *
     * @param pnrCandidate Personnummer candidate as a string.<br/>
     *                     <p>
     *                     When using the forgiving flag (set to true), the parse
     *                     algorithm ignores bad- or<br />
     *                     missing checksum digit and auto correcting it by
     *                     re-calculating it.
     * @param isForgiving  if set to true it auto corrects the checksum digit when
     *                     parsing.
     * @return Personnummer obj representing a Personnummer, or null when fail (due
     * to bad input values<br />
     * as wrong checksum (unless forgiving flag is set to true)
     */
    public static Optional<Personnummer> parse(String pnrCandidate, boolean isForgiving) {
        if (pnrCandidate == null || pnrCandidate.trim().length() < 9) {
            return Optional.empty();
        }

        final Matcher matcher = PRE_COMPILED_PNR_PATTERN.matcher(pnrCandidate.trim());
        if (matcher.find()) {
            String era = matcher.group("era");
            String year = matcher.group("year");
            String month = matcher.group("month");
            String day = matcher.group("day");
            String sign = matcher.group("sign");
            String last3 = matcher.group("last3");
            String checksum = matcher.group("checksum");
            String calculatedChecksum = calculateChecksum(year, month, day, last3);
            boolean plus = "+".equals(sign);
            if (era != null && plus) {
                return Optional.empty(); // Personnummer cannot have both era and plus sign
            }
            if (isForgiving) {
                checksum = calculatedChecksum;
            } else if (checksum == null || !checksum.equals(calculatedChecksum)) {
                return Optional.empty();
            }
            if (!isForgiving && "0000".equals(last3 + checksum)) {
                return Optional.empty();
            }
            int yearInt = parseInt(year);
            int monthInt = parseInt(month);
            int dayInt = parseInt(day);
            boolean isSam = (dayInt >= SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE);
            dayInt = isSam ? (dayInt - SAMORDNINGSNUMMER_OFFSET_FOR_DAY_IN_DATE) : dayInt;
            LocalDate pnrDate = null;
            try {
                pnrDate = resolveBirthDate(era, yearInt, monthInt, dayInt, plus);
            } catch (IllegalFieldValueException e) {
                return Optional.empty(); // Occurs when trying to parse February 29 and its no leap year!
            }
            if (!isForgiving && (pnrDate.centuryOfEra().get() < MIN_CENTURY || pnrDate.isAfter(LocalDate.now()))) {
                return Optional.empty(); // Personnummer is in future, not valid
            }
            return Optional.of(new Personnummer(pnrDate, (last3 + checksum), isForgiving, isSam));
        }
        return Optional.empty();
    }

    /**
     * Indicator that the Personnummer have reach an age of a hundred years, or
     * more. When representing a Personnummer, with an age of hundred or above,
     * using a length without era (11 chars), a plus sign (+) is introduced, instead
     * of minus (-) is used to indicate when the age is hundred years or more.
     *
     * @return true if the Personnummer have reach an age of a hundred years, or
     * more.
     */
    public boolean isHundredYears() {
        return _pnrDate.isBefore(LocalDate.now().minus(Years.years(100)));
    }

    public boolean isForgiving() {
        return _isForgiving;
    }

    /**
     * Get age for a Personnummer.
     * <p>
     * Calculation is based on comparison of birth date and present date.
     *
     * @return age age now (birth date compared to todays date)
     */
    public int getAgeNow() {
        return Years.yearsBetween(_pnrDate, LocalDate.now()).getYears();
    }

    /**
     * Gets the date of birth for Personnummer as a LocalDate obj
     *
     * @return the date of birth for the Personnummer as a LocalDate obj
     */
    public LocalDate getBirthDate() {
        return _pnrDate;
    }

    /**
     * Gets the valid checksum digit (the very last digit of the Personnummer
     * sequence).
     * <p>
     * The checksum is calculated with the so called "modulus-10" algorithm and is
     * done automatically within the parse metods of this class. If checksum was
     * invalid, it will not parse. When using the forgiving flag with a parse
     * method, it will automatically correct a wrong checksum by replacing it with
     * the calculated one, making the it a fully valid Personnummer.
     * <p>
     * More reading (swedish tax agency):
     * http://www.skatteverket.se/privat/sjalvservice/blanketterbroschyrer/
     * broschyrer/info/704.4.39f16f103821c58f680007993.html?q=Personnummer
     *
     * @return checksum digit (last digit) of the Personnummer.
     */
    public int getChecksum() {
        return _lastDigits[3];
    }

    /**
     * Method to count number of days since birth until now (today).
     *
     * @return number of days from birth until today as an integer
     */
    public int getDaysSinceBirth() {
        return Days.daysBetween(_pnrDate, LocalDate.now()).getDays();
    }

    /**
     * Given two strings as arguments (representing woman and man), this method
     * returns the one that represents the gender of this instance.
     *
     * @param woman a string representing a woman
     * @param man   a string representing a man
     * @return returns one of the two strings, based on the gender of this instance
     */
    public String getGender(String woman, String man) {
        return (_lastDigits[2] % 2 == 0) ? woman : man;
    }

    /**
     * Gets the gender of the Personnummer. The digit just before the last one in a
     * Personnummer is either even or odd, and corresponds to woman or man.
     *
     * @return Gender
     */
    public GenderType getGender() {
        return (_lastDigits[2] % 2 == 0) ? GenderType.FEMALE : GenderType.MALE;
    }

    /**
     * Gets the last four digits from the Personnummer
     *
     * @return a string with the last four digits of the Personnummer
     */
    public String getLastFour() {
        return new StringBuilder().append(_lastDigits[0]).append(_lastDigits[1]).append(_lastDigits[2])
                .append(_lastDigits[3]).toString();
    }

    /**
     * Hash code is based upon the Personnummer birth date and the four last digits,
     * using its valid checksum.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(_lastDigits);
        result = prime * result + ((_pnrDate == null) ? 0 : _pnrDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Personnummer other = (Personnummer) obj;
        if (!this.toString13().equals(other.toString13())) {
            return false;
        }
        return true;
    }

    public int compareTo(Personnummer pnr) {
        if (_pnrDate.isBefore(pnr.getBirthDate())) {
            return (-1);
        } else if (_pnrDate.isAfter(pnr.getBirthDate())) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        // Default is same as toString11
        toString11 = ofNullable(toString11).orElseGet(() -> toString(false, true));
        return toString11;
    }

    public String toString10() {
        toString10 = ofNullable(toString10).orElseGet(() -> toString(false, false));
        return toString10;
    }

    public String toString11() {
        toString11 = ofNullable(toString11).orElseGet(() -> toString(false, true));
        return toString11;
    }

    public String toString12() {
        toString12 = ofNullable(toString12).orElseGet(() -> toString(true, false));
        return toString12;
    }

    public String toString13() {
        toString13 = ofNullable(toString13).orElseGet(() -> toString(true, true));
        return toString13;
    }

    @Override
    public IDType getIDType() {
        return _isSamordningsnummer ? IDType.SAMORDNINGSNUMMER : IDType.PERSONNUMMER;
    }
}