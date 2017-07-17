package matcher.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import matcher.model.Product;

/**
 * List of price listings, in JSON, is in a file located at src/test/resources/products.txt
 */
public class ProductDaoFile implements ProductDao {

    private List<Product> products = new ArrayList<>();
    private List<String> manufacturers = new ArrayList<>();

    public List<String> getManufacturers() {
        return manufacturers;
    }

    @Override
    public List<Product> buildList() {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        InputStream is = null;
        BufferedReader br = null;

        try {
            is = classLoader.getResourceAsStream("products.txt");
            br = new BufferedReader(new InputStreamReader(is));

            ObjectMapper mapper = new ObjectMapper();

            String jsonLine = null;
            while ((jsonLine = br.readLine()) != null) {

                // JSON from String to Object
                Product product = mapper.readValue(jsonLine, Product.class);

                products.add(product);

                // Product object already uppercases the manufacturer so no need here.
                String productManu = product.getManufacturer();

                // At the same time, build the unique list of manufacturers
                if (!manufacturers.contains(productManu)) {
                    manufacturers.add(productManu);
                }
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

        return products;
    }

}
