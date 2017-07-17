package matcher.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * There is more to the model object than vanilla getters/setters. Need to test.
 */
public class PriceListingTest {

    @Test
    public void testManuSetter() {
        // lowercase and with spaces
        String manu = "abc123   ";
        PriceListing pl = new PriceListing();
        pl.setManufacturer(manu);

        Assert.assertEquals("ABC123", pl.getManufacturer());
    }

}
