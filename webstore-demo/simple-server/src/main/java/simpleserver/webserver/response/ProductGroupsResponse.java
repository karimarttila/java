package simpleserver.webserver.response;

import simpleserver.domaindb.dto.ProductGroups;

import java.util.Map;

/**
 * The interface Product groups response.
 */
public interface ProductGroupsResponse {

    /**
     * Gets the responce map that Spring converts to JSON.
     * @return the response
     */
    public Map<String, Object> getResponse();
}
