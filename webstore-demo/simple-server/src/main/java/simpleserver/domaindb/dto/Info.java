package simpleserver.domaindb.dto;

/**
 * The Info.
 * Provides information to view index.html page.
 */
public class Info {
    private String info = "index.html => Info in HTML format";

    /**
     * Gets info as json. Used in testing.
     * Not needed in production since Spring controller creates the mapping
     * from the Java data structure (items) to the JSON automatically.
     *
     * @return the info
     */
    public String getInfoAsString() {
        return info;
    }


    /**
     * Gets info.
     *
     * @return the info
     */
    public String getInfo() {
        return info;
    }
}

