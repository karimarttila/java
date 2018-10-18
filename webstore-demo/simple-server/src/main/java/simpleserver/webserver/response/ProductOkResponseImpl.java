package simpleserver.webserver.response;

import simpleserver.domaindb.dto.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductOkResponseImpl implements Response {
    private final Map<String, Object> response = new HashMap<>();

    private ProductOkResponseImpl(Product product) {
        response.put("ret", "ok");
        String pgId = Integer.toString(product.pgId);
        String pId = Integer.toString(product.pId);
        response.put("pg-id", pgId);
        response.put("p-id", pId);
        // Has to do like this or creates a Json conversion with extra layer.
        String[] productView = new String[8];
        productView[0] = Integer.toString(product.pId);
        productView[1] = Integer.toString(product.pgId);
        productView[2] = product.title;
        productView[3] = Double.toString(product.price);
        productView[4] = product.author_or_director;
        productView[5] = Integer.toString(product.year);
        productView[6] = product.country;
        productView[7] = product.genre_or_language;
        response.put("product", productView);
    }

    public static Response createProductOkResponse(Product product) {
        return new ProductOkResponseImpl(product);
    }

    @Override
    public Map<String, Object> getRestView() {
        return response;
    }

}
