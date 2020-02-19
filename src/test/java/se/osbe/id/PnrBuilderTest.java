package se.osbe.id;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import se.osbe.id.exception.PersonnummerException;
import se.osbe.id.helper.PersonnummerBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PnrBuilderTest {

    @Test
    public void testSsnBuilderWithAgeNull() {
        List<Personnummer> ssnList = null;
        try {
            ssnList = new PersonnummerBuilder().setAge(null).build();
        } catch (PersonnummerException e) {
            Assert.assertTrue(e instanceof PersonnummerException);
            Assert.assertNull(ssnList);
        }
    }

    @Test
    public void testSsnBuilder100WithAgeZeroTo101() {
        List<Personnummer> ssnList = IntStream.rangeClosed(0, 100).mapToObj(a -> {
            try {
                return new PersonnummerBuilder().setAge(a).setUpperLimitForGenerate(100).build();
            } catch (PersonnummerException e) {
                e.printStackTrace();
                Assert.fail();
            }
            return new ArrayList<Personnummer>();
        })
                .flatMap(p -> p.stream())
                .collect(Collectors.toList());
        Assert.assertNotNull(ssnList);
    }

    @Test
    public void testSsnBuilderWithNotNullDate() {
        List<Personnummer> ssnList = null;
        try {
            ssnList = new PersonnummerBuilder().setStartAndEndDate(LocalDate.now().minusDays(1), LocalDate.now()).build();
        } catch (PersonnummerException e) {
            Assert.fail();
        }
        Assert.assertNotNull(ssnList);
    }

    @Test
    public void testSsnBuilderWithNullAsStartDate() {
        try {
            new PersonnummerBuilder().setStartAndEndDate(null, LocalDate.now());
        } catch (PersonnummerException e) {
            Assert.assertNotNull(e);
            return;
        }
        Assert.fail();
    }

    @Test
    public void testSsnBuilderWithNullAsEndDate() {
        try {
            new PersonnummerBuilder().setStartAndEndDate(LocalDate.now(), null);
        } catch (PersonnummerException e) {
            Assert.assertNotNull(e);
            return;
        }
        Assert.fail();
    }

    @Test
    public void testSsnBuilderWithFutureDate() {
        try {
            new PersonnummerBuilder().setStartAndEndDate(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        } catch (PersonnummerException e) {
            Assert.assertNotNull(e);
            return;
        }
        Assert.fail();
    }

    @Test
    public void testSsnBuilderWithLimit() {
        List<Personnummer> ssnList = null;
        try {
            ssnList = new PersonnummerBuilder().setAge(10).setUpperLimitForGenerate(123).build();
            Assert.assertTrue(ssnList.size() > 0);
            Assert.assertTrue(ssnList.size() == 123);
            Assert.assertTrue(ssnList.get(0).getAgeNow() == 10);
        } catch (PersonnummerException e) {
            Assert.assertNull(e);
        }
    }
}
