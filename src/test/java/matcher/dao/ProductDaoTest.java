package matcher.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import matcher.model.Product;

public class ProductDaoTest {

	@Test
	public void test() {
		ProductDao plDao = new ProductDaoFile();
		
		List<Product> products = plDao.buildList();
		Assert.assertNotNull(products);
		Assert.assertEquals(743, products.size());
		
		// spot-check assertions
		Product p1 = products.get(0);
		Assert.assertEquals(p1.getProductName(), "Sony_Cyber-shot_DSC-W310");
		
		// Make sure manufacturer is uppercase
		Assert.assertEquals("SONY", p1.getManufacturer());
		
		Product productLast = products.get(products.size() - 1);
		Assert.assertEquals(productLast.getProductName(), "Casio_XV-3");
		
		// Make sure manufacturer is uppercase
		Assert.assertEquals("CASIO", productLast.getManufacturer());
	}
	
}
