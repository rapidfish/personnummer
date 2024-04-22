package se.osbe.id;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static se.osbe.id.enums.PnrTungshuAnimalType.*;

public class PnrTungshuAnimalTypeTest {

    // Positive tests
    @Test
    public void testZodiacSignCapricornusRat() {
        assertEquals("Rat", RAT.getAnimalName());
        assertEquals("Råttan", RAT.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignCapricornusOx() {
        assertEquals("Ox", OX.getAnimalName());
        assertEquals("Oxen", OX.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignCapricornusTiger() {
        assertEquals("Tiger", TIGER.getAnimalName());
        assertEquals("Tigern", TIGER.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignCapricornusRabbit() {
        assertEquals("Rabbit", RABBIT.getAnimalName());
        assertEquals("Kaninen", RABBIT.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignCapricornusDragon() {
        assertEquals("Dragon", DRAGON.getAnimalName());
        assertEquals("Draken", DRAGON.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignCapricornusSnake() {
        assertEquals("Snake", SNAKE.getAnimalName());
        assertEquals("Ormen", SNAKE.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignCapricornusHorse() {
        assertEquals("Horse", HORSE.getAnimalName());
        assertEquals("Hästen", HORSE.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignCapricornusGoat() {
        assertEquals("Goat", GOAT.getAnimalName());
        assertEquals("Geten", GOAT.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignCapricornusMonkey() {
        assertEquals("Monkey", MONKEY.getAnimalName());
        assertEquals("Apan", MONKEY.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignCapricornusRooster() {
        assertEquals("Rooster", ROOSTER.getAnimalName());
        assertEquals("Tuppen", ROOSTER.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignCapricornusDog() {
        assertEquals("Dog", DOG.getAnimalName());
        assertEquals("Hunden", DOG.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignCapricornusPig() {
        assertEquals("Pig", PIG.getAnimalName());
        assertEquals("Grisen", PIG.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignCapricornusLogicRAT() {
        assertEquals(RAT, getTypeForYear(LocalDate.parse("2020-01-01")));
        assertEquals(RAT, getTypeForYear(LocalDate.parse("2008-01-01")));
        assertEquals(RAT, getTypeForYear(LocalDate.parse("1996-01-01")));
        assertEquals(RAT, getTypeForYear(LocalDate.parse("1984-01-01")));
    }

    @Test
    public void testZodiacSignCapricornusLogicOX() {
        assertEquals(OX, getTypeForYear(LocalDate.parse("2021-01-01")));
        assertEquals(OX, getTypeForYear(LocalDate.parse("2009-01-01")));
        assertEquals(OX, getTypeForYear(LocalDate.parse("1997-01-01")));
        assertEquals(OX, getTypeForYear(LocalDate.parse("1985-01-01")));
    }

    @Test
    public void testZodiacSignCapricornusLogicTIGER() {
        assertEquals(TIGER, getTypeForYear(LocalDate.parse("2022-01-01")));
        assertEquals(TIGER, getTypeForYear(LocalDate.parse("2010-01-01")));
        assertEquals(TIGER, getTypeForYear(LocalDate.parse("1998-01-01")));
        assertEquals(TIGER, getTypeForYear(LocalDate.parse("1986-01-01")));
    }

    @Test
    public void testZodiacSignCapricornusLogicRABBIT() {
        assertEquals(RABBIT, getTypeForYear(LocalDate.parse("2023-01-01")));
        assertEquals(RABBIT, getTypeForYear(LocalDate.parse("2011-01-01")));
        assertEquals(RABBIT, getTypeForYear(LocalDate.parse("1999-01-01")));
        assertEquals(RABBIT, getTypeForYear(LocalDate.parse("1987-01-01")));
    }

    @Test
    public void testZodiacSignCapricornusLogicDRAGON() {
        assertEquals(DRAGON, getTypeForYear(LocalDate.parse("2024-01-01")));
        assertEquals(DRAGON, getTypeForYear(LocalDate.parse("2012-01-01")));
        assertEquals(DRAGON, getTypeForYear(LocalDate.parse("2000-01-01")));
        assertEquals(DRAGON, getTypeForYear(LocalDate.parse("1988-01-01")));
    }

    @Test
    public void testZodiacSignCapricornusLogicSNAKE() {
        assertEquals(SNAKE, getTypeForYear(LocalDate.parse("2025-01-01")));
        assertEquals(SNAKE, getTypeForYear(LocalDate.parse("2013-01-01")));
        assertEquals(SNAKE, getTypeForYear(LocalDate.parse("2001-01-01")));
        assertEquals(SNAKE, getTypeForYear(LocalDate.parse("1989-01-01")));
    }

    @Test
    public void testZodiacSignCapricornusLogicHORSE() {
        assertEquals(HORSE, getTypeForYear(LocalDate.parse("2026-01-01")));
        assertEquals(HORSE, getTypeForYear(LocalDate.parse("2014-01-01")));
        assertEquals(HORSE, getTypeForYear(LocalDate.parse("2002-01-01")));
        assertEquals(HORSE, getTypeForYear(LocalDate.parse("1990-01-01")));
    }

    @Test
    public void testZodiacSignCapricornusLogicGOAT() {
        assertEquals(GOAT, getTypeForYear(LocalDate.parse("2027-01-01")));
        assertEquals(GOAT, getTypeForYear(LocalDate.parse("2015-01-01")));
        assertEquals(GOAT, getTypeForYear(LocalDate.parse("2003-01-01")));
        assertEquals(GOAT, getTypeForYear(LocalDate.parse("1991-01-01")));
    }

    @Test
    public void testZodiacSignCapricornusLogicMONKEY() {
        assertEquals(MONKEY, getTypeForYear(LocalDate.parse("2028-01-01")));
        assertEquals(MONKEY, getTypeForYear(LocalDate.parse("2016-01-01")));
        assertEquals(MONKEY, getTypeForYear(LocalDate.parse("2004-01-01")));
        assertEquals(MONKEY, getTypeForYear(LocalDate.parse("1992-01-01")));
    }

    @Test
    public void testZodiacSignCapricornusLogicROOSTER() {
        assertEquals(ROOSTER, getTypeForYear(LocalDate.parse("2029-01-01")));
        assertEquals(ROOSTER, getTypeForYear(LocalDate.parse("2017-01-01")));
        assertEquals(ROOSTER, getTypeForYear(LocalDate.parse("2005-01-01")));
        assertEquals(ROOSTER, getTypeForYear(LocalDate.parse("1993-01-01")));
        assertEquals(ROOSTER, getTypeForYear(LocalDate.parse("1981-01-01")));
    }

    @Test
    public void testZodiacSignCapricornusLogicDOG() {
        assertEquals(DOG, getTypeForYear(LocalDate.parse("2030-01-01")));
        assertEquals(DOG, getTypeForYear(LocalDate.parse("2018-01-01")));
        assertEquals(DOG, getTypeForYear(LocalDate.parse("2006-01-01")));
        assertEquals(DOG, getTypeForYear(LocalDate.parse("1994-01-01")));
        assertEquals(DOG, getTypeForYear(LocalDate.parse("1982-01-01")));
    }

    @Test
    public void testZodiacSignCapricornusLogicPIG() {
        assertEquals(PIG, getTypeForYear(LocalDate.parse("2031-01-01")));
        assertEquals(PIG, getTypeForYear(LocalDate.parse("2019-01-01")));
        assertEquals(PIG, getTypeForYear(LocalDate.parse("2007-01-01")));
        assertEquals(PIG, getTypeForYear(LocalDate.parse("1995-01-01")));
        assertEquals(PIG, getTypeForYear(LocalDate.parse("1983-01-01")));
    }

    @Test
    public void testZodiacSignCapricornusLogicExtremeDates() {
        assertEquals(HORSE, getTypeForYear(LocalDate.parse("1066-01-01"))); // Hastings
        assertEquals(HORSE, getTypeForYear(LocalDate.parse("1666-01-01"))); // Mirabilis
        assertEquals(SNAKE, getTypeForYear(LocalDate.parse("2001-01-01"))); // Twin Tower
        assertEquals(MONKEY, getTypeForYear(LocalDate.parse("3048-01-01"))); // Future
        assertEquals(PIG, getTypeForYear(LocalDate.parse("9999-01-01"))); //
    }

    // Negative tests
    @Test(expected = NullPointerException.class)
    public void testZodiacSignCapricornusLogic_NOK() {
        getTypeForYear(null);
    }

    @Test
    public void testZodiacSignCapricornusLogicRAT_NOK() {
        assertNotEquals(RAT, getTypeForYear(LocalDate.parse("2021-01-01")));
        assertNotEquals(RAT, getTypeForYear(LocalDate.parse("2009-01-01")));
        assertNotEquals(RAT, getTypeForYear(LocalDate.parse("1997-01-01")));
        assertNotEquals(RAT, getTypeForYear(LocalDate.parse("1985-01-01")));
    }
}
