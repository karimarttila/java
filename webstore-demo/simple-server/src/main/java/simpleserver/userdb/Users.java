package simpleserver.userdb;

import simpleserver.domaindb.dto.Product;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The interface Users.
 */
public interface Users {

    /**
     * Checks if given email already exists in the user db.
     *
     * @param givenEmail the given email
     * @return true if email already exists, false otherwise
     */
    boolean emailAlreadyExists(String givenEmail);


}
