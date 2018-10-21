package simpleserver.userdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleserver.userdb.dto.User;
import simpleserver.util.Consts;
import org.springframework.stereotype.Service;
import simpleserver.util.SSErrorCode;
import simpleserver.util.SSException;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.codec.digest.DigestUtils;


/**
 * The type Users.
 */
@Service
public class UsersImpl implements Users {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * The Synchronized user db. Synchronized since Spring Services
     * are singletons by default (and hence not multi-thread safe)
     * and class member variables need to be synchoronized
     * to be thread safe.
     */
    private final Map<String, User> syncUserDb =
            Collections.synchronizedMap(new HashMap<>());
    /**
     * The Counter. Access synchronized on method level.
     */
    private long counter = 3;


    /**
     * Instantiates a new Users.
     */
    public UsersImpl() {
        User user1 = new User(1, "kari.karttinen@foo.com",
                "Kari", "Karttinen", "87EE0597C41D7AB8C074D7DC4794716D");
        User user2 = new User(2, "timo.tillinen@foo.com",
                "Timo", "Tillinen", "EE5F0C6F4D191B58497F7DB5C5C9CAF8");
        User user3 = new User(3, "erkka.erkkila@foo.com",
                "Erkka", "Erkkila", "8F0E3B40464DFF73C392DCE9F2DCE9DD");
        syncUserDb.put(Long.toString(user1.userId), user1);
        syncUserDb.put(Long.toString(user2.userId), user2);
        syncUserDb.put(Long.toString(user3.userId), user3);
    }

    /**
     * Get next counter.
     *
     * @return the long
     */
    private synchronized long counter() {
        counter++;
        return counter;
    }


    @Override
    public Map<String, User> getUsers() {
        return new HashMap<>(syncUserDb);
    }

    @Override
    public boolean emailAlreadyExists(String givenEmail) {
        logger.debug(Consts.LOG_ENTER);
        Collection<User> users = syncUserDb.values();
        List<User> filteredUsers = users.stream().filter( thisUser ->
                (thisUser.email.equals(givenEmail))).collect(Collectors.toList());
        boolean ret = (!filteredUsers.isEmpty());
        logger.debug("{}, ret: {}", Consts.LOG_EXIT, ret);
        return ret;
    }

    @Override
    public User addUser(String newEmail, String firstName, String lastName, String password) {
        logger.debug(Consts.LOG_ENTER);
        User user;
        if (!emailAlreadyExists(newEmail)) {
            long id = counter();
            String hashedPassword = DigestUtils.md5Hex(password).toUpperCase();
            user = new User(id, newEmail, firstName, lastName, hashedPassword);
            syncUserDb.put(Long.toString(id), user);
        }
        else {
            throw new SSException("Email already exists: " + newEmail, SSErrorCode.EMAIL_ALREADY_EXISTS);
        }
        logger.debug(Consts.LOG_EXIT);
        return user;
    }
}
