package simpleserver.webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertTrue;

import simpleserver.util.Consts;
import simpleserver.util.SSConfigurationImpl;

@DisplayName("Session test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SessionImpl.class, SSConfigurationImpl.class})
public class SessionTest {

    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Session session;


    @DisplayName("Tests creating the JSON web token")
    @Test
    void createJsonWebTokenTest() {
        logger.debug(Consts.LOG_ENTER);
        String jwt = session.createJsonWebToken("kari.karttinen@foo.com");
        logger.debug("Returned jwt: {}", jwt);
        assertTrue(jwt.length() > 10);
        logger.debug(Consts.LOG_EXIT);
    }


}
