package matcher.dao;

import java.util.List;

import matcher.model.Product;

/**
 * Responsible for building and returning a list of Product objects. How is up to the implementation class. Assumption:
 * the list will not be so large that returning all will be a problem.
 */
public interface ProductDao {
    public List<Product> buildList();

    public List<String> getManufacturers();

}
