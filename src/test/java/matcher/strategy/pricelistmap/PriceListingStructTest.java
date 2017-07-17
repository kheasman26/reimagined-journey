package matcher.strategy.pricelistmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import matcher.model.PriceListing;
import matcher.strategy.pricelistmap.PriceListingStruct;

public class PriceListingStructTest {
	private PriceListingStruct struct = null;
	
	@Before
	public void setup() {
		// Authoritative list of manufacturers (that is generated from the list of Products)
		// used as initial filter down of the price listings
		List<String> manus = new ArrayList<>();
		manus.add("SONY");
		manus.add("CASIO");
		manus.add("ABBA");
		manus.add("CONTAX"); // Similar to real dataset, no price listings that match
		
		struct = new PriceListingStruct(manus);
	}
	
	@Test
	public void testStructBuild_emptyPriceListings() {
		List<PriceListing> priceListings = new ArrayList<>();
		struct.build(priceListings);

		Map<String, List<PriceListing>> manuMap = struct.getManuListingMap();
		Assert.assertNotNull(manuMap);
		
		manuMap.forEach((key, value) -> Assert.assertEquals(0, value.size()));
	}
	
	@Test
	public void testStructBuild_valid() {
		
	    // only the manufacturer property is used in the building of the map datastruct
	    List<PriceListing> priceListings = new ArrayList<>();
		priceListings.add(new PriceListing(null, "Manu123", 0f, null)); // no matching manu
		priceListings.add(new PriceListing(null, "Sony Canada", 0f, null)); // non-exact manu
		priceListings.add(new PriceListing(null, "Sony", 0f, null)); // exact manu
		priceListings.add(new PriceListing(null, "Casio Canada", 0f, null));
		priceListings.add(new PriceListing(null, "Sony ABC", 0f, null));
		priceListings.add(new PriceListing(null, "Abba Inc.", 0f, null));
		priceListings.add(new PriceListing(null, "Abba Corp", 0f, null));
		priceListings.add(new PriceListing(null, "Abba", 0f, null));
		priceListings.add(new PriceListing(null, "Abba Cadabra Jinx", 0f, null));
		priceListings.add(new PriceListing(null, "Manu123", 0f, null));
		
		struct.build(priceListings);
		
		Map<String, List<PriceListing>> manuMap = struct.getManuListingMap();
		Assert.assertNotNull(manuMap);
		
		// 4 manus in the product list but there is no listing for CONTAX
		Assert.assertEquals(3, manuMap.size());
		
		assertMapEntry(manuMap, "SONY", 3);
		assertMapEntry(manuMap, "CASIO", 1);
		assertMapEntry(manuMap, "ABBA", 4);
		
		// The manufacturer CONTAX was never in the price listings.
		Assert.assertTrue(!manuMap.containsKey("CONTAX"));
	}
	
	private void assertMapEntry(final Map<String, List<PriceListing>> manuMap, final String key, final int size) {
		Assert.assertTrue(manuMap.containsKey(key));
		Assert.assertEquals(size, manuMap.get(key).size());
	}
	
	
}
