package simpleserver.domaindb.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Product Groups entity.
 */
public class ProductGroups {

    private final Map<String, String> groups = new HashMap<>();

    /**
     * Gets product groups for the Spring controller (Server.java).
     * Good as it is (map) which Spring controller converts to json object.
     *
     * @return product groups
     */

    public Map<String, String> getProductGroups() {
        return groups;
    }

    /**
     * Adds a new product group.
     *
     * @param id   the id of the group
     * @param name the name of the group
     */
    public void addProductGroup(String id, String name) {
        groups.put(id, name);
    }

}
