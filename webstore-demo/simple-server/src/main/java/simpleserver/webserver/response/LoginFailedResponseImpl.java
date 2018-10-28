package simpleserver.webserver.response;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Login failed response.
 */
public class LoginFailedResponseImpl implements Response {

    private final Map<String, Object> response = new HashMap<>();


    private LoginFailedResponseImpl(String errMsg, String email) {
        response.put("ret", "failed");
        response.put("msg", errMsg);
        if (email != null) {
            response.put("email", email);
        }
    }


    /**
     * Create login failed response.
     *
     * @param errMsg the error msg
     * @param email  the email
     * @return the response
     */
    public static Response createLoginFailedResponse(String errMsg, String email) {
        return new LoginFailedResponseImpl(errMsg, email);
    }


    @Override
    public Map<String, Object> getRestView() {
        return response;
    }


    @Override
    public boolean isOk() {
        return false;
    }

}
