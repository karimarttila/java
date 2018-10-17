package simpleserver.webserver.response;

import java.util.Map;

/**
 * The interface Product groups response.
 */
public interface Response {

    /**
     * Gets the responce map that Spring converts to JSON.
     * @return the response
     */
    Map<String, Object> getRestView();
}
