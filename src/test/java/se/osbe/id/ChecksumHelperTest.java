package se.osbe.id;

import org.junit.Assert;
import org.junit.Test;
import se.osbe.id.helper.ChecksumHelper;

public class ChecksumHelperTest {
    @Test
    public void testCalculateChecksumFromString() {
        String result = ChecksumHelper.calculateChecksum("1212121212");
        Assert.assertEquals("2", result);
    }

    @Test
    public void testCalculateChecksumFromCharArray() {
        String result = ChecksumHelper.calculateChecksum("1212121212".toCharArray());
        Assert.assertEquals("2", result);
    }
}
