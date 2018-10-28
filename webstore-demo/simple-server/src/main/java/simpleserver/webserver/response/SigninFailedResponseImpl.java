package simpleserver.webserver.response;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Signin failed response.
 */
public class SigninFailedResponseImpl implements Response {

    private final Map<String, Object> response = new HashMap<>();


    private SigninFailedResponseImpl(String errMsg, String email) {
        response.put("ret", "failed");
        response.put("msg", errMsg);
        if (email != null) {
            response.put("email", email);
        }
    }


    /**
     * Create signin failed response.
     *
     * @param errMsg the error msg
     * @param email  the email
     * @return the response
     */
    public static Response createSigninFailedResponse(String errMsg, String email) {
        return new SigninFailedResponseImpl(errMsg, email);
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
