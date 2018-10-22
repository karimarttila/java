package simpleserver.webserver.response;

import java.util.HashMap;
import java.util.Map;

public class SigninOkResponseImpl implements Response {

    private final Map<String, Object> response = new HashMap<>();


    private SigninOkResponseImpl(String email) {
        response.put("ret", "ok");
        response.put("email", email);
    }


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
