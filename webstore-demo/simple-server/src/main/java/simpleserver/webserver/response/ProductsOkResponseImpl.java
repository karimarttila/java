package simpleserver.webserver.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import simpleserver.domaindb.dto.Product;


public class ProductsOkResponseImpl implements Response {
    private final Map<String, Object> response = new HashMap<>();

    private ProductsOkResponseImpl(int myPgId, List<Product> products) {
        response.put("ret", "ok");
        String pgId = Integer.toString(myPgId);
        response.put("pg-id", pgId);
        // Has to do like this or creates a Json conversion with extra layer.
        List<String[]> productsView = products.stream().map(product -> {
            String[] p = new String[4];
            p[0] = Integer.toString(product.pId);
            p[1] = Integer.toString(product.pgId);
            p[2] = product.title;
            p[3] = Double.toString(product.price);
            return p;
        }).collect(Collectors.toList());
        response.put("products", productsView);
    }

    public static Response createProductsOkResponse(int pgId, List<Product> products) {
        return new ProductsOkResponseImpl(pgId, products);
    }

    @Override
    public Map<String, Object> getRestView() {
        return response;
    }

}

