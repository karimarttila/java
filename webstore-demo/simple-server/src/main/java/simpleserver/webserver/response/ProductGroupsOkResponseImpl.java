package simpleserver.webserver.response;

import simpleserver.domaindb.dto.ProductGroups;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Product groups ok response.
 */
public class ProductGroupsOkResponseImpl implements Response {
    private final Map<String, Object> response = new HashMap<>();


    private ProductGroupsOkResponseImpl(ProductGroups productGroups) {
        response.put("ret", "ok");
        // Has to do like this or creates a Json conversion with extra layer.
        response.put("product-groups", productGroups.getProductGroups());
    }


    /**
     * Create product groups ok response.
     *
     * @param productGroups the product groups
     * @return the response
     */
    public static Response createProductGroupsOkResponse(ProductGroups productGroups) {
        return new ProductGroupsOkResponseImpl(productGroups);
    }


    @Override
    public Map<String, Object> getRestView() {
        return response;
    }


    @Override
    public boolean isOk() {
        return true;
    }
}
