package se.osbe.id;

import org.joda.time.Days;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import se.osbe.id.enums.GenderType;
import se.osbe.id.enums.IDType;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.lang.Integer.*;
import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;

public class Organisationsnummer implements Id {
    private static final int LAST4DIGITS = 4;
    private static final int MIN_CENTURY = 18;

    private LocalDate _orgDate;
    private int[] _lastDigits = new int[LAST4DIGITS];
    private final boolean _isForgiving;

    // Used for optimizing toString
    private String toString10;
    private String toString11;
    private String toString12;
    private String toString13;

    private Organisationsnummer() {
        /*
         * !!! private constructor !!!
         *
         * Use the static parser methods to instantiate objects of this class...
         *
         * e.g. Personnummer.parse("121212-1212") will get you an Optional<Personnummer>().
         *
         */
        this(null, null, false);
    }

    private Organisationsnummer(LocalDate orgDate, String lastDigits, boolean isForgiving) {
        _orgDate = orgDate;
        IntStream.range(0, lastDigits.length()).forEach(i -> {
            _lastDigits[i] = parseInt("" + lastDigits.charAt(i));
        });
        _isForgiving = isForgiving;
    }

    private static int calculateChecksum(String year, String month, String day, String last3) {
        char[] orgNumbersWithoutChecksum = String.join("", year, month, day, last3).toCharArray();
        boolean even = true;
        int sum = 0;
        for (char c : orgNumbersWithoutChecksum) {
            int partSum = (parseInt(Character.toString(c)) * ((even) ? 2 : 1));
            sum += ((partSum > 9) ? (1 + (partSum % 10)) : partSum);
            even = !even;
        }
        return ((10 - (sum % 10)) % 10);
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
        StringBuilder sb = new StringBuilder();
        int yearOfCentury = _orgDate.getYearOfCentury();
        int monthOfYear = _orgDate.getMonthOfYear();
        int dayOfMonth = _orgDate.getDayOfMonth();
        if (!useEra && yearOfCentury < 10) {
            sb.append("0");
        }
        sb.append(useEra ? _orgDate.getYear() : yearOfCentury);
        if (monthOfYear < 10) {
            sb.append("0");
        }
        sb.append(monthOfYear);
        if (dayOfMonth < 10) {
            sb.append("0");
        }
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
     * @param orgCandidate Personnummer candidate as a string.
     * @return an Optional<Personnummer> containing a Swedish Personnummer, or empty if not valid.
     */
    public static Optional<Personnummer> parse(String orgCandidate) {
        return Personnummer.parse(orgCandidate, false);
    }

    /**
     * Parse Personnummer from a candidate given as argument
     *
     * @param orgCandidate Personnummer candidate as a string.<br/>
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
    public static Optional<Organisationsnummer> parse(String orgCandidate, boolean isForgiving) {
        if (orgCandidate == null || orgCandidate.trim().length() < 9) {
            return Optional.empty();
        }
        final Pattern preCompiledPattern = Pattern.compile(IDType.ORGANISATIONSNUMMER.getPattern());
        Matcher matcher = preCompiledPattern.matcher(orgCandidate.trim());
        if (matcher.find()) {
            String era = matcher.group("era");
            String year = matcher.group("year");
            String month = matcher.group("month");
            String day = matcher.group("day");
            String sign = matcher.group("sign");
            String last3 = matcher.group("last3");
            String checksum = matcher.group("checksum");
            int calculatedChecksum = calculateChecksum(year, month, day, last3);
            boolean plus = "+".equals(sign);
            if (era != null && plus) {
                return Optional.empty(); // Personnummer cannot have both era and plus sign
            }
            if (isForgiving) {
                checksum = String.valueOf(calculatedChecksum);
            } else if (checksum == null || (parseInt(checksum) != calculatedChecksum)) {
                return Optional.empty();
            }
            if (!isForgiving && "0000".equals(last3 + checksum)) {
                return Optional.empty();
            }
            int yearInt = parseInt(year);
            int monthInt = parseInt(month);
            int dayInt = parseInt(day);
            if (monthInt >= 20) {
                return Optional.empty(); // Not a Personnummer
            }
            LocalDate orgDate = null;
            try {
                orgDate = resolveBirthDate(era, yearInt, monthInt, dayInt, plus);
            } catch (IllegalFieldValueException e) {
                return Optional.empty(); // Occurs when trying to parse February 29 and its no leap year!
            }
            if (!isForgiving && (orgDate.centuryOfEra().get() < MIN_CENTURY || orgDate.isAfter(LocalDate.now()))) {
                return Optional.empty(); // Personnummer is in future, not valid
            }
            return Optional.of(new Organisationsnummer(orgDate, (last3 + checksum), isForgiving));
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
        return _orgDate.isBefore(LocalDate.now().minus(Years.years(100)));
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
        return Years.yearsBetween(_orgDate, LocalDate.now()).getYears();
    }

    /**
     * Gets the date of birth for Personnummer as a LocalDate obj
     *
     * @return the date of birth for the Personnummer as a LocalDate obj
     */
    public LocalDate getBirthDate() {
        return _orgDate;
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
        return Days.daysBetween(_orgDate, LocalDate.now()).getDays();
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
        return (_lastDigits[2] % 2 == 0) ? GenderType.WOMAN : GenderType.MAN;
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
        result = prime * result + ((_orgDate == null) ? 0 : _orgDate.hashCode());
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

    public int compareTo(Organisationsnummer org) {
        if (_orgDate.isBefore(org.getBirthDate())) {
            return (-1);
        } else if (_orgDate.isAfter(org.getBirthDate())) {
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
    public IDType getType() {
        return IDType.ORGANISATIONSNUMMER;
    }

}
