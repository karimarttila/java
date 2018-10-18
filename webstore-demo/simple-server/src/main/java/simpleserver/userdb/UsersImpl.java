package simpleserver.userdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleserver.userdb.dto.User;
import simpleserver.util.Consts;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Users.
 */
@Service
public class UsersImpl implements Users {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * The Sync user db.
     */
    Map<String, User> syncUserDb =
            Collections.synchronizedMap(new HashMap<String, User>());
    /**
     * The Counter.
     */
    long counter = 3;

    /**
     * Instantiates a new Users.
     */
    public UsersImpl() {
        User user1 = new User(1, "kari.karttinen@foo.com",
                "Kari", "Karttinen", "2087856692");
        User user2 = new User(2, "timo.tillinen@foo.com",
                "Timo", "Tillinen", "2087903674");
        User user3 = new User(3, "erkka.erkkila@foo.com",
                "Erkka", "Erkkila", "170251027");
        syncUserDb.put(Long.toString(user1.userId), user1);
        syncUserDb.put(Long.toString(user2.userId), user2);
        syncUserDb.put(Long.toString(user3.userId), user3);
    }

    /**
     * Get next counter.
     *
     * @return the long
     */
    public synchronized long counter() {
        counter++;
        return counter;
    }

    /**
     * Gets users.
     *
     * @return the users
     */
    public Map getUsers() {
        return new HashMap<String, User>(syncUserDb);

    }


    @Override
    public boolean emailAlreadyExists(String givenEmail) {
        logger.debug(Consts.LOG_ENTER);
        Collection<User> users = syncUserDb.values();
        List<User> filteredUsers = users.stream().filter( thisUser ->
                (thisUser.email.equals(givenEmail))).collect(Collectors.toList());
        boolean ret = (filteredUsers.size() > 0);
        logger.debug(Consts.LOG_EXIT + ", ret: " + ret);
        return ret;
    }
}
