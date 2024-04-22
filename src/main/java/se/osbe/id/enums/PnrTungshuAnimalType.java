package se.osbe.id.enums;

import se.osbe.id.helper.PersonnummerHelper;

import java.time.LocalDate;

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

    public static PnrTungshuAnimalType getTypeForYear(LocalDate date) {
        return PersonnummerHelper.getTypeForYear(date);
    }
}