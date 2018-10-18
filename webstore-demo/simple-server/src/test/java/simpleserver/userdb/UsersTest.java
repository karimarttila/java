package simpleserver.userdb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import simpleserver.util.Consts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Users test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UsersImpl.class})
public class UsersTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Users users;


    @DisplayName("Tests if email already exists")
    @Test
    public void emailAlreadyExistsTest() {
        logger.debug(Consts.LOG_ENTER);
        boolean retOk = users.emailAlreadyExists("kari.karttinen@foo.com");
        boolean retNotOk = users.emailAlreadyExists("NOT.FOUND@foo.com");
        assertTrue(retOk);
        assertTrue(!retNotOk);
        logger.debug(Consts.LOG_EXIT);
    }





}
