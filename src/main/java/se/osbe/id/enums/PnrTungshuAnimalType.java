package se.osbe.id.enums;


import org.joda.time.DateTime;
import se.osbe.id.helper.PersonnummerHelper;

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
        return PersonnummerHelper.getTypeForYear(date);
    }
}