package simpleserver.domaindb.dto;

/**
 * The Info.
 * Provides information to view index.html page.
 */
public class Info {

    private static final String INFO_MSG = "index.html => Info in HTML format";

    /**
     * Gets info as json. Used in testing.
     * Not needed in production since Spring controller creates the mapping
     * from the Java data structure (items) to the JSON automatically.
     *
     * @return the info
     */
    public String getInfo() {
        return INFO_MSG;
    }

}

