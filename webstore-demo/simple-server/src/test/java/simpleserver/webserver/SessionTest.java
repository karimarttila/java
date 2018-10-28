package simpleserver.webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import simpleserver.util.SSConsts;
import simpleserver.util.SSPropertiesImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("Session test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SessionImpl.class, SSPropertiesImpl.class})
public class SessionTest {

    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Session session;


    @DisplayName("Tests the JSON web token")
    @Test
    void jsonWebTokenTest() {
        logger.debug(SSConsts.LOG_ENTER);
        // First try to create it.
        String testEmail = "kari.karttinen@foo.com";
        String jwt = session.createJsonWebToken(testEmail);
        logger.debug("Returned jwt: {}", jwt);
        assertTrue(jwt.length() > 10);
        // Now try to parse it.
        String email = session.validateJsonWebToken(jwt);
        assertEquals(testEmail, email);
        logger.debug(SSConsts.LOG_EXIT);
    }


}
