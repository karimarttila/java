package simpleserver.webserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Signin {

    @JsonProperty("first-name")
    public String firstName;

    @JsonProperty("last-name")
    public String lastName;

    @JsonProperty("email")
    public String email;

    @JsonProperty("password")
    public String password;

    public List<String> getParamsAsList() {
        List<String> ret = new ArrayList<>();
        ret.add(firstName);
        ret.add(lastName);
        ret.add(email);
        ret.add(password);
        return ret;
    }

}
