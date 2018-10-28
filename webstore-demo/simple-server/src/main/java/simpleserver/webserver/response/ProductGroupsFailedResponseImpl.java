package simpleserver.webserver.response;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Product groups failed response.
 */
public class ProductGroupsFailedResponseImpl implements Response {
    private final Map<String, Object> response = new HashMap<>();


    private ProductGroupsFailedResponseImpl(String msg) {
        response.put("ret", "failed");
        response.put("msg", msg);
    }


    /**
     * Create product groups failed response.
     *
     * @param msg the msg
     * @return the response
     */
    public static Response createProductGroupsFailedResponse(String msg) {
        return new ProductGroupsFailedResponseImpl(msg);
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
