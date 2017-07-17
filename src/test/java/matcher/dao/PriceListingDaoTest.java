package matcher.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import matcher.model.PriceListing;

public class PriceListingDaoTest {
	
	@Test
	public void test() {
		PriceListingDao plDao = new PriceListingDaoFile();
		
		List<PriceListing> priceListings = plDao.buildList();
		Assert.assertNotNull(priceListings);
		
		Assert.assertEquals(20196, priceListings.size());
		
		// Some basic spot checks, first and last
		PriceListing pl1 = priceListings.get(0);
		Assert.assertNotNull(pl1);
		Assert.assertEquals("LED Flash Macro Ring Light (48 X LED) with 6 Adapter Rings for For Canon/Sony/Nikon/Sigma Lenses", pl1.getTitle());
		
		PriceListing plLast = priceListings.get(priceListings.size() - 1);
		Assert.assertNotNull(plLast);
		Assert.assertEquals("Olympus - Pen E-PL1 - Compact à objectifs interchangeables - Boîtier nu - 12,3 Mpix - Blanc", plLast.getTitle());
		Assert.assertEquals("462.56", plLast.getPrice());
	}
	
}
