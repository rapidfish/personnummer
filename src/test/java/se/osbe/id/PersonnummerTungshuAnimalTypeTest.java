package se.osbe.id;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static se.osbe.id.enums.PnrTungshuAnimalType.*;

public class PersonnummerTungshuAnimalTypeTest {

    // Positive tests
    @Test
    public void testZodiacSignTungshuAnimalRat() {
        assertEquals("Rat", RAT.getAnimalName());
        assertEquals("Råttan", RAT.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignTungshuAnimalOx() {
        assertEquals("Ox", OX.getAnimalName());
        assertEquals("Oxen", OX.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignTungshuAnimalTiger() {
        assertEquals("Tiger", TIGER.getAnimalName());
        assertEquals("Tigern", TIGER.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignTungshuAnimalRabbit() {
        assertEquals("Rabbit", RABBIT.getAnimalName());
        assertEquals("Kaninen", RABBIT.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignTungshuAnimalDragon() {
        assertEquals("Dragon", DRAGON.getAnimalName());
        assertEquals("Draken", DRAGON.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignTungshuAnimalSnake() {
        assertEquals("Snake", SNAKE.getAnimalName());
        assertEquals("Ormen", SNAKE.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignTungshuAnimalHorse() {
        assertEquals("Horse", HORSE.getAnimalName());
        assertEquals("Hästen", HORSE.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignTungshuAnimalGoat() {
        assertEquals("Goat", GOAT.getAnimalName());
        assertEquals("Geten", GOAT.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignTungshuAnimalMonkey() {
        assertEquals("Monkey", MONKEY.getAnimalName());
        assertEquals("Apan", MONKEY.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignTungshuAnimalRooster() {
        assertEquals("Rooster", ROOSTER.getAnimalName());
        assertEquals("Tuppen", ROOSTER.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignTungshuAnimalDog() {
        assertEquals("Dog", DOG.getAnimalName());
        assertEquals("Hunden", DOG.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignTungshuAnimalPig() {
        assertEquals("Pig", PIG.getAnimalName());
        assertEquals("Grisen", PIG.getAnimalNameSwe());
    }

    @Test
    public void testZodiacSignTungshuAnimalLogicRAT() {
        assertEquals(RAT, getTypeForYear(LocalDate.parse("2020-01-01")));
        assertEquals(RAT, getTypeForYear(LocalDate.parse("2008-01-01")));
        assertEquals(RAT, getTypeForYear(LocalDate.parse("1996-01-01")));
        assertEquals(RAT, getTypeForYear(LocalDate.parse("1984-01-01")));
    }

    @Test
    public void testZodiacSignTungshuAnimalLogicOX() {
        assertEquals(OX, getTypeForYear(LocalDate.parse("2021-01-01")));
        assertEquals(OX, getTypeForYear(LocalDate.parse("2009-01-01")));
        assertEquals(OX, getTypeForYear(LocalDate.parse("1997-01-01")));
        assertEquals(OX, getTypeForYear(LocalDate.parse("1985-01-01")));
    }

    @Test
    public void testZodiacSignTungshuAnimalLogicTIGER() {
        assertEquals(TIGER, getTypeForYear(LocalDate.parse("2022-01-01")));
        assertEquals(TIGER, getTypeForYear(LocalDate.parse("2010-01-01")));
        assertEquals(TIGER, getTypeForYear(LocalDate.parse("1998-01-01")));
        assertEquals(TIGER, getTypeForYear(LocalDate.parse("1986-01-01")));
    }

    @Test
    public void testZodiacSignTungshuAnimalLogicRABBIT() {
        assertEquals(RABBIT, getTypeForYear(LocalDate.parse("2023-01-01")));
        assertEquals(RABBIT, getTypeForYear(LocalDate.parse("2011-01-01")));
        assertEquals(RABBIT, getTypeForYear(LocalDate.parse("1999-01-01")));
        assertEquals(RABBIT, getTypeForYear(LocalDate.parse("1987-01-01")));
    }

    @Test
    public void testZodiacSignTungshuAnimalLogicDRAGON() {
        assertEquals(DRAGON, getTypeForYear(LocalDate.parse("2024-01-01")));
        assertEquals(DRAGON, getTypeForYear(LocalDate.parse("2012-01-01")));
        assertEquals(DRAGON, getTypeForYear(LocalDate.parse("2000-01-01")));
        assertEquals(DRAGON, getTypeForYear(LocalDate.parse("1988-01-01")));
    }

    @Test
    public void testZodiacSignTungshuAnimalLogicSNAKE() {
        assertEquals(SNAKE, getTypeForYear(LocalDate.parse("2025-01-01")));
        assertEquals(SNAKE, getTypeForYear(LocalDate.parse("2013-01-01")));
        assertEquals(SNAKE, getTypeForYear(LocalDate.parse("2001-01-01")));
        assertEquals(SNAKE, getTypeForYear(LocalDate.parse("1989-01-01")));
    }

    @Test
    public void testZodiacSignTungshuAnimalLogicHORSE() {
        assertEquals(HORSE, getTypeForYear(LocalDate.parse("2026-01-01")));
        assertEquals(HORSE, getTypeForYear(LocalDate.parse("2014-01-01")));
        assertEquals(HORSE, getTypeForYear(LocalDate.parse("2002-01-01")));
        assertEquals(HORSE, getTypeForYear(LocalDate.parse("1990-01-01")));
    }

    @Test
    public void testZodiacSignTungshuAnimalLogicGOAT() {
        assertEquals(GOAT, getTypeForYear(LocalDate.parse("2027-01-01")));
        assertEquals(GOAT, getTypeForYear(LocalDate.parse("2015-01-01")));
        assertEquals(GOAT, getTypeForYear(LocalDate.parse("2003-01-01")));
        assertEquals(GOAT, getTypeForYear(LocalDate.parse("1991-01-01")));
    }

    @Test
    public void testZodiacSignTungshuAnimalLogicMONKEY() {
        assertEquals(MONKEY, getTypeForYear(LocalDate.parse("2028-01-01")));
        assertEquals(MONKEY, getTypeForYear(LocalDate.parse("2016-01-01")));
        assertEquals(MONKEY, getTypeForYear(LocalDate.parse("2004-01-01")));
        assertEquals(MONKEY, getTypeForYear(LocalDate.parse("1992-01-01")));
    }

    @Test
    public void testZodiacSignTungshuAnimalLogicROOSTER() {
        assertEquals(ROOSTER, getTypeForYear(LocalDate.parse("2029-01-01")));
        assertEquals(ROOSTER, getTypeForYear(LocalDate.parse("2017-01-01")));
        assertEquals(ROOSTER, getTypeForYear(LocalDate.parse("2005-01-01")));
        assertEquals(ROOSTER, getTypeForYear(LocalDate.parse("1993-01-01")));
        assertEquals(ROOSTER, getTypeForYear(LocalDate.parse("1981-01-01")));
    }

    @Test
    public void testZodiacSignTungshuAnimalLogicDOG() {
        assertEquals(DOG, getTypeForYear(LocalDate.parse("2030-01-01")));
        assertEquals(DOG, getTypeForYear(LocalDate.parse("2018-01-01")));
        assertEquals(DOG, getTypeForYear(LocalDate.parse("2006-01-01")));
        assertEquals(DOG, getTypeForYear(LocalDate.parse("1994-01-01")));
        assertEquals(DOG, getTypeForYear(LocalDate.parse("1982-01-01")));
    }

    @Test
    public void testZodiacSignTungshuAnimalLogicPIG() {
        assertEquals(PIG, getTypeForYear(LocalDate.parse("2031-01-01")));
        assertEquals(PIG, getTypeForYear(LocalDate.parse("2019-01-01")));
        assertEquals(PIG, getTypeForYear(LocalDate.parse("2007-01-01")));
        assertEquals(PIG, getTypeForYear(LocalDate.parse("1995-01-01")));
        assertEquals(PIG, getTypeForYear(LocalDate.parse("1983-01-01")));
    }

    @Test
    public void testZodiacSignTungshuAnimalLogicExtremeDates() {
        assertEquals(HORSE, getTypeForYear(LocalDate.parse("1066-01-01"))); // Hastings
        assertEquals(HORSE, getTypeForYear(LocalDate.parse("1666-01-01"))); // Mirabilis
        assertEquals(SNAKE, getTypeForYear(LocalDate.parse("2001-01-01"))); // Twin Tower
        assertEquals(MONKEY, getTypeForYear(LocalDate.parse("3048-01-01"))); // Future
        assertEquals(PIG, getTypeForYear(LocalDate.parse("9999-01-01"))); //
    }

    // Negative tests
    @Test(expected = NullPointerException.class)
    public void testZodiacSignTungshuAnimalLogic_NOK() {
        getTypeForYear(null);
    }

    @Test
    public void testZodiacSignTungshuAnimalLogicRAT_NOK() {
        assertNotEquals(RAT, getTypeForYear(LocalDate.parse("2021-01-01")));
        assertNotEquals(RAT, getTypeForYear(LocalDate.parse("2009-01-01")));
        assertNotEquals(RAT, getTypeForYear(LocalDate.parse("1997-01-01")));
        assertNotEquals(RAT, getTypeForYear(LocalDate.parse("1985-01-01")));
    }
}
