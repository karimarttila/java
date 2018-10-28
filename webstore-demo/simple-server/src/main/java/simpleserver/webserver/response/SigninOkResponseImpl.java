package simpleserver.webserver.response;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Signin ok response.
 */
public class SigninOkResponseImpl implements Response {

    private final Map<String, Object> response = new HashMap<>();


    private SigninOkResponseImpl(String email) {
        response.put("ret", "ok");
        response.put("email", email);
    }


    /**
     * Create signin ok response.
     *
     * @param email the email
     * @return the response
     */
    public static Response createSigninOkResponse(String email) {
        return new SigninOkResponseImpl(email);
    }


    @Override
    public Map<String, Object> getRestView() {
        return response;
    }


    @Override
    public boolean isOk() {
        return true;
    }

}
