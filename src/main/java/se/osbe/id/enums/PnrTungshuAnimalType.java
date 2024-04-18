package se.osbe.id.enums;


import org.joda.time.DateTime;

import static java.util.Objects.requireNonNull;

/**
 * An attempt to model the Tungshu Zodiac lists of animals associated with certain years.
 */
public enum PnrTungshuAnimalType {
    RAT("Rat", "Råttan"),
    OX("Ox", "Oxen"),
    TIGER("Tiger", "Tigern"),
    RABBIT("Rabbit", "Kaninen"),
    DRAGON("Dragon", "Draken"),
    SNAKE("Snake", "Ormen"),
    HORSE("Horse", "Hästen"),
    GOAT("Goat", "Geten"),
    MONKEY("Monkey", "Apan"),
    ROOSTER("Rooster", "Tuppen"),
    DOG("Dog", "Hunden"),
    PIG("Pig", "Grisen"),
    ;

    PnrTungshuAnimalType(String animalName, String animalNameSwe) {
        _animalName = animalName;
        _animalNameSwe = animalNameSwe;
    }

    private final String _animalName;
    private final String _animalNameSwe;


    public String getAnimalName() {
        return _animalName;
    }

    public String getAnimalNameSwe() {
        return _animalNameSwe;
    }

    /**
     * toString returning the latin name of this zodiac sign
     */
    @Override
    public String toString() {
        return this.name();
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
}