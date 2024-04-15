package se.osbe.id;

import org.junit.Assert;
import org.junit.Test;
import se.osbe.id.enums.IDType;

import java.util.List;

import static org.junit.Assert.*;

public class OrganisationsnummerBasicTest {

    @Test
    public void testOrganisationsnummerOK() {
        String orgnrStr1 = "889201-6943"; // Förening
        String orgnrStr2 = "556297-3320"; // Biltema AB
        String orgnrStr3 = "121212-1212"; // Enskild firma

        Organisationsnummer orgnr2 = Organisationsnummer.parse(orgnrStr2).get();
        Organisationsnummer orgnr1 = Organisationsnummer.parse(orgnrStr1).get();
        Organisationsnummer orgnr3 = Organisationsnummer.parse(orgnrStr3).get();

        Assert.assertFalse(orgnr1.isForgiving());
        Assert.assertTrue(orgnr1.isJuridiskPerson());
        Assert.assertFalse(orgnr1.toPersonnummer().isPresent());
        Assert.assertSame(IDType.ORGANISATIONSNUMMER, orgnr1.getIDType());
        Assert.assertEquals("3", String.valueOf(orgnr1.getChecksum()));
        Assert.assertEquals("889201-6943", orgnr1.toString());
        Assert.assertEquals("8892016943", orgnr1.toString10());
        Assert.assertEquals("889201-6943", orgnr1.toString11());
        Assert.assertEquals("168892016943", orgnr1.toString12());
        Assert.assertEquals("16889201-6943", orgnr1.toString13());

        Assert.assertFalse(orgnr2.isForgiving());
        Assert.assertTrue(orgnr2.isJuridiskPerson());
        Assert.assertFalse(orgnr2.toPersonnummer().isPresent());
        Assert.assertSame(IDType.ORGANISATIONSNUMMER, orgnr2.getIDType());
        Assert.assertEquals("0", String.valueOf(orgnr2.getChecksum()));
        Assert.assertEquals("556297-3320", orgnr2.toString());
        Assert.assertEquals("5562973320", orgnr2.toString10());
        Assert.assertEquals("556297-3320", orgnr2.toString11());
        Assert.assertEquals("165562973320", orgnr2.toString12());
        Assert.assertEquals("16556297-3320", orgnr2.toString13());

        Assert.assertFalse(orgnr3.isForgiving());
        Assert.assertFalse(orgnr3.isJuridiskPerson());
        Assert.assertTrue(orgnr3.toPersonnummer().isPresent());
        Assert.assertSame(IDType.ORGANISATIONSNUMMER, orgnr3.getIDType());
        Assert.assertEquals("2", String.valueOf(orgnr3.getChecksum()));
        Assert.assertEquals(String.valueOf(orgnr3.getChecksum()), String.valueOf(orgnr3.toPersonnummer().get().getChecksum()));
        Assert.assertEquals("121212-1212", orgnr3.toString());
        Assert.assertEquals("1212121212", orgnr3.toString10());
        Assert.assertEquals("121212-1212", orgnr3.toString11());
        Assert.assertEquals("161212121212", orgnr3.toString12());
        Assert.assertEquals("16121212-1212", orgnr3.toString13());
    }

    @Test
    public void testSamordningsnummerNOK() {
        //      assertFalse(Organisationsnummer.parse("121212-1212").isPresent());
        //      assertFalse(Organisationsnummer.parse("551212-1212").isPresent());
    }

    @Test
    public void testSamordningsnummerToStringOK() {
        ;
    }
}