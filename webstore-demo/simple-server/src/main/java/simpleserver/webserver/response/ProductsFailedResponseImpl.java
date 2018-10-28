package simpleserver.webserver.response;

import simpleserver.domaindb.dto.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * The type Products failed response.
 */
public class ProductsFailedResponseImpl implements Response {
    private final Map<String, Object> response = new HashMap<>();


    private ProductsFailedResponseImpl(String msg) {
        response.put("ret", "failed");
        response.put("msg", msg);
    }


    /**
     * Create products failed response.
     *
     * @param msg Error message
     * @return the response
     */
    public static Response createProductsFailedResponse(String msg) {
        return new ProductsFailedResponseImpl(msg);
    }


    @Override
    public Map<String, Object> getRestView() {
        return response;
    }


    @Override
    public boolean isOk() {
        return false;
    }
}

