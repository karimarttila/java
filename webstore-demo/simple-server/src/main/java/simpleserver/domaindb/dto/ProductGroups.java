package simpleserver.domaindb.dto;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Product Groups entity.
 */
public class ProductGroups {

    private Map<String, String> items = new HashMap<>();

    /**
     * Gets product groups as json. Used in testing.
     * Not needed in production since Spring controller creates the mapping
     * from the Java data structure (items) to the JSON automatically.
     *
     * @return the product groups as json
     */
    public String getProductGroupsAsJson() {
        JSONObject jPg = new JSONObject(items);
        return jPg.toString();
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
