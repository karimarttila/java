package simpleserver.webserver.response;

import java.util.Map;

/**
 * The interface Product groups response.
 */
public interface ProductGroupsResponse {

    /**
     * Gets the responce map that Spring converts to JSON.
     * @return the response
     */
    Map<String, Object> getResponse();
}
