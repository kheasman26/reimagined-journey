package matcher.dao;

import java.util.List;

import matcher.model.PriceListingMatches;

public interface PriceListingMatchesDao {
    public void saveAll(List<PriceListingMatches> matches);

}
