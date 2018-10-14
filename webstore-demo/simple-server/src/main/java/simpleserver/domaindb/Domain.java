package simpleserver.domaindb;

import simpleserver.domaindb.dto.Info;
import simpleserver.domaindb.dto.ProductGroups;
import simpleserver.domaindb.dto.Products;

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
    Products getProducts(int pgId);
}
