package simpleserver.userdb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import simpleserver.userdb.dto.User;
import simpleserver.util.SSConsts;
import simpleserver.util.SSException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Users test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UsersImpl.class})
class UsersTest {
    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Users users;


    @DisplayName("Tests if email already exists")
    @Test
    void emailAlreadyExistsTest() {
        logger.debug(SSConsts.LOG_ENTER);
        boolean retOk = users.emailAlreadyExists("kari.karttinen@foo.com");
        boolean retNotOk = users.emailAlreadyExists("NOT.FOUND@foo.com");
        assertTrue(retOk);
        assertTrue(!retNotOk);
        logger.debug(SSConsts.LOG_EXIT);
    }


    @DisplayName("Tests adding a new user")
    @Test
    void addUserTest() {
        logger.debug(SSConsts.LOG_ENTER);
        Map<String, User> currentUsers = users.getUsers();
        assertEquals(3, currentUsers.size());
        // Adding new user with non-conflicting email.
        User newUser = users.addUser("jamppa.jamppanen@foo.com", "Jamppa", "Jamppanen", "JampanSalasana");
        currentUsers = users.getUsers();
        assertEquals(4, currentUsers.size());
        assertTrue(users.emailAlreadyExists("jamppa.jamppanen@foo.com"));
        // Trying to add the same email again.
        Executable codeToTest = () -> {
            User failedUser = users.addUser("jamppa.jamppanen@foo.com", "Jamppa", "Jamppanen", "JampanSalasana");
        };
        SSException ex = assertThrows(SSException.class, codeToTest);
        assertEquals("Email already exists: jamppa.jamppanen@foo.com", ex.getMessage());
        logger.debug(SSConsts.LOG_EXIT);
    }


    @DisplayName("Tests checking user credentials")
    @Test
    void checkCredentialsTest() {
        logger.debug(SSConsts.LOG_ENTER);
        assertTrue(users.checkCredentials("kari.karttinen@foo.com", "Kari"));
        assertTrue(!users.checkCredentials("NOT-FOUND.karttinen@foo.com", "Kari"));
        assertTrue(!users.checkCredentials("kari.karttinen@foo.com", "WRONG-PASSWORD"));
    }
}
