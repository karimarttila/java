package simpleserver.webserver.response;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Login ok response.
 */
public class LoginOkResponseImpl implements Response {

    private final Map<String, Object> response = new HashMap<>();


    private LoginOkResponseImpl(String jwt) {
        response.put("ret", "ok");
        response.put("msg", "Credentials ok");
        response.put("json-web-token", jwt);
    }


    /**
     * Create login ok response.
     *
     * @param jwt the jwt
     * @return the response
     */
    public static Response createLoginOkResponse(String jwt) {
        return new LoginOkResponseImpl(jwt);
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
