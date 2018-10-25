package simpleserver.webserver;

import java.util.Map;

public interface Session {

    /**
     * Creates JSON web token (email as payload) and adds it to sessions set.
     *
     * @param userEmail User's email
     * @return Created Json Web Token
     */
    String createJsonWebToken(String userEmail);

    /**
     * Validates the token. Returns email from token
     * if session ok, null otherwise.
     * Token validation has two parts:
     * 1. Check that we actually created the token
     * in the first place (should find it in my-sessions set.
     * 2. Validate the actual token (can unsign it, token is not expired).
     *
     * @param jwt Json Web Token to validate
     * @return the email in the Json Web Token if parsed ok, null otherwise.
     */
    String validateJsonWebToken(String jwt);
}
