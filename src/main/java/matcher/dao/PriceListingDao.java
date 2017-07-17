package matcher.dao;

import java.util.List;

import matcher.model.PriceListing;

/**
 * Responsible for building and returning a list of PriceListing objects. How is up to the implementation class.
 * Assumption: the list will not be so large that returning all will be a problem.
 */
public interface PriceListingDao {

    public List<PriceListing> buildList();
}
