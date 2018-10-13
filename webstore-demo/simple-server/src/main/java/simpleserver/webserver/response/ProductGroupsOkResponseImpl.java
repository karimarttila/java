package simpleserver.webserver.response;

import org.json.JSONPropertyName;
import org.springframework.beans.factory.annotation.Autowired;
import simpleserver.domaindb.dto.ProductGroups;

import java.util.HashMap;
import java.util.Map;

public class ProductGroupsOkResponseImpl implements ProductGroupsResponse {
    private Map<String, Object> response = new HashMap<>();

    private ProductGroupsOkResponseImpl(ProductGroups productGroups) {
        response.put("ret", "ok");
        // Has to do like this or creates a Json conversion with extra
        response.put("product-groups", productGroups.getProductGroups());
    }

    public static ProductGroupsResponse createProductGroupsOkResponse(ProductGroups productGroups) {
        var ret = new ProductGroupsOkResponseImpl(productGroups);
        return ret;
    }

    @Override
    public Map<String, Object> getResponse() {
        return response;
    }

}
