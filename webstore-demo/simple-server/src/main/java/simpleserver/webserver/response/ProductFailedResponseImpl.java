package simpleserver.webserver.response;

import simpleserver.domaindb.dto.Product;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Product failed response.
 */
public class ProductFailedResponseImpl implements Response {

    private final Map<String, Object> response = new HashMap<>();


    private ProductFailedResponseImpl(String msg) {
        response.put("ret", "failed");
        response.put("msg", msg);
    }


    /**
     * Create product failed response.
     *
     * @param msg Error message
     * @return the response
     */
    public static Response createProductFailedResponse(String msg) {
        return new ProductFailedResponseImpl(msg);
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
