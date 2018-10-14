package simpleserver.domaindb.dto;

import java.util.ArrayList;
import java.util.List;

public class Products {

    private int pgId;
    private final List<List> items = new ArrayList<List>();

    /**
     * Products Constructor.
     *
     * @param pgId Product group id
     */
    public Products(int pgId) {
        this.pgId = pgId;
    }

    /**
     * Gets the product group id.
     *
     * @return product group id
     */
    public int getPgId() { return pgId;}


    /**
     * Gets products for the Spring Controller (Server.java).
     *
     * @return products
     */
    public List<List> getProducts() {
        return items;
    }

    /**
     * Adds a new product to the items list.
     *
     * @param product the product
     */
    public void addProduct(List product) {
        items.add(product);
    }

}
