package matcher.strategy.pricelistmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import matcher.model.PriceListing;
import matcher.model.PriceListingMatches;
import matcher.model.Product;

/**
 * Product to PriceListing matcher that uses a map for the PriceListings: key = Manufacturer, value = List of
 * PriceListing entries in that Manufacturer
 * <p>
 * Within a 'strategy' package since there are other ways (strategies) that could be used/experimented to perform the
 * match.
 */
public class PriceListMapMatcher {
    private static final Logger logger = LoggerFactory.getLogger(PriceListMapMatcher.class);

    public List<PriceListingMatches> match(List<Product> products, PriceListingStruct priceListingStruct) {

        List<PriceListingMatches> priceListingMatches = new ArrayList<>(products.size());

        // manuToListingsMap is distilled to manus that actually exist in the product list.
        Map<String, List<PriceListing>> manuToListingsMap = priceListingStruct.getManuListingMap();

        // Go thru each product, and regex thru the price listings.
        for (Product product : products) {

            PriceListingMatches plm = new PriceListingMatches(product.getProductName());
            priceListingMatches.add(plm);

            // Retrieve the listing list, which is based on the product manufacturers
            List<PriceListing> priceListingsPerManu = manuToListingsMap.get(product.getManufacturer());

            // There will be no PriceListing in the struct when there were no
            // price listings for a product.
            if (priceListingsPerManu == null)
                continue;

            List<PriceListing> matchedPriceListings = findMatchForProduct(product, priceListingsPerManu);
            plm.addAllListings(matchedPriceListings);

            if (matchedPriceListings.size() == 0) {
                logger.info("N, " + product.getManufacturer() + " - " + product.getModel());
            }
        }

        return priceListingMatches;
    }

    /**
     * Finds matching PriceListings for Product.
     * 
     * @param product
     * @param priceListings
     * @return
     */
    public List<PriceListing> findMatchForProduct(Product product, List<PriceListing> priceListings) {
        // return data struct
        List<PriceListing> matchedPriceListings = new ArrayList<>();

        // For each listing, evaluate the model in the price list
        for (PriceListing priceListing : priceListings) {

            boolean isMatch = doesListingMatch(product, priceListing);

            if (isMatch) {
                String logString = "{" + product.getManufacturer() + " | " + product.getModel() + " | " + product.getFamily() + "} - {"
                        + priceListing.getTitle() + " | " + priceListing.getPrice() + "}";

                if (priceListing.isMatched()) {
                    // *** requirement: a price can match only once

                    // An option is to remove the price listing for better efficiency since matching checks would
                    // not be performed. However, I left them in for auditing and verification and, in a reality,
                    // perhaps a followup analysis of the price listings.
                    logger.warn(logString);

                } else {
                    logger.info("Y, " + logString);

                    matchedPriceListings.add(priceListing);
                }
            }
        }

        return matchedPriceListings;
    }

    /**
     * Determines if priceListing is a match to the product.
     * 
     * @param product
     * @param priceListing
     * @return
     */
    public boolean doesListingMatch(Product product, PriceListing priceListing) {
        String regex = "\\b" + product.getModel() + "\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(priceListing.getTitle());

        // find looks for whole words
        boolean matches = matcher.find();

        return matches;
    }

}
