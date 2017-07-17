package matcher.strategy.pricelistmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import matcher.dao.PriceListingDao;
import matcher.dao.PriceListingDaoFile;
import matcher.model.PriceListing;

/**
 * Builds and holds data structures for the PriceListing entries. Data structures:
 * <ul>
 * <li>manufacturer/PriceListings map - used in main logic of matcher to find matches</li>
 * <li>PriceListings - a list of non-matching PriceListings - for manual verification but, in reality, could be used for
 * reporting and possible escalation.</li>
 * </ul>
 */
public class PriceListingStruct {

    private List<String> manus;

    private Map<String, List<PriceListing>> manuListingMap = new TreeMap<>();
    private List<String> noMatchPriceListings = new ArrayList<>();

    // dep
    private PriceListingDao priceListingDao;

    public Map<String, List<PriceListing>> getManuListingMap() {
        return manuListingMap;
    }

    public List<String> getNoMatchPriceListings() {
        return noMatchPriceListings;
    }

    /**
     * Definitive set of manufacturers to use when building the data structures.
     * 
     * @param manus
     */
    public PriceListingStruct(List<String> manus) {
        this.manus = manus;

        // TODO: use injection framework
        priceListingDao = new PriceListingDaoFile();
    }

    public void build() {
        // Get full list of PriceListings
        List<PriceListing> priceListings = priceListingDao.buildList();

        build(priceListings);
    }

    /**
     * From the list of PriceListing entries, will build the data structures.
     * 
     * @param priceListings
     */
    public void build(final List<PriceListing> priceListings) {

        for (PriceListing listing : priceListings) {
            String listingManu = listing.getManufacturer();

            // Used to add to the no-match list or not
            boolean match = false;

            // For each listing-manu, determine into what manu bucket it should fit.
            for (String productManu : manus) {

                // listingManu is either a manufacturer name exactly, or contains it
                // example: 'Canon Canada' is the PriceListing manufacturer that contains 'Canon'
                if (listingManu.indexOf(productManu) > -1) {
                    match = true; // hit!

                    if (manuListingMap.containsKey(productManu)) {
                        // Mapping already exists. Get the associated entry list and add this to it.
                        List<PriceListing> entries = manuListingMap.get(productManu);
                        entries.add(listing);

                    } else {
                        // Entry with key doesnt exist
                        List<PriceListing> entries = new ArrayList<>();
                        entries.add(listing);

                        manuListingMap.put(productManu, entries);
                    }

                    // Match is found, no need to continue looping
                    break;
                }
            }

            if (!match && !noMatchPriceListings.contains(listingManu)) {
                noMatchPriceListings.add(listingManu);
            }
        }

    }

}
