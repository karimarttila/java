package simpleserver.userdb;

import simpleserver.domaindb.dto.Product;
import simpleserver.userdb.dto.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The interface Users.
 */
public interface Users {


    /**
     * Gets a shallow copy of users.
     *
     * @return Users
     */
    Map getUsers();

    /**
     * Checks if given email already exists in the user db.
     *
     * @param givenEmail the given email
     * @return true if email already exists, false otherwise
     */
    boolean emailAlreadyExists(String givenEmail);

    /**
     * Adds new user to the database.
     *
     * @param newEmail Email
     * @param firstName First name
     * @param lastName Last name
     * @param password Password
     * @return new User if ok, null if not ok (+ possible SSException)
     * @throws simpleserver.util.SSException if failure
     */
    User addUser(String newEmail, String firstName, String lastName, String password);
}
