package se.osbe.id;

import org.junit.Assert;
import org.junit.Test;
import se.osbe.id.enums.IDType;

import java.util.Arrays;
import java.util.List;

public class IdentifiableTest {

    @Test
    public void idTypeTest() {
        Identifiable id1 = (Identifiable) (Personnummer.parse("1212121212").get());
        Identifiable id2 = (Identifiable) (Personnummer.parse("1212721212", true).get());
        Identifiable id3 = (Identifiable) (Organisationsnummer.parse("556391-0354", true).get());

        Assert.assertEquals(IDType.PERSONNUMMER,        id1.getIDType());
        Assert.assertEquals(IDType.SAMORDNINGSNUMMER,   id2.getIDType());
        Assert.assertEquals(IDType.ORGANISATIONSNUMMER, id3.getIDType());
    }

}
