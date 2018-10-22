package simpleserver.webserver.response;

import simpleserver.domaindb.dto.ProductGroups;

import java.util.HashMap;
import java.util.Map;

public class ProductGroupsOkResponseImpl implements Response {
    private final Map<String, Object> response = new HashMap<>();


    private ProductGroupsOkResponseImpl(ProductGroups productGroups) {
        response.put("ret", "ok");
        // Has to do like this or creates a Json conversion with extra layer.
        response.put("product-groups", productGroups.getProductGroups());
    }


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
