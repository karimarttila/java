package simpleserver.userdb.dto;

/**
 * The type User.
 * NOTE: Uses intentionally public fields.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class User {

    // We provide fields as public since there is no reason why they should
    // be private in a simple DTO class like this.
    public final long userId;
    public final String email;
    public final String firstName;
    public final String lastName;
    public final String hashedPassword;

    /**
     * Instantiates a new User.
     *
     * @param userId         the user id
     * @param email          the email
     * @param firstName      the first name
     * @param lastName       the last name
     * @param hashedPassword the hashed password
     */
    public User(long userId, String email, String firstName, String lastName,
                String hashedPassword) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hashedPassword = hashedPassword;
    }

}
