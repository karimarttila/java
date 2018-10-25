package simpleserver.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * The type Ss configuration.
 */
@Service
@PropertySource("application.properties")
public class SSPropertiesImpl implements SSProperties {

    @Value("${json-web-token-expiration-as-seconds}")
    private int jsonWebTokenExpirationSeconds;

    @Value("${application-properties}")
    private String applicationPropertiesFilePath;

    @Override
    public int getJsonWebTokenExpirationSeconds() {
        return jsonWebTokenExpirationSeconds;
    }

    @Override
    public String getApplicationPropertiesFilePath() {
        return applicationPropertiesFilePath;
    }
}
