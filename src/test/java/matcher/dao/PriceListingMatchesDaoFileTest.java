package matcher.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import matcher.model.PriceListing;
import matcher.model.PriceListingMatches;

public class PriceListingMatchesDaoFileTest {
    private PriceListingMatchesDao priceListingMatchesDao = new PriceListingMatchesDaoFile();

    @Test
    public void testSaveAll_emptyList() throws Exception {
        List<PriceListingMatches> matches = new ArrayList<>();

        priceListingMatchesDao.saveAll(matches);

        InputStream is = new FileInputStream(new File(PriceListingMatchesDaoFile.OUTPUT_FILENAME));
        Assert.assertNotNull(is);

        // The file should be empty
        Assert.assertEquals(0, is.available());
    }

    @Test
    public void testSaveAll() throws Exception {
        // Build a data structure as if the matcher did
        List<PriceListingMatches> matches = new ArrayList<>();

        PriceListingMatches plm1 = new PriceListingMatches("manu1");
        List<PriceListing> pl1 = new ArrayList<>();
        pl1.add(new PriceListing("pl1a-title", "manu1", 1.00f, null));
        pl1.add(new PriceListing("pl1b-title", "manu1", 2.00f, null));
        pl1.add(new PriceListing("pl1c-title", "manu1", 3.00f, null));
        plm1.addAllListings(pl1);
        matches.add(plm1);

        PriceListingMatches plm2 = new PriceListingMatches("manu2");
        List<PriceListing> pl2 = new ArrayList<>();
        pl2.add(new PriceListing("pl2a-title", "manu2", 1.00f, null));
        plm2.addAllListings(pl2);
        matches.add(plm2);

        priceListingMatchesDao.saveAll(matches);

        BufferedReader bis = new BufferedReader(new InputStreamReader(new FileInputStream(PriceListingMatchesDaoFile.OUTPUT_FILENAME)));
        Assert.assertNotNull(bis);

        // only 2 lines, manually go thru them
        String line1 = bis.readLine();

        // For sake of ease for assert make all double-quotes be single
        String line1Escaped = line1.replaceAll("\"", "\'");
        Assert.assertEquals(
                "{'product_name':'manu1','listings':[{'title':'pl1a-title','manufacturer':'MANU1','currency':null,'price':'1.00'},{'title':'pl1b-title','manufacturer':'MANU1','currency':null,'price':'2.00'},{'title':'pl1c-title','manufacturer':'MANU1','currency':null,'price':'3.00'}]}",
                line1Escaped);

        String line2 = bis.readLine();
        String line2Escaped = line2.replaceAll("\"", "\'");
        Assert.assertEquals(
                "{'product_name':'manu2','listings':[{'title':'pl2a-title','manufacturer':'MANU2','currency':null,'price':'1.00'}]}",
                line2Escaped);
    }
}
