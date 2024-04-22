package se.osbe.id;

import org.junit.Assert;
import org.junit.Test;

import static se.osbe.id.helper.ChecksumHelper.calculateChecksum;

public class ChecksumHelperTest {
    @Test
    public void testCalculateChecksumFromString() {
        String result = calculateChecksum("1212121212");
        Assert.assertEquals("2", result);
    }

    @Test
    public void testCalculateChecksumFromCharArray() {
        String result = calculateChecksum("1212121212".toCharArray());
        Assert.assertEquals("2", result);
    }
}
