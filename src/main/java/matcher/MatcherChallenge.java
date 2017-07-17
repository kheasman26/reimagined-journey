package matcher;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import matcher.dao.PriceListingMatchesDao;
import matcher.dao.PriceListingMatchesDaoFile;
import matcher.dao.ProductDao;
import matcher.dao.ProductDaoFile;
import matcher.model.PriceListingMatches;
import matcher.model.Product;
import matcher.strategy.pricelistmap.PriceListMapMatcher;
import matcher.strategy.pricelistmap.PriceListingStruct;

public class MatcherChallenge {
    private static final Logger logger = LoggerFactory.getLogger(MatcherChallenge.class);

    public static void main(String[] args) {
        logger.info("starting matcher challenge ...");

        // In an enterprise app, should call a service method with auditing,
        // transaction demarcation, etc. Cutting corner here and calling DAO directly.
        ProductDao productDao = new ProductDaoFile();
        List<Product> products = productDao.buildList();

        PriceListingStruct priceListingStruct = new PriceListingStruct(productDao.getManufacturers());
        priceListingStruct.build();

        logger.info("running matcher ...");
        PriceListMapMatcher matcher = new PriceListMapMatcher();
        List<PriceListingMatches> matches = matcher.match(products, priceListingStruct);

        logger.info("saving matches ...");
        
        // Same as above, in an enterprise app should use a service.
        PriceListingMatchesDao priceListingMatchesDao = new PriceListingMatchesDaoFile();
        priceListingMatchesDao.saveAll(matches);

        logger.info("done!");
    }

}
