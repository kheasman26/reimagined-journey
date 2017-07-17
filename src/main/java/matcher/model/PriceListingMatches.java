package matcher.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceListingMatches {

    private String productName;
    private List<PriceListing> listings = new ArrayList<>();

    public PriceListingMatches(final String productName) {
        this.productName = productName;
    }

    @JsonProperty("product_name")
    public String getProductName() {
        return productName;
    }

    @JsonProperty("listings")
    public List<PriceListing> getListings() {
        return listings;
    }

    /**
     * Adds the PriceListing to a tracked list of listings. Also sets the matched boolean.
     * 
     * @param priceListing
     */
    public void addListing(PriceListing priceListing) {
        priceListing.setMatched(true);

        listings.add(priceListing);
    }

    public void addAllListings(List<PriceListing> priceListings) {
        priceListings.forEach(pl -> addListing(pl));
    }
}
