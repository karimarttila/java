package simpleserver.util;

public interface SSConfiguration {

    /**
     * Gets json web token expiration seconds.
     *
     * @return the json web token expiration seconds
     */
    int getJsonWebTokenExpirationSeconds();

    /**
     * Gets application properties file path.
     *
     * @return the application properties file path
     */
    String getApplicationPropertiesFilePath();

}
