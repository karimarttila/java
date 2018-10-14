package simpleserver.domaindb.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Product Groups entity.
 */
public class ProductGroups {

    private final Map<String, String> items = new HashMap<>();

    /**
     * Gets product groups for the Spring Controller (Server.java).
     *
     * @return product groups
     */

    public Map<String, String> getProductGroups() {
        return items;
    }

    /**
     * Adds a new product group.
     *
     * @param id   the id
     * @param name the name
     */
    public void addProductGroup(String id, String name) {
        items.put(id, name);
    }

}
