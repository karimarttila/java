package simpleserver.webserver.response;

import simpleserver.domaindb.dto.ProductGroups;

import java.util.HashMap;
import java.util.Map;

public class ProductGroupsOkResponseImpl implements ProductGroupsResponse {
    private final Map<String, Object> response = new HashMap<>();

    private ProductGroupsOkResponseImpl(ProductGroups productGroups) {
        response.put("ret", "ok");
        // Has to do like this or creates a Json conversion with extra
        response.put("product-groups", productGroups.getProductGroups());
    }

    public static ProductGroupsResponse createProductGroupsOkResponse(ProductGroups productGroups) {
        return new ProductGroupsOkResponseImpl(productGroups);
    }

    @Override
    public Map<String, Object> getResponse() {
        return response;
    }

}
