package matcher.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * There is more to the model object than vanilla getters/setters. Need to test.
 */
public class ProductTest {

    @Test
    public void testManuSetter() {
        // lowercase and with spaces
        String manu = "abc123   ";
        Product p = new Product();
        p.setManufacturer(manu);

        Assert.assertEquals("ABC123", p.getManufacturer());
    }

}
