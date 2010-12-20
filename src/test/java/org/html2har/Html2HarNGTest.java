package org.html2har;

import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author romain
 */
public class Html2HarNGTest {

    @Test
    public void testToHar() {
        String har = new Html2Har().toHar("http://google.fr");

        //check
        Assert.assertTrue(har.contains("google"));
    }
}
