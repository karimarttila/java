package simpleserver.userdb.dto;

public class User {

    public long userId;
    public String email;
    public String firstName;
    public String lastName;
    public String hashedPassword;

    public User(long userId, String email, String firstName, String lastName,
                String hashedPassword) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hashedPassword = hashedPassword;
    }
}
