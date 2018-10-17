package simpleserver.domaindb;

import simpleserver.domaindb.dto.Info;
import simpleserver.domaindb.dto.ProductGroups;
import simpleserver.domaindb.dto.Product;

import java.util.List;

/**
 * The interface Domain.
 */
public interface Domain {

    /**
     * Gets info.
     *
     * @return the info
     */
    Info getInfo();

    /**
     * Gets product groups.
     *
     * @return the product groups
     */
    ProductGroups getProductGroups();

    /**
     * Gets the products.
     *
     * @param pgId product group id
     * @return the products
     */
    List<Product> getProducts(int pgId);


    /**
     * Gets the product.
     * @param pgId product group id
     * @param pId product id
     * @return the product
     */
    Product getProduct(int pgId, int pId);
}
