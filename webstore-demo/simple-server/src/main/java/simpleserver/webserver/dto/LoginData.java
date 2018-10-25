package simpleserver.webserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class LoginData {

    @JsonProperty("email")
    public String email;

    @JsonProperty("password")
    public String password;

    public List<String> getParamsAsList() {
        List<String> ret = new ArrayList<>();
        ret.add(email);
        ret.add(password);
        return ret;
    }

}
