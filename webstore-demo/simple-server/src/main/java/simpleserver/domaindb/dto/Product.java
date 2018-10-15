package simpleserver.domaindb.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Product entity.
 */
public class Product {

    private int pgId;
    private int pId;
    private final List<String> item = new ArrayList<String>();

    /**
     * Instantiates a new Product.
     *
     * @param pgId the product group id
     * @param pId  the product id
     */
    public Product(int pgId, int pId) {
        this.pgId = pgId;
        this.pId = pId;
    }

    /**
     * Gets product group id.
     *
     * @return the product group id
     */
    public int getPgId() {
        return pgId;
    }

    /**
     * Gets product id.
     *
     * @return the product id
     */
    public int getpId() {
        return pId;
    }

    /**
     * Gets the product.
     *
     * @return the product
     */
    public List<String> getProduct() { return item; }

    /**
     * Sets product.
     *
     * @param product the product
     */
    public void setProduct(String[] product) {
        item.addAll(Arrays.asList(product));
    }

}
