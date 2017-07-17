package matcher.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import matcher.model.PriceListing;

/**
 * List of price listings, in JSON, is in a file located at src/test/resources/listings.txt
 */
public class PriceListingDaoFile implements PriceListingDao {
    private List<PriceListing> entries = new ArrayList<>();

    @Override
    /**
     */
    public List<PriceListing> buildList() {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        InputStream is = null;
        BufferedReader br = null;
        try {
            is = classLoader.getResourceAsStream("listings.txt");
            br = new BufferedReader(new InputStreamReader(is));

            ObjectMapper mapper = new ObjectMapper();

            String jsonLine = null;
            while ((jsonLine = br.readLine()) != null) {
                // JSON from String to Object
                PriceListing listingEntry = mapper.readValue(jsonLine, PriceListing.class);

                entries.add(listingEntry);
            }

        } catch (Exception e) {
            throw new DaoRuntimeException(e);

        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
            }
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
        }

        return entries;
    }

}
