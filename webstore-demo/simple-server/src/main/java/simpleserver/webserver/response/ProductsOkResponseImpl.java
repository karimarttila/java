package simpleserver.webserver.response;

import java.util.HashMap;
import java.util.Map;
import simpleserver.domaindb.dto.Products;


public class ProductsOkResponseImpl implements Response {
    private final Map<String, Object> response = new HashMap<>();

    private ProductsOkResponseImpl(Products products) {
        response.put("ret", "ok");
        String pgId = new Integer(products.getPgId()).toString();
        response.put("pg-id", pgId);
        // Has to do like this or creates a Json conversion with extra layer.
        response.put("products", products.getProducts());
    }

    public static Response createProductsOkResponse(Products products) {
        return new ProductsOkResponseImpl(products);
    }

    @Override
    public Map<String, Object> getResponse() {
        return response;
    }

}

